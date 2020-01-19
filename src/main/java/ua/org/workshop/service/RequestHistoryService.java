package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.RequestHistory;
import ua.org.workshop.domain.Status;
import ua.org.workshop.exception.WorkshopErrors;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.RequestHistoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestHistoryService {

    private static final Logger logger = LogManager.getLogger(RequestHistoryService.class);

    private final RequestHistoryRepository requestHistoryRepository;

    public RequestHistoryService(RequestHistoryRepository requestHistoryRepository){
        super();
        this.requestHistoryRepository = requestHistoryRepository;
    }

    public List<RequestHistory> getRequestListHistoryByLanguageAndAuthor(String language, Account author) throws WorkshopException{
        return requestHistoryRepository
                .getRequestListHistoryByLanguageAndAuthor(language, author)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_HISTORY_IS_EMPTY_ERROR));
    }

    public List<RequestHistory> getRequestListHistoryByLanguage(String language) throws WorkshopException{
        return requestHistoryRepository
                .getRequestListHistoryByLanguage(language)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_HISTORY_IS_EMPTY_ERROR));
    }

    public RequestHistory findById(Long id) throws WorkshopException{
        return requestHistoryRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_HISTORY_NOT_FOUND_ERROR));
    }

    public List<RequestHistory> getRequestListHistoryByLanguageAndStatus (
            String language,
            Status status) throws WorkshopException{
        return requestHistoryRepository
                .getRequestListHistoryByLanguageAndStatus(language, status)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_HISTORY_IS_EMPTY_ERROR));
    }

    public List<RequestHistory> getRequestListHistoryByLanguageAndUser(
            String language,
            Account user) throws WorkshopException{
            return requestHistoryRepository
                    .getRequestListHistoryByLanguageAndUser(language, user)
                    .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_HISTORY_IS_EMPTY_ERROR));
        }

    public Page<RequestHistory> findAllPage(Pageable pageable){
        return requestHistoryRepository.findAll(pageable);
    }
}
