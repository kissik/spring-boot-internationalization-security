package ua.org.workshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.domain.Status;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.StatusRepository;

@Service
@Transactional(readOnly = true)
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        super();
        this.statusRepository = statusRepository;
    }

    public StatusRepository getStatusRepository() {
        return statusRepository;
    }

    public Status findByCode(String status) throws WorkshopException {
        return statusRepository
                .findByCode(status)
                .orElseThrow(() -> new WorkshopException(WorkshopError.STATUS_NOT_FOUND_ERROR));
    }

    public boolean hasNextStatus(Status oldStatus, Status newStatus) throws WorkshopException {
        for (Status nextStatus : oldStatus.getNextStatuses()){
            if (nextStatus.getCode().equals(newStatus.getCode())) return true;
        }
        throw new WorkshopException(WorkshopError.NEXT_STATUS_CHECK_ERROR);
    }
}
