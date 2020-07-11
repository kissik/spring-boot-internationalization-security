package ua.org.workshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.RequestRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RequestService {

    private final AccountService accountService;
    private final RequestRepository requestRepository;
    private final StatusService statusService;

    public RequestService(AccountService accountService,
                          RequestRepository requestRepository,
                          StatusService statusService) {
        this.accountService = accountService;
        this.requestRepository = requestRepository;
        this.statusService = statusService;
    }

    @Transactional(readOnly = false)
    public void newRequest(Request request) throws WorkshopException {
        log.info("Before New request creation");

        try {
            requestRepository.save(request);
        } catch (Exception e) {
            log.error(WorkshopError.DATABASE_CONNECTION_ERROR.message());
            throw new WorkshopException(WorkshopError.DATABASE_CONNECTION_ERROR);
        }
        log.info("New request was created: {}", request.toString());
    }

    public Page<Request> findAllByLanguageAndAuthor(Pageable pageable, String language, Account author) {
        return requestRepository
                .findAllByLanguageAndAuthor(pageable, language, author);
    }

    public Page<Request> findAllByLanguage(Pageable pageable, String language) {
        return requestRepository
                .findAllByLanguage(pageable, language);
    }

    public Request findById(Long id) throws WorkshopException {
        return requestRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopError.REQUEST_NOT_FOUND_ERROR));
    }

    @Transactional(readOnly = false)
    public void setRequestInfo(Request request) throws WorkshopException {
        try {
            requestRepository.saveAndFlush(request);
            log.info("Request was changed: {}", request.toString());
            if (request.getStatus().isClosed())
                requestRepository.delete(request);
            log.info("Request was deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new WorkshopException(WorkshopError.DATABASE_CONNECTION_ERROR);
        }
    }

    public Page<Request> findAllByLanguageAndStatus(
            Pageable page,
            String language,
            Status status) {
        return requestRepository
                .findAllByLanguageAndStatus(page, language, status);
    }

    public Request findByIdAndAuthor(Long id, Account author) throws WorkshopException {
        return requestRepository
                .findByIdAndAuthor(id, author)
                .orElseThrow(() -> new WorkshopException(WorkshopError.REQUEST_NOT_FOUND_ERROR));
    }

    public Page<Request> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }
}
