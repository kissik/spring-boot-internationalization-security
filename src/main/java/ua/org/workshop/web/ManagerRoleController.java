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
import ua.org.workshop.web.form.ManagerUpdateRequestForm;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerRoleController {

    private static final String CURRENT_ROLE = "MANAGER";

    private final AccountService accountService;
    private final MessageSource messageSource;
    private final RequestService requestService;
    private final StatusService statusService;

    public ManagerRoleController(AccountService accountService,
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
                "status",
                "price",
                "cause");
    }

    @GetMapping("/requests")
    @ResponseBody
    Page<RequestDTO> managerRequests(
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
                statusService.findByCode(ApplicationConstants.UPDATE_REQUEST_MANAGER_VALID_STATUS))
        );
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getManagerPage() {
        return Pages.MANAGER_PAGE;
    }

    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}",
            method = RequestMethod.GET)
    public String getRequest(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) throws IllegalArgumentException {
        SecurityService.isCurrentUserHasRole(CURRENT_ROLE);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                requestService.findById(id)
                );
        return Pages.MANAGER_REQUEST_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.GET)
    public String getEditRequestStatusForm(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) {
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.MANAGER_UPDATE_REQUEST_FORM,
                new ManagerUpdateRequestForm());
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                requestService.findById(id));
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.MANAGER_UPDATE_REQUEST_FORM,
                new ManagerUpdateRequestForm());
        return Pages.MANAGER_UPDATE_REQUEST_FORM_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.POST)
    public String editManagerRequestStatus(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            @ModelAttribute(ApplicationConstants.ModelAttribute.Form.MANAGER_UPDATE_REQUEST_FORM) @Valid ManagerUpdateRequestForm form,
            BindingResult result,
            Model model) {
        Request request;
        request = requestService.findById(id);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.REQUEST,
                request);
        SecurityService.checkTheAuthorities(CURRENT_ROLE, request.getStatus().getCode(), ApplicationConstants.UPDATE_REQUEST_MANAGER_VALID_STATUS);
        validateFields(form, result);
        Status newStatus = statusService.findByCode(form.getStatus());
        statusService
                .hasNextStatus(
                        request.getStatus(),
                        newStatus);
        request.setPrice(
                Optional.ofNullable(form.getPrice())
                        .orElse(ApplicationConstants.APP_DEFAULT_PRICE));
        request.setCause(
                Optional
                        .ofNullable(form.getCause())
                        .orElse(ApplicationConstants.APP_STRING_DEFAULT_VALUE));
        request.setUser(accountService.getAccountByUsername(SecurityService.getCurrentUsername()));
        if (!result.hasErrors()){
            request.setStatus(newStatus);
            request.setClosed(newStatus.isClosed());
            log.info("Request : {}", request);
            requestService.setRequestInfo(request);
        }
        return (result.hasErrors() ? Pages.MANAGER_UPDATE_REQUEST_FORM_PAGE : SecurityService.getPathByAuthority());
    }

    private BindingResult validateFields(ManagerUpdateRequestForm form, BindingResult result) {
        if (form.getStatus().equals("ACCEPT") && form.getPrice() == null)
            result.rejectValue("price", "validation.text.error.required.field",
                    new String[]{"validation.text.error.required.field"}, "This field is required!");
        if (form.getStatus().equals("REJECT") && form.getCause().length() < 6)
            result.rejectValue("cause", "validation.text.error.from.six.to.two.five.five",
                    new String[]{"validation.text.error.from.six.to.two.five.five"}, "from 6 to 255 symbols!");
        return result;
    }
}
