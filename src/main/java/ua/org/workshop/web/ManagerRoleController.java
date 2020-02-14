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
import ua.org.workshop.web.dto.RequestDTO;
import ua.org.workshop.web.dto.service.RequestDTOService;
import ua.org.workshop.web.form.ManagerUpdateRequestForm;
import ua.org.workshop.web.form.WorkmanUpdateRequestForm;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerRoleController {

    private static final Logger LOGGER = LogManager.getLogger(ManagerRoleController.class);
    private static final String CURRENT_ROLE = "MANAGER";

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

    @GetMapping("/requests")
    @ResponseBody
    Page<RequestDTO> managerRequests(
            @PageableDefault(
                    page = ApplicationConstants.Pageable.PAGE_DEFAULT_VALUE,
                    size = ApplicationConstants.Pageable.SIZE_DEFAULT_VALUE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = ApplicationConstants.RequestField.DATE_CREATED, direction = Sort.Direction.DESC),
                    @SortDefault(sort = ApplicationConstants.RequestField.TITLE, direction = Sort.Direction.ASC)
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

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
    public String getRequest(@PathVariable(ApplicationConstants.PathVariable.ID) Long id, Model model) throws IllegalArgumentException {
        try {
            if (SecurityService.isCurrentUserHasRole("MANAGER")) {
                model.addAttribute(
                        ApplicationConstants.ModelAttribute.REQUEST,
                        requestService.findById(id)
                );
            } else
                return Pages.ACCESS_DENIED_PAGE_REDIRECT;
        } catch (WorkshopException e) {
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute(ApplicationConstants.ModelAttribute.MESSAGE, e.getMessage());
        }
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
                ApplicationConstants.ModelAttribute.MANAGER_UPDATE_REQUEST_FORM,
                new ManagerUpdateRequestForm());
        Request request;
        try {
            request = requestService.findById(id);
        } catch (WorkshopException e) {
            model.addAttribute(
                    ApplicationConstants.ModelAttribute.MESSAGE,
                    e.getMessage()
            );
            return Pages.ERROR_PAGE;
        }
        model.addAttribute(
                ApplicationConstants.ModelAttribute.REQUEST,
                request);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.WORKMAN_UPDATE_REQUEST_FORM,
                new ManagerUpdateRequestForm());
        return Pages.MANAGER_UPDATE_REQUEST_FORM_PAGE;
    }

    @PreAuthorize("hasRole('" + CURRENT_ROLE + "')")
    @RequestMapping(
            value = "/requests/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.POST)
    public String editManagerRequestStatus(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            @ModelAttribute(ApplicationConstants.ModelAttribute.MANAGER_UPDATE_REQUEST_FORM) @Valid ManagerUpdateRequestForm form,
            BindingResult result,
            Model model) {
        Request request;
        try {
            request = requestService.findById(id);
        } catch (WorkshopException e) {
            model.addAttribute(
                    ApplicationConstants.ModelAttribute.MESSAGE,
                    e.getMessage()
            );
            return Pages.ERROR_PAGE;
        }
        model.addAttribute(
                ApplicationConstants.ModelAttribute.REQUEST,
                request);
        try {
            SecurityService.checkTheAuthorities(CURRENT_ROLE, request.getStatus().getCode(), ApplicationConstants.UPDATE_REQUEST_MANAGER_VALID_STATUS);
        } catch (WorkshopException e) {
            if (e.getErrorCode().equals(WorkshopError.RIGHT_VIOLATION_ERROR.code()))
                return Pages.ACCESS_DENIED_PAGE_REDIRECT;
            return Pages.ERROR_PAGE;
        }
        result = validateFields(form, result);
        Status newStatus = statusService.findByCode(form.getStatus());
        request.setPrice(
                Optional.ofNullable(form.getPrice())
                        .orElse(ApplicationConstants.APP_DEFAULT_PRICE));
        request.setCause(
                Optional
                        .ofNullable(form.getCause())
                        .orElse(ApplicationConstants.APP_STRING_DEFAULT_VALUE));
        request.setUser(accountService.getAccountByUsername(SecurityService.getCurrentUsername()));

        if (!result.hasErrors())
            try {
                request.setStatus(newStatus);
                request.setClosed(newStatus.isClose());
                LOGGER.info(request);
                requestService.setRequestInfo(request);
            } catch (WorkshopException e) {
                LOGGER.error("custom error message: " + e.getMessage());
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
