package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.exception.WorkshopErrors;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.RequestRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestService  {

    private static final Logger logger = LogManager.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    @Autowired
    private StatusService statusService;
    @Autowired
    private AccountService accountService;

    public RequestService(RequestRepository requestRepository){
        super();
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = false)
    public void newRequest(Request request) throws WorkshopException {
        logger.info("Before New request creation");

        try {
            requestRepository.save(request);
        }
        catch(Exception e){
            logger.error(WorkshopErrors.DATABASE_CONNECTION_ERROR.message());
            throw new WorkshopException(WorkshopErrors.DATABASE_CONNECTION_ERROR);
            }
        logger.info("New request was created: " + request.toString());
    }

    public Page<Request> findAllByLanguageAndAuthor(Pageable pageable, String language, Account author){
        return requestRepository
                .findAllByLanguageAndAuthor(pageable, language, author);
    }

    public Page<Request> findAllByLanguage(Pageable pageable, String language){
        return requestRepository
                .findAllByLanguage(pageable, language);
    }

    public Request findById(Long id) throws WorkshopException{
        return requestRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_NOT_FOUND_ERROR));
    }

    @Transactional(readOnly = false)
    public void setRequestInfo(Request request) throws WorkshopException{
        try{
            requestRepository.saveAndFlush(request);
            logger.info("Request was changed: " + request.toString());
            if (request.getStatus().isClose())
                requestRepository.delete(request);
            logger.info("Request was deleted");
        }
        catch(Exception e){
            logger.error(e.getMessage());
            throw new WorkshopException(WorkshopErrors.DATABASE_CONNECTION_ERROR);
        }
    }

    public Page<Request> findAllByLanguageAndStatus (
            Pageable page,
            String language,
            Status status){
        return requestRepository
                .findAllByLanguageAndStatus(page, language, status);
    }

    public Request findByIdAndAuthor(Long id, Account author) throws WorkshopException{
        return requestRepository
                .findByIdAndAuthor(id, author)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_NOT_FOUND_ERROR));
    }

    public Page<Request> findAll(Pageable pageable){
        return requestRepository.findAll(pageable);
    }
}
