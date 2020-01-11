package ua.org.workshop.service;

import org.springframework.stereotype.Service;
import ua.org.workshop.domain.Status;
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

    public Status findByCode(String status) {
        return statusRepository.findByCode(status);
    }

}
