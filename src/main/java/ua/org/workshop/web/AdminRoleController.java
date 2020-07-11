package ua.org.workshop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Role;
import ua.org.workshop.service.*;
import ua.org.workshop.web.form.RoleForm;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminRoleController {

    private static final String CURRENT_ROLE = "ADMIN";

    @Autowired
    private AccountService accountService;
    @Autowired
    private HistoryRequestService historyRequestService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("role");
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getUsers(Model model) {
        return Pages.ADMIN_PAGE;
    }

    @GetMapping("/accounts")
    @ResponseBody
    Page<Account> workmanRequests(
            @PageableDefault(
                    page = ApplicationConstants.Page.PAGE_DEFAULT_VALUE,
                    size = ApplicationConstants.Page.SIZE_DEFAULT_VALUE)
            @SortDefault.SortDefaults({
                    @SortDefault(
                            sort = ApplicationConstants.Account.Sort.USERNAME,
                            direction = Sort.Direction.ASC),
                    @SortDefault(
                            sort = ApplicationConstants.Account.Sort.DATE_CREATED,
                            direction = Sort.Direction.DESC)
            })
                    Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping(path = "/history-requests")
    @ResponseBody
    Page<HistoryRequest> loadRequestsHistoryPage(
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
                    Pageable pageable) {
        return historyRequestService.findAll(pageable);
    }

    @GetMapping(path = "/requests")
    @ResponseBody
    Page<Request> loadRequestsPage(
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
                    Pageable pageable) {
        return requestService.findAll(pageable);
    }

    @RequestMapping(
            value = "/accounts/{" + ApplicationConstants.PathVariable.ID + "}",
            method = RequestMethod.GET)
    public String getAccount(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) {
        model.addAttribute(
                    ApplicationConstants.ModelAttribute.View.ACCOUNT,
                    accountService.getAccountById(id));
        return Pages.ADMIN_ACCOUNT_INFO_PAGE;
    }

    @RequestMapping(
            value = "/accounts/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.GET)
    public String getEditAccountRoleForm(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            Model model) {
        Account account = null;
        account = accountService.getAccountById(id);
        model.addAttribute(
                    ApplicationConstants.ModelAttribute.View.ROLES_LIST,
                    roleService.findAll());
        RoleForm roleForm = new RoleForm();

        String[] roles = account.getRoles()
                .stream()
                .map(Role::getCode)
                .toArray(String[]::new);

        roleForm.setRole(roles);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.View.ACCOUNT,
                account);
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.ROLE_FORM,
                roleForm);
        return Pages.ADMIN_ACCOUNT_EDIT_PAGE;
    }

    @RequestMapping(
            value = "/accounts/{" + ApplicationConstants.PathVariable.ID + "}/edit",
            method = RequestMethod.POST)
    public String putAccountRoles(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id,
            @ModelAttribute(ApplicationConstants.ModelAttribute.Form.ROLE_FORM) @Valid RoleForm roleForm,
            BindingResult result) {
        SecurityService.isCurrentUserHasRole(CURRENT_ROLE);
        if (!result.hasErrors()) {
            Account account = accountService.getAccountById(id);
            Collection<Role> roles = new HashSet<>();
            for (String roleStr : roleForm.getRole())
                roles.add(roleService.findByCode(roleStr));
            account.setRoles(roles);
            accountService.update(account);
        } else
            return Pages.ERROR_PAGE;
        return Pages.ADMIN_PAGE_REDIRECT_UPDATE_ACCOUNT_SUCCESS + id + "?update=true";
    }

    @RequestMapping(
            value = "/accounts/{" + ApplicationConstants.PathVariable.ID + "}/delete",
            method = RequestMethod.POST)
    public String deleteAccount(
            @PathVariable(ApplicationConstants.PathVariable.ID) Long id) {
        SecurityService.isCurrentUserHasRole(CURRENT_ROLE);
        Account deleteAccount = accountService.getAccountById(id);
        accountService.delete(deleteAccount.getId());
        return Pages.ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_SUCCESS;
    }
}
