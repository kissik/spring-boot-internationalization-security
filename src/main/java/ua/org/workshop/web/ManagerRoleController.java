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
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.*;
import ua.org.workshop.web.form.StatusForm;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
public class ManagerRoleController {

    private static final Logger LOGGER = LogManager.getLogger(ManagerRoleController.class);

    @Autowired
    private RequestService requestService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "status",
                "price",
                "cause");
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
                messageSource.getMessage(
                        ApplicationConstants.BUNDLE_LANGUAGE_FOR_REQUEST, null, locale),
                statusService.findByCode(ApplicationConstants.REQUEST_MANAGER_STATUS)
        );
    }

    @RequestMapping(value = "manager/page", method = RequestMethod.GET)
    public String getManagerPage(){
        return Pages.MANAGER_PAGE;
    }

    @RequestMapping(value = "manager/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
        try{
            if (SecurityService.isCurrentUserHasRole("MANAGER")){
                model.addAttribute(
                        "request",
                        requestService.findById(id)
                );
            }
            else
                return Pages.ACCESS_DENIED_PAGE_REDIRECT;
        }catch(WorkshopException e){
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
        }
        return Pages.MANAGER_REQUEST_PAGE;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "manager/requests/{id}/edit", method = RequestMethod.GET)
    public String getEditRequestStatusForm(
            @PathVariable("id") Long id,
            Model model) {
        StatusForm statusForm = new StatusForm();
        try{
            statusForm.setRequest(requestService.findById(id));
        }catch(WorkshopException e){
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("statuses", statusForm);
        return Pages.MANAGER_REQUEST_UPDATE_FORM_PAGE;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "manager/requests/{id}/edit", method = RequestMethod.POST)
    public String editManagerRequestStatus(
            @PathVariable("id") Long id,
            @ModelAttribute("statuses") @Valid StatusForm statusForm,
            BindingResult result,
            Model model) {
        Request request = requestService.findById(id);
        try {
            SecurityService.checkTheAuthorities(request.getStatus().getCode());
        }
        catch (WorkshopException e) {
            return Pages.ACCESS_DENIED_PAGE_REDIRECT;
        }
        if (result.hasErrors()) return Pages.MANAGER_REQUEST_UPDATE_FORM_PAGE;

        LOGGER.info(statusForm.toString());
        validateFields(statusForm, result);
        Status newStatus = statusService.findByCode(statusForm.getStatus());
        try {
            request.setPrice(
                    Optional.ofNullable(statusForm.getPrice())
                            .orElseThrow(() -> new WorkshopException(WorkshopError.PRICE_NOT_FOUND_ERROR)));
        }catch(WorkshopException e){
            LOGGER.error("custom error message: " + e.getMessage());
        }
        try {
            request.setCause(
                    Optional.ofNullable(statusForm.getCause())
                            .orElseThrow(() -> new WorkshopException(WorkshopError.CAUSE_NOT_FOUND_ERROR)));
        }catch(WorkshopException e){
            LOGGER.error("custom error message: " + e.getMessage());
        }
        request.setStatus(newStatus);
        request.setUser(accountService.getAccountByUsername(SecurityService.getCurrentUsername()));
        request.setClosed(newStatus.isClose());
        if (!result.hasErrors())
            try{
                requestService.setRequestInfo(request);
            }catch(WorkshopException e){
                LOGGER.error("custom error message: " + e.getMessage());
                model.addAttribute("message", e.getMessage());
            }
        try{
            statusForm.setRequest(requestService.findById(id));
        }catch(WorkshopException e){
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("statuses", statusForm);
        return (result.hasErrors() ? Pages.MANAGER_REQUEST_UPDATE_FORM_PAGE : SecurityService.getPathByAuthority());
    }

    private void validateFields(StatusForm form, BindingResult result) {
        if (form.getStatus().equals("ACCEPT") && form.getPrice() == null)
            result.rejectValue("price", "validation.text.error.required.field");
        if (form.getStatus().equals("REJECT") && form.getCause().length() < 6)
            result.rejectValue("cause", "validation.text.error.from.six.to.two.five.five");
    }
}
