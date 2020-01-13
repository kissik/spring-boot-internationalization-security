package ua.org.workshop.service;

import org.springframework.stereotype.Service;
import ua.org.workshop.domain.Status;
import ua.org.workshop.exception.WorkshopErrors;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.StatusRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatusService  {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){
        super();
        this.statusRepository = statusRepository;
    }

    public StatusRepository getStatusRepository() {
        return statusRepository;
    }

    public Status findByCode(String status) throws WorkshopException {
        return statusRepository
                .findByCode(status)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.STATUS_NOT_FOUND_ERROR));
    }

}
