package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Role;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.*;
import ua.org.workshop.web.form.RoleForm;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@Controller
public class AdminRoleController {

    private static final Logger LOGGER = LogManager.getLogger(AdminRoleController.class);

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

    @RequestMapping(value = "/admin/page", method = RequestMethod.GET)
    public String getUsers(Model model) {
        return Pages.ADMIN_PAGE;
    }

    @GetMapping("/admin/accounts")
    @ResponseBody
    Page<Account> workmanRequests(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "username", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping(path = "/admin/history-requests")
    @ResponseBody
    Page<HistoryRequest> loadRequestsHistoryPage(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "author", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return historyRequestService.findAll(pageable);
    }

    @GetMapping(path = "/admin/requests")
    @ResponseBody
    Page<Request> loadRequestsPage(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "author", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return requestService.findAll(pageable);
    }

    @RequestMapping(value = "/admin/accounts/{id}", method = RequestMethod.GET)
    public String getAccount(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("account", accountService.getAccountById(id));
        } catch (WorkshopException e) {
            LOGGER.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return Pages.ADMIN_ACCOUNT_INFO_PAGE;
    }

    @RequestMapping(value = "/admin/accounts/{id}/edit", method = RequestMethod.GET)
    public String getEditAccountRoleForm(
            @PathVariable("id") Long id,
            Model model) {
        try {
            Account account = accountService.getAccountById(id);
            RoleForm roleForm = new RoleForm();

            String[] roles = account.getRoles()
                    .stream()
                    .map(Role::getCode)
                    .toArray(String[]::new);

            roleForm.setRole(roles);
            model.addAttribute("account", account);
            model.addAttribute("rolesList", roleService.findAll());
            model.addAttribute("role", roleForm);
            LOGGER.info("form roles:" + roleForm.toString());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "access-denied";
        }
        return Pages.ADMIN_ACCOUNT_EDIT_PAGE;
    }

    @RequestMapping(value = "/admin/accounts/{id}/edit", method = RequestMethod.POST)
    public String putAccountRoles(
            @PathVariable("id") Long id,
            @ModelAttribute("role") @Valid RoleForm roleForm,
            BindingResult result) {
        LOGGER.info(roleForm.getRole());
        if (!result.hasErrors()) {
            Account account = accountService.getAccountById(id);
            Collection<Role> roles = new HashSet<>();
            for (String roleStr : roleForm.getRole())
                roles.add(roleService.findByCode(roleStr));
            account.setRoles(roles);
            accountService.update(account);
        }
        return Pages.ADMIN_PAGE_REDIRECT_UPDATE_ACCOUNT_SUCCESS + id + "?update=true";
    }

    @RequestMapping(value = "/admin/accounts/{id}/delete", method = RequestMethod.POST)
    public String deleteAccount(
            @PathVariable("id") Long id) {
        try {
            Account deleteAccount = accountService.getAccountById(id);

            if (!SecurityService.isCurrentUserHasRole(ApplicationConstants.APP_SUPERUSER_ROLE)) {
                LOGGER.error("user has no rights to delete accounts!");
                throw new WorkshopException(WorkshopError.REQUEST_UPDATE_ERROR);
            }
            LOGGER.info("try to delete account " + id);
            accountService.delete(deleteAccount.getId());
        } catch (WorkshopException e) {
            LOGGER.error("delect account error : " + e.getMessage());
            return Pages.ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_FAILED;
        }
        return Pages.ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_SUCCESS;
    }
}
