package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.*;
import ua.org.workshop.web.dto.service.HistoryRequestDTOService;
import ua.org.workshop.web.dto.service.RequestDTOService;
import ua.org.workshop.web.form.RequestForm;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserRoleController {

    private static final Logger LOGGER = LogManager.getLogger(UserRoleController.class);
    private static final String CURRENT_ROLE = "USER";
    @Autowired
    private RequestService requestService;
    @Autowired
    private HistoryRequestService historyRequestService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private MessageSource messageSource;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "title",
                "description");
    }

    @GetMapping("/requests")
    @ResponseBody
    public org.springframework.data.domain.Page userRequests(
            @PageableDefault(
                    page = ApplicationConstants.Page.PAGE_DEFAULT_VALUE,
                    size = ApplicationConstants.Page.SIZE_DEFAULT_VALUE)
            @SortDefault.SortDefaults({
                    @SortDefault(
                            sort = ApplicationConstants.Request.Sort.DATE_CREATED,
                            direction = Sort.Direction.DESC),
                    @SortDefault(
                            sort = ApplicationConstants.Request.Sort.TITLE,
                            direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale) {
        RequestDTOService requestDTOService = new RequestDTOService(messageSource);
        return requestDTOService.createDTOPage(pageable, locale, requestService.findAllByLanguageAndAuthor(
                pageable,
                messageSource.getMessage(
                        ApplicationConstants.BUNDLE_LANGUAGE_FOR_REQUEST, null, locale),
                accountService.getAccountByUsername(SecurityService.getCurrentUsername())
        ));
    }

    @GetMapping("/history-requests")
    @ResponseBody
    org.springframework.data.domain.Page userHistoryRequests(
            @PageableDefault(
                    page = ApplicationConstants.Page.PAGE_DEFAULT_VALUE,
                    size = ApplicationConstants.Page.SIZE_DEFAULT_VALUE)
            @SortDefault.SortDefaults({
                    @SortDefault(
                            sort = ApplicationConstants.HistoryRequest.Sort.DATE_CREATED,
                            direction = Sort.Direction.DESC),
                    @SortDefault(
                            sort = ApplicationConstants.HistoryRequest.Sort.TITLE,
                            direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale) {
        HistoryRequestDTOService historyRequestDTOService = new HistoryRequestDTOService(messageSource);
        return historyRequestDTOService.createDTOPage(pageable, locale, historyRequestService.findByLanguageAndAuthor(
                pageable,
                messageSource.getMessage(
                        ApplicationConstants.BUNDLE_LANGUAGE_FOR_REQUEST, null, locale),
                accountService.getAccountByUsername(SecurityService.getCurrentUsername())
        ));
    }

    @RequestMapping(
            value = "/page",
            method = RequestMethod.GET)
    public String getUserPage() {
        return Pages.USER_PAGE;
    }

    @RequestMapping(
            value = "/requests/new",
            method = RequestMethod.GET)
    public String getRequestForm(Model model) {
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.REQUEST_FORM,
                new RequestForm());
        return Pages.USER_CREATE_REQUEST_FORM;
    }

    @RequestMapping(
            value = "/requests/new",
            method = RequestMethod.POST)
    public String postRequestForm(
            @ModelAttribute(ApplicationConstants.ModelAttribute.Form.REQUEST_FORM) @Valid RequestForm form,
            BindingResult result,
            Model model,
            Locale locale) {
        Request request;
        Account author;
        Status status;
        try {
            request = toRequest(form, locale);
            author = accountService.getAccountByUsername(SecurityService.getCurrentUsername());
            status = statusService.findByCode(ApplicationConstants.REQUEST_DEFAULT_STATUS);
            if (!result.hasErrors()) {
                request.setStatus(status);
                request.setAuthor(author);
                request.setUser(author);
                request.setClosed(status.isClosed());
                requestService.newRequest(request);
            }
        } catch (WorkshopException e) {
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute(
                    ApplicationConstants.ModelAttribute.ERROR_MESSAGE,
                    e.getMessage());
            return Pages.ERROR_PAGE;
        }
        return (result.hasErrors() ? Pages.USER_CREATE_REQUEST_FORM : Pages.USER_PAGE_REDIRECT_NEW_REQUEST_SUCCESS);
    }

    private Request toRequest(RequestForm form, Locale locale) {
        Request request = new Request();
        request.setTitle(form.getTitle());
        request.setPrice(BigDecimal.ZERO);
        request.setDescription(form.getDescription());
        request.setLanguage(messageSource.getMessage(
                ApplicationConstants.BUNDLE_LANGUAGE_FOR_REQUEST, null, locale));
        return request;
    }
}
