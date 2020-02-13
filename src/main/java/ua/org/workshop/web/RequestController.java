package ua.org.workshop.web;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.SecurityService;
import ua.org.workshop.service.StatusService;
import ua.org.workshop.web.form.RequestForm;
import ua.org.workshop.web.form.StatusForm;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class RequestController {

    private static final String CREATE_NEW_FORM_JSP_FILE = "requests/create-request-form";
    private static final String UPDATE_FORM_JSP_FILE = "requests/edit-request-form";
    private static final String REDIRECT_TO_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST = "redirect:/requests";
    private static final String REDIRECT_TO_MANAGER_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST = "redirect:/manager-requests";
    private static final String REDIRECT_TO_WORKMAN_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST = "redirect:/workman-requests";
    private static final String REDIRECT_TO_ACCESS_DENIED_PAGE = "redirect:/access-denied";
    private static final String DEFAULT_STATUS = "REGISTER";
    private static final String MANAGER_STATUS = "REGISTER";
    private static final String WORKMAN_STATUS = "ACCEPT";

    private static final Logger logger = LogManager.getLogger(RequestController.class);

    @Autowired
    private RequestService requestService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatusService statusService;

    private Utility utility = new Utility();

    @GetMapping("user/requests")
    @ResponseBody
    Page<Request> userRequests(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "title", direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale){
        return requestService.findAllByLanguageAndAuthor(
                pageable,
                utility.getLanguageStringForStoreInDB(locale),
                accountService.getAccountByUsername(SecurityService.getCurrentUsername())
        );
    }

    @GetMapping("manager/requests")
    @ResponseBody
    Page<Request> managerRequests(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "title", direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale){
        return requestService.findAllByLanguageAndStatus(
                pageable,
                utility.getLanguageStringForStoreInDB(locale),
                statusService.findByCode(MANAGER_STATUS)
        );
    }

    @GetMapping("workman/requests")
    @ResponseBody
    Page<Request> workmanRequests(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "title", direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale){
        return requestService.findAllByLanguageAndStatus(
                pageable,
                utility.getLanguageStringForStoreInDB(locale),
                statusService.findByCode(WORKMAN_STATUS)
        );
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
        try{
            if (SecurityService.isCurrentUserHasRole("MANAGER")||
                    SecurityService.isCurrentUserHasRole("WORKMAN")){
                model.addAttribute(
                        "request",
                        requestService.findById(id)
                );
            }
            else
                model.addAttribute(
                        "request",
                        requestService.findByIdAndAuthor(
                                id,
                                accountService
                                        .getAccountByUsername(
                                                SecurityService.getCurrentUsername()
                                        )
                        )
                );
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            if (SecurityService.isCurrentUserHasRole("USER")) {
                return REDIRECT_TO_ACCESS_DENIED_PAGE;
            }
        }
        return "requests/request";
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.GET)
    public String getRequestForm(Model model) {
        model.addAttribute("request", new RequestForm());
        return CREATE_NEW_FORM_JSP_FILE;
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.POST)
    public String postRequestForm(
            @ModelAttribute("request") @Valid RequestForm form,
            BindingResult result,
            Model model,
            Locale locale) {
        Request request;
        Account author;
        Status status;
        try {
            request = toRequest(form, locale);
            author = accountService.getAccountByUsername(SecurityService.getCurrentUsername());
            status = statusService.findByCode(DEFAULT_STATUS);
            if (!result.hasErrors()) {
                request.setStatus(status);
                request.setAuthor(author);
                request.setUser(author);
                request.setClosed(status.isClose());
                logger.info("new request form creation: " + form.toString());
                requestService.newRequest(request);
            }
        }
        catch(WorkshopException e){
                logger.info("custom error message: " + e.getMessage());
                logger.error("custom error message: " + e.getMessage());
                model.addAttribute("message", e.getMessage());
        }
        return (result.hasErrors() ? CREATE_NEW_FORM_JSP_FILE : REDIRECT_TO_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'WORKMAN')")
    @RequestMapping(value = "/requests/{id}/edit", method = RequestMethod.GET)
    public String getEditRequestStatusForm(
            @PathVariable("id") Long id,
            Model model) {
        StatusForm statusForm = new StatusForm();
        try{
            statusForm.setRequest(requestService.findById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            //return "error";
        }
        model.addAttribute("statuses", statusForm);
        return "requests/edit-request-form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'WORKMAN')")
    @RequestMapping(value = "/requests/{id}/edit", method = RequestMethod.POST)
    public String putRequestStatus(
            @PathVariable("id") Long id,
            @ModelAttribute("statuses") @Valid StatusForm statusForm,
            BindingResult result,
            Model model) {
        Request request = requestService.findById(id);
        try {
            checkTheAuthorities(request.getStatus().getCode());
        }
        catch (WorkshopException e) {
            return REDIRECT_TO_ACCESS_DENIED_PAGE;
        }
        validateFields(statusForm.getStatus(), statusForm, result);
        Status newStatus = statusService.findByCode(statusForm.getStatus());
        try {
            request.setPrice(
                    Optional.ofNullable(statusForm.getPrice())
                            .orElseThrow(() -> new WorkshopException(WorkshopError.PRICE_NOT_FOUND_ERROR)));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
        }
        try {
            request.setCause(
                    Optional.ofNullable(statusForm.getCause())
                            .orElseThrow(() -> new WorkshopException(WorkshopError.CAUSE_NOT_FOUND_ERROR)));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
        }
        request.setStatus(newStatus);
        request.setUser(accountService.getAccountByUsername(SecurityService.getCurrentUsername()));
        request.setClosed(newStatus.isClose());
        if (!result.hasErrors())
            try{
                requestService.setRequestInfo(request);
            }catch(WorkshopException e){
                logger.info("custom error message: " + e.getMessage());
                logger.error("custom error message: " + e.getMessage());
                model.addAttribute("message", e.getMessage());
            }
        try{
            statusForm.setRequest(requestService.findById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            //return "error";
        }
        model.addAttribute("statuses", statusForm);
        return (result.hasErrors() ? UPDATE_FORM_JSP_FILE : getPathByAuthority());
    }

    private String getPathByAuthority() {
        if (SecurityService.isCurrentUserHasRole("MANAGER"))
            return REDIRECT_TO_MANAGER_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST;
        else
            return REDIRECT_TO_WORKMAN_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST;
    }

    private void validateFields(String status, StatusForm form, BindingResult result) {
        if (status.equals("ACCEPT") && form.getPrice() == null)
            result.rejectValue("price", "validation.text.error.required.field");
        if (status.equals("REJECT") && form.getCause().length() < 6)
            result.rejectValue("cause", "validation.text.error.from.six.to.two.five.five");
    }

    private void checkTheAuthorities(String status) throws WorkshopException{
        boolean check = true;
        if (SecurityService.isCurrentUserHasRole("MANAGER") && status.equals(MANAGER_STATUS))
            check = false;
        if (SecurityService.isCurrentUserHasRole("WORKMAN") && status.equals(WORKMAN_STATUS))
            check = false;

        if (check) throw new WorkshopException(WorkshopError.RIGHT_VIOLATION_ERROR);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "title",
                "description",
                "status",
                "price",
                "comment",
                "cause");
    }

    private Request toRequest(RequestForm form, Locale locale) {
        Request request = new Request();
        request.setTitle(form.getTitle());
        request.setPrice(BigDecimal.ZERO);
        request.setDescription(form.getDescription());
        request.setLanguage(utility.getLanguageStringForStoreInDB(locale));
        return request;
    }

}
