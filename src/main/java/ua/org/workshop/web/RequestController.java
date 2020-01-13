package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import ua.org.workshop.dao.AccountDetails;
import ua.org.workshop.domain.Request;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.StatusService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class RequestController {

    private static final String CREATE_NEW_FORM_JSP_FILE = "requests/create-request-form";
    private static final String REDIRECT_TO_REQUESTS_LISTS_ON_SUCCESSFUL_NEW_REQUEST = "redirect:/requests";
    private static final String REDIRECT_TO_REQUEST_ON_SUCCESSFUL_EDIT_REQUEST = "redirect:/requests/{id}?saved=true";
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    private String getLanguageStringForStoreInDB(Locale locale){
        return messageSource.getMessage("locale.string", null, locale);
    }

    @GetMapping("/requests")
    public String requests(Model model,
                           Locale locale) {
        try{
            model.addAttribute("requestsList", requestService
                    .getRequestListByLanguageAndAuthorAndClosed(
                            getLanguageStringForStoreInDB(locale),
                            accountService.getAccountByUsername(getCurrentUsername()),
                            false
                    ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/requests-list";
    }

    @GetMapping("/manager-requests")
    public String managerRequests(Model model,
                           Locale locale) {
        try{
            model.addAttribute("requestsList",  requestService
                    .getRequestListByLanguageAndStatusAndClosed(
                            getLanguageStringForStoreInDB(locale),
                            statusService.findByCode(MANAGER_STATUS),
                            false
                    ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        return "requests/requests-list";
    }

    @GetMapping("/workman-requests")
    public String workmanRequests(Model model,
                                  Locale locale) {
        try{
            model.addAttribute("requestsList",  requestService
                .getRequestListByLanguageAndStatusAndClosed(
                        getLanguageStringForStoreInDB(locale),
                        statusService.findByCode(WORKMAN_STATUS),
                        false
                ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/requests-list";
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
        try{
            model.addAttribute("request", requestService.findById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/request";
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.GET)
    public String getRequestForm(Map<String, RequestForm> model) {
        model.put("request", new RequestForm());
        return CREATE_NEW_FORM_JSP_FILE;
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.POST)
    public String postRequestForm(
            @ModelAttribute("request") @Valid RequestForm form,
            BindingResult result,
            Model model,
            Locale locale) {

        logger.info("new request form creation: "+ form.toString());

        try{
            requestService.newRequest(toRequest(form, locale),
                accountService.getAccountByUsername(getCurrentUsername()),
                statusService.findByCode(DEFAULT_STATUS));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
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
            return "error";
        }
        model.addAttribute("statuses", statusForm);
        return "requests/edit-request-form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'WORKMAN')")
    @RequestMapping(value = "/requests/{id}/edit", method = RequestMethod.POST)
    public String putRequestStatus(
            @PathVariable("id") Long id,
            @ModelAttribute("statuses") @Valid StatusForm statusForm,
            Model model) {
        try{
            requestService.setRequestInfo(requestService.findById(id),
                    accountService.getAccountByUsername(getCurrentUsername()),
                    statusForm.getStatus());
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return REDIRECT_TO_REQUEST_ON_SUCCESSFUL_EDIT_REQUEST;
    }

    private AccountDetails getAuthentication(){
        SecurityContext securityCtx = SecurityContextHolder.getContext();
        return (AccountDetails) securityCtx.getAuthentication().getPrincipal();
    }

    private String getCurrentUsername() {

        return getAuthentication().getUsername();
    }

    private boolean isCurrentUserHasRole(String role){
        return getAuthentication().hasRole(role);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("title", "description", "status");
    }

    private Request toRequest(RequestForm form, Locale locale) {
        Request request = new Request();
        request.setTitle(form.getTitle());
        request.setPrice(BigDecimal.ZERO);
        request.setDescription(form.getDescription());
        request.setLanguage(getLanguageStringForStoreInDB(locale));
        return request;
    }

}
