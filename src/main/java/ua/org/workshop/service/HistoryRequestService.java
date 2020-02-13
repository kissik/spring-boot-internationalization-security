package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.domain.Status;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.RequestHistoryRepository;

@Service
@Transactional(readOnly = true)
public class HistoryRequestService {

    private static final Logger logger = LogManager.getLogger(HistoryRequestService.class);

    private final RequestHistoryRepository requestHistoryRepository;

    public HistoryRequestService(RequestHistoryRepository requestHistoryRepository){
        super();
        this.requestHistoryRepository = requestHistoryRepository;
    }

    public Page<HistoryRequest> findByLanguageAndAuthor(
            Pageable pageable, String language, Account author) {
        return requestHistoryRepository
                .findByLanguageAndAuthor(pageable, language, author);
    }

    public Page<HistoryRequest> findByLanguage(Pageable pageable, String language) {
        return requestHistoryRepository
                .findByLanguage(pageable, language);
    }

    public HistoryRequest findById(Long id) throws WorkshopException{
        return requestHistoryRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopError.REQUEST_HISTORY_NOT_FOUND_ERROR));
    }

    public Page<HistoryRequest> findByLanguageAndStatus (
            Pageable pageable,
            String language,
            Status status){
        return requestHistoryRepository
                .findByLanguageAndStatus(pageable, language, status);
    }

    public Page<HistoryRequest> findByLanguageAndUser(
            Pageable pageable,
            String language,
            Account user) throws WorkshopException{
            return requestHistoryRepository
                    .findByLanguageAndUser(pageable, language, user);
        }

    public Page<HistoryRequest> findAll(Pageable pageable){
        return requestHistoryRepository.findAll(pageable);
    }
}
