package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import ua.org.workshop.dao.AccountDetails;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.StatusService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

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

    private static final String VN_NEW_FORM = "requests/create-request-form";
    private static final String VN_NEW_OK = "redirect:/requests";
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
        model.put("requestsList",
                requestService
                        .getRequestRepository()
                        .getRequestListByLanguageAndAuthor(
                                getLanguageStringForStoreInDB(locale),
                                accountService.getAccountByUsername(getCurrentUsername())
                        ));
        return "requests/requests-list";
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

    private String getCurrentUsername() {
        SecurityContext securityCtx = SecurityContextHolder.getContext();
        Authentication auth = securityCtx.getAuthentication();
        return ((AccountDetails)auth.getPrincipal()).getUsername();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("title", "description");
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
