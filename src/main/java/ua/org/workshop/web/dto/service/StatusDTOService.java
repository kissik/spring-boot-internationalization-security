package ua.org.workshop.web.dto.service;

import org.springframework.context.MessageSource;
import ua.org.workshop.domain.Status;
import ua.org.workshop.service.ApplicationConstants;
import ua.org.workshop.web.dto.StatusDTO;

import java.util.AbstractMap;
import java.util.Locale;
import java.util.stream.Collectors;

public class StatusDTOService {
    private MessageSource messageSource;

    public StatusDTOService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public StatusDTO getStatusDTO(Status status, Locale locale) {
        StatusDTO statusDTO = new StatusDTO(status);
        statusDTO.setValue(messageSource.getMessage(ApplicationConstants.BUNDLE_REQUEST_STATUS_PREFIX
                + status.getCode().toLowerCase(), null, locale));
        statusDTO.setNextStatuses(
                status
                        .getNextStatuses()
                        .stream()
                        .map(nextStatus -> new AbstractMap.SimpleEntry<>(
                                nextStatus.getCode(),
                                messageSource.getMessage(
                                        ApplicationConstants.BUNDLE_REQUEST_STATUS_PREFIX
                                                + nextStatus.getCode().toLowerCase(), null, locale)
                        ))
                        .collect(Collectors.toList())
        );
        return statusDTO;
    }

}
