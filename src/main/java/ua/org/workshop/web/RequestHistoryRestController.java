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
import ua.org.workshop.domain.RequestHistory;
import ua.org.workshop.service.RequestHistoryService;

@Controller
@RequiredArgsConstructor
public class RequestHistoryRestController {

    @Autowired
    private RequestHistoryService requestHistoryService;

    @GetMapping(path = "/requests-history-pageable")
    @ResponseBody
    Page<RequestHistory> loadRequestsHistoryPage(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "author", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return requestHistoryService.findAllPage(pageable);
    }
}
