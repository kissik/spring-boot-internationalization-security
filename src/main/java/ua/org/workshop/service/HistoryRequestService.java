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
import ua.org.workshop.repository.HistoryRequestRepository;

@Service
@Transactional(readOnly = true)
public class HistoryRequestService {

    private static final Logger LOGGER = LogManager.getLogger(HistoryRequestService.class);

    private final HistoryRequestRepository historyRequestRepository;

    public HistoryRequestService(HistoryRequestRepository historyRequestRepository) {
        super();
        this.historyRequestRepository = historyRequestRepository;
    }

    public Page<HistoryRequest> findByLanguageAndAuthor(
            Pageable pageable, String language, Account author) {
        return historyRequestRepository
                .findByLanguageAndAuthor(pageable, language, author);
    }

    public Page<HistoryRequest> findByLanguage(Pageable pageable, String language) {
        return historyRequestRepository
                .findByLanguage(pageable, language);
    }

    public HistoryRequest findById(Long id) throws WorkshopException {
        return historyRequestRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopError.HISTORY_REQUEST_NOT_FOUND_ERROR));
    }

    public Page<HistoryRequest> findByLanguageAndStatus(
            Pageable pageable,
            String language,
            Status status) {
        return historyRequestRepository
                .findByLanguageAndStatus(pageable, language, status);
    }

    public Page<HistoryRequest> findByLanguageAndUser(
            Pageable pageable,
            String language,
            Account user) throws WorkshopException {
        return historyRequestRepository
                .findByLanguageAndUser(pageable, language, user);
    }

    public Page<HistoryRequest> findAll(Pageable pageable) {
        return historyRequestRepository.findAll(pageable);
    }

    @Transactional(readOnly = false)
    public void update(HistoryRequest historyRequest) throws WorkshopException {
        try {
            historyRequestRepository.save(historyRequest);
        }catch (Exception e){
            throw new WorkshopException(WorkshopError.HISTORY_REQUEST_UPDATE_ERROR);
        }
    }
}
