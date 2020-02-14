package ua.org.workshop.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.service.HistoryRequestService;

@Controller
@RequiredArgsConstructor
public class RequestHistoryRestController {

    @Autowired
    private HistoryRequestService historyRequestService;

    @GetMapping(path = "/requests-history-pageable")
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
}
