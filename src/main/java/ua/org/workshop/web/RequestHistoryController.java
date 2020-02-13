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
import org.springframework.web.bind.annotation.*;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.HistoryRequestService;
import ua.org.workshop.service.SecurityService;

import java.util.Locale;

@Controller
public class RequestHistoryController {

    private static final Logger LOGGER = LogManager.getLogger(RequestHistoryController.class);

    @Autowired
    private HistoryRequestService historyRequestService;
    @Autowired
    private AccountService accountService;
    private Utility utility = new Utility();

    @GetMapping("user/history-requests")
    @ResponseBody
    Page<HistoryRequest> userHistoryRequests(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "title", direction = Sort.Direction.ASC)
            })
                    Pageable pageable, Locale locale){
        return historyRequestService.findByLanguageAndAuthor(
                pageable,
                utility.getLanguageStringForStoreInDB(locale),
                accountService.getAccountByUsername(SecurityService.getCurrentUsername())
        );
    }
}
