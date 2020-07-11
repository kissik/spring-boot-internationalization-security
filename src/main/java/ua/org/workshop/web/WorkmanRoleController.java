package ua.org.workshop.web;

import lombok.extern.slf4j.Slf4j;
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
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.SecurityService;
import ua.org.workshop.service.StatusService;
import ua.org.workshop.web.dto.RequestDTO;
import ua.org.workshop.web.dto.service.RequestDTOService;
import ua.org.workshop.web.form.WorkmanUpdateRequestForm;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/workman")
public class WorkmanRoleController {

    private static final String CURRENT_ROLE = "WORKMAN";

    private final AccountService accountService;
    private final MessageSource messageSource;
    private final RequestService requestService;
    private final StatusService statusService;

    public WorkmanRoleController(AccountService accountService,
                                 MessageSource messageSource,
                                 RequestService requestService,
                                 StatusService statusService) {
        this.accountService = accountService;
        this.messageSource = messageSource;
        this.requestService = requestService;
        this.statusService = statusService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "status");
    }

    @GetMapping("/requests")
    @ResponseBody
    Page<RequestDTO> workmanRequests(
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
        return requestDTOService.createDTOPage(pageable, locale, requestService.findAllByLanguageAndStatus(
                pageable,
                messageSource.getMessage(
                        ApplicationConstants.BUNDLE_LANGUAGE_FOR_REQUEST, null, locale),
                statusService.findByCode(ApplicationConstants.UPDATE_REQUEST_WORKMAN_VALID_STATUS)
        ));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getWorkmanPage() {
        return Pages.WORKMAN_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}",
            method = RequestMethod.GET)
    public String getRequest(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) throws IllegalArgumentException {
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                requestService.findById(id));
        return Pages.WORKMAN_REQUEST_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.GET)
    public String getEditRequestStatusForm(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) {
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                requestService.findById(id));
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.WORKMAN_UPDATE_REQUEST_FORM,
                new WorkmanUpdateRequestForm());
        return Pages.WORKMAN_UPDATE_REQUEST_FORM_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.POST)
    public String editWorkmanRequestStatus(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            @ModelAttribute(ApplicationConstants.ModelAttribute.Form.WORKMAN_UPDATE_REQUEST_FORM) @Valid WorkmanUpdateRequestForm form,
            BindingResult result,
            Model model) {
        Request request;
        Status newStatus;
        request = requestService.findById(id);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                request);
        if (result.hasErrors())
            return Pages.WORKMAN_UPDATE_REQUEST_FORM_PAGE;
        SecurityService.checkTheAuthorities(CURRENT_ROLE,
                    request.getStatus().getCode(),
                    ApplicationConstants.UPDATE_REQUEST_WORKMAN_VALID_STATUS);
        newStatus = statusService.findByCode(form.getStatus());
        request.setUser(accountService.getAccountByUsername(SecurityService.getCurrentUsername()));
        statusService
                .hasNextStatus(
                        request.getStatus(),
                        newStatus);
        request.setStatus(newStatus);
        request.setClosed(newStatus.isClosed());
        log.info("request : {}", request);
        requestService.setRequestInfo(request);
        return SecurityService.getPathByAuthority();
    }
}
