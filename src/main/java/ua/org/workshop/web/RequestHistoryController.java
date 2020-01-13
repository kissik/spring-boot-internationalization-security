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
import ua.org.workshop.service.RequestHistoryService;
import ua.org.workshop.service.StatusService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

@Controller
public class RequestHistoryController {

    private static final Logger logger = LogManager.getLogger(RequestHistoryController.class);
    private static final String REDIRECT_TO_REQUEST_HISTORY_ON_SUCCESSFUL_EDIT_REQUEST =
            "redirect:/requests-history/{id}?saved=true";

    @Autowired
    private RequestHistoryService requestHistoryService;
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

    @GetMapping("/requests-history")
    public String requestsHistory(Model model,
                           Locale locale) {
        try{
            model.addAttribute("requestsList", requestHistoryService
                    .getRequestListHistoryByLanguageAndAuthor(
                            getLanguageStringForStoreInDB(locale),
                            accountService.getAccountByUsername(getCurrentUsername())
                    ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/requests-list-history-user";
    }

    @GetMapping("/manager-requests-history")
    public String managerRequestsHistory(Model model,
                           Locale locale) {
        try{
            model.addAttribute("requestsList",  requestHistoryService
                    .getRequestListHistoryByLanguageAndUser(
                            getLanguageStringForStoreInDB(locale),
                            accountService.getAccountByUsername(getCurrentUsername())
                    ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        return "requests/requests-list-history";
    }

    @GetMapping("/workman-requests-history")
    public String workmanRequestsHistory(Model model,
                                  Locale locale) {
        try{
            model.addAttribute("requestsList",  requestHistoryService
                .getRequestListHistoryByLanguageAndUser(
                        getLanguageStringForStoreInDB(locale),
                        accountService.getAccountByUsername(getCurrentUsername())
                ));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/requests-list-history";
    }

    @RequestMapping(value = "/requests-history/{id}", method = RequestMethod.GET)
    public String getRequestHistory(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
        try{
            model.addAttribute("request", requestHistoryService.findById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "requests/request-history";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/requests-history/{id}/edit", method = RequestMethod.GET)
    public String getEditRequestHistoryStatusForm(
            @PathVariable("id") Long id,
            Model model) {
        //HistoryForm historyForm = new HistoryForm();
        try{
            //historyForm.setRequest(requestHistoryService.findById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        //model.addAttribute("history", historyForm);
        return "requests/edit-request-history-form";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/requests-history/{id}/edit", method = RequestMethod.POST)
    public String putRequestHistoryStatus(
            @PathVariable("id") Long id,
            //@ModelAttribute("history") @Valid HistoryForm historyForm,
            Model model) {
        try{
//            requestHistoryService.setRequestHistoryInfo(requestHistoryService.findById(id),
//                    accountService.getAccountByUsername(getCurrentUsername()),
//                    historyForm.getComment(),
//                    historyForm.getRating());
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return REDIRECT_TO_REQUEST_HISTORY_ON_SUCCESSFUL_EDIT_REQUEST;
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
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setAllowedFields("title", "description", "status");
//    }
//
//    private Request toRequest(RequestForm form, Locale locale) {
//        Request request = new Request();
//        request.setTitle(form.getTitle());
//        request.setPrice(BigDecimal.ZERO);
//        request.setDescription(form.getDescription());
//        request.setLanguage(getLanguageStringForStoreInDB(locale));
//        return request;
//    }

}
