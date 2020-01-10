package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import ua.org.workshop.dao.AccountDetails;
import ua.org.workshop.domain.Request;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.StatusService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class RequestController {

    public class RequestForm{

        private String title;
        private String description;

        @NotNull
        @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @NotNull
        @Size(min = 6, max = 255, message = "Can't be less than 6 or more than 255 characters")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String toString() {
            return new StringBuilder()
                    .append(" title:" + title)
                    .append(" description:" + description)
                    .toString();
        }
    }

    public class StatusForm{
        private String status;

        @NotNull(message = "This field is required!")
        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }

        public String toString() {
            return new StringBuilder()
                    .append(" status:" + status)
                    .toString();
        }
    }

    private static final String VN_NEW_FORM = "requests/create-request-form";
    private static final String VN_NEW_OK = "redirect:/requests";
    private static final String VN_EDIT_OK = "redirect:/requests/{id}?saved=true";
    private static final String DEFAULT_STATUS = "REGISTER";

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
    public String requests(Map<String, Object> model,
                           Locale locale) {
        List<Request> requestsList = null;

        if (isCurrentUserHasRole("MANAGER"))
            requestsList = requestService
                    .getRequestListByLanguage(
                            getLanguageStringForStoreInDB(locale)
                    );
        else
            requestsList = requestService
                    .getRequestListByLanguageAndAuthor(
                            getLanguageStringForStoreInDB(locale),
                            accountService.getAccountByUsername(getCurrentUsername())
                    );

        model.put("requestsList", requestsList);
        return "requests/requests-list";
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable("id") Long id, Map<String, Object> model) throws IllegalArgumentException{
        try{
            Optional<Request> request = requestService.findById(id);
            model.put("request", request.get());
        }catch(IllegalArgumentException e){
            model.put("error", e.getMessage());
            return "access-denied";
        }
        return "requests/request";
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.GET)
    public String getRequestForm(Map<String, Object> model) {
        RequestForm requestForm = new RequestForm();
        model.put("request", requestForm);
        model.put("method", "post");

        return VN_NEW_FORM;
    }

    @RequestMapping(value = "/requests/new", method = RequestMethod.POST)
    public String postRequestForm(
            @ModelAttribute("request") @Valid RequestForm form,
            BindingResult result, Map<String, Object> model,
            Locale locale) {

        logger.info(form.toString());

        requestService.newRequest(toRequest(form, locale), getCurrentUsername(), DEFAULT_STATUS, result);

        model.put("method", "post");

        return (result.hasErrors() ? VN_NEW_FORM : VN_NEW_OK);
    }

    @RequestMapping(value = "/requests/{id}/edit", method = RequestMethod.GET)
    public String getEditRequestStatusForm(
            @PathVariable("id") Long id,
            Map<String, Object> model) {
        try{
            Request request = requestService.findById(id).get();

            model.put("statusList", request.getStatus().getNextStatuses());
            logger.info("statuses list: " + request.getStatus().getNextStatuses().toString());
            model.put("originalRequest", request);
            logger.info("originalRequest:" + request.toString());
            model.put("statuses", new StatusForm());

        }catch(IllegalArgumentException e){
            model.put("error", e.getMessage());
            return "access-denied";
        }catch(NullPointerException e){
            model.put("error", e.getMessage());
            return "access-denied";
        }
        return "requests/edit-request-form";
    }

    @RequestMapping(value = "/requests/{id}/edit", method = RequestMethod.POST)
    public String putRequestStatus(
            @PathVariable("id") Long id,
            @ModelAttribute("statuses") @Valid StatusForm statusForm,
            BindingResult result,
            Map<String, Object> model) {

        Request requestOrigin;
        requestOrigin = requestService.findById(id).get();

        requestService.setRequestInfo(requestOrigin, statusForm.getStatus());

        model.put("title", requestOrigin.getTitle());
        return VN_EDIT_OK;
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
