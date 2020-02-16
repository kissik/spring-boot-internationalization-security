package ua.org.workshop.web.dto.service;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.web.dto.HistoryRequestDTO;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class HistoryRequestDTOService {
    private MessageSource messageSource;
    private StatusDTOService statusDTOService;
    private AccountDTOService accountDTOService;
    private LocaleDTOService localeDTOService;

    public HistoryRequestDTOService(MessageSource messageSource) {
        this.messageSource = messageSource;
        statusDTOService = new StatusDTOService(messageSource);
        accountDTOService = new AccountDTOService(messageSource);
        localeDTOService = new LocaleDTOService(messageSource);
    }

    private HistoryRequestDTO getDTOHistoryRequest(Locale locale, HistoryRequest historyRequest) {
        HistoryRequestDTO historyRequestDTO = new HistoryRequestDTO(historyRequest);
        historyRequestDTO.setDateCreated(
                localeDTOService.getLocaleDate(
                        historyRequest.getDateCreated(),
                        locale)
        );
        historyRequestDTO.setDateUpdated(
                localeDTOService.getLocaleDate(
                        historyRequest.getDateUpdated(),
                        locale)
        );
        historyRequestDTO.setPrice(localeDTOService.getLocalePrice(locale, historyRequest.getPrice()));
        historyRequestDTO.setCause(Optional.ofNullable(historyRequest.getCause())
                .orElse(ApplicationConstants.APP_STRING_DEFAULT_VALUE));
        historyRequestDTO.setStatus(statusDTOService.getStatusDTO(historyRequest.getStatus(), locale));
        historyRequestDTO.setAuthorDTO(accountDTOService.getAccountDTO(historyRequest.getAuthor()));
        historyRequestDTO.setUser(accountDTOService.getAccountDTO(historyRequest.getUser()));
        historyRequestDTO.setReview(Optional.ofNullable(historyRequest.getReview())
                .orElse(ApplicationConstants.APP_STRING_DEFAULT_VALUE));

        return historyRequestDTO;
    }

    public Page<HistoryRequestDTO> createDTOPage(Pageable pageable, Locale locale, Page<HistoryRequest> page) {
        Page<HistoryRequestDTO> dtoPage = new PageImpl(formatRequest(locale, page.toList()), pageable, page.getTotalElements());
        return dtoPage;
    }

    private List<HistoryRequestDTO> formatRequest(Locale locale, List<HistoryRequest> historyRequestsList) {
        return historyRequestsList
                .stream()
                .map((historyRequest) -> getDTOHistoryRequest(locale, historyRequest))
                .collect(Collectors.toList());
    }
}
