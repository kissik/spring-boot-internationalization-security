package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RequestRepository;

import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.repository.StatusRepository;

@Service
@Transactional(readOnly = true)
public class RequestService  {

    private static final Logger logger = LogManager.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private AccountRepository accountRepository;

    public RequestService(RequestRepository requestRepository){
        super();
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = false)
    public boolean newRequest(Request request, String username, String status, Errors errors) {
        boolean valid = !errors.hasErrors();

        if (valid) {
            Account author = accountRepository.findByUsername(username);
            request.setStatus(statusRepository.findByCode(status));
            request.setAuthor(author);
            request.setUser(author);
            request.setClosed(false);

            requestRepository.save(request);
            logger.info(request.toString());
        }

        return valid;
    }

    public RequestRepository getRequestRepository() {
        return requestRepository;
    }
}
