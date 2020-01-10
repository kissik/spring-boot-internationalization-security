package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RequestRepository;

import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.repository.StatusRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    public boolean newRequest(Request request, String username, String status, Errors errors) {
        boolean valid = !errors.hasErrors();

        if (valid) {
            Account author = accountService.getAccountByUsername(username);
            request.setStatus(statusService.findByCode(status));
            request.setAuthor(author);
            request.setUser(author);
            request.setClosed(false);

            requestRepository.save(request);
            logger.info(request.toString());
        }

        return valid;
    }

    public List<Request> getRequestListByLanguageAndAuthor(String language, Account author){
        return requestRepository.getRequestListByLanguageAndAuthor(language, author);
    }

    public List<Request> getRequestListByLanguage(String language){
        return requestRepository.getRequestListByLanguage(language);
    }

    public Optional<Request> findById(Long id){
        return requestRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void setRequestInfo(Request request, String status) {

        request.setStatus(statusService.findByCode(status));
        requestRepository.saveAndFlush(request);
    }
}
