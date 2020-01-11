package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void newRequest(Request request, Account author, Status status) throws WorkshopException {
        logger.info("Before New request creation");

        request.setStatus(status);
        request.setAuthor(author);
        request.setUser(author);
        request.setClosed(false);
        try {
            requestRepository.save(request);
        }
        catch(Exception e){
            logger.error(WorkshopErrors.DATABASE_CONNECTION_ERROR.message());
            throw new WorkshopException(WorkshopErrors.DATABASE_CONNECTION_ERROR);
            }
        logger.info("New request was created: " + request.toString());
    }

    public List<Request> getRequestListByLanguageAndAuthor(String language, Account author) throws WorkshopException{
        return requestRepository
                .getRequestListByLanguageAndAuthor(language, author)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_IS_EMPTY_ERROR));
    }

    public List<Request> getRequestListByLanguage(String language) throws WorkshopException{
        return requestRepository
                .getRequestListByLanguage(language)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_IS_EMPTY_ERROR));
    }

    public Request findById(Long id) throws WorkshopException{
        return requestRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_NOT_FOUND_ERROR));
    }

    @Transactional(readOnly = false)
    public void setRequestInfo(Request request, String status) throws WorkshopException{
        request.setStatus(statusService.findByCode(status));
        try{
            requestRepository.saveAndFlush(request);
        }
        catch(Exception e){
            logger.error(WorkshopErrors.DATABASE_CONNECTION_ERROR.message());
            throw new WorkshopException(WorkshopErrors.DATABASE_CONNECTION_ERROR);
        }
        logger.info("Request was changed: " + request.toString());
    }

    public List<Request> getRequestListByLanguageAndStatus (
            String language,
            Status status) throws WorkshopException{
        return requestRepository
                .getRequestListByLanguageAndStatus(language, status)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.REQUEST_LIST_IS_EMPTY_ERROR));
    }
}
