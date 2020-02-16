package ua.org.workshop.web.dto.service;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Request;
import ua.org.workshop.web.dto.RequestDTO;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestDTOService {
    private MessageSource messageSource;
    private StatusDTOService statusDTOService;
    private AccountDTOService accountDTOService;
    private LocaleDTOService localeDTOService;

    public RequestDTOService(MessageSource messageSource) {
        this.messageSource = messageSource;
        statusDTOService = new StatusDTOService(messageSource);
        accountDTOService = new AccountDTOService(messageSource);
        localeDTOService = new LocaleDTOService(messageSource);
    }

    private RequestDTO getDTORequest(Locale locale, Request request) {
        RequestDTO requestDTO = new RequestDTO(request);
        requestDTO.setDateCreated(
                localeDTOService.getLocaleDate(
                        request.getDateCreated(),
                        locale)
        );
        requestDTO.setDateUpdated(
                localeDTOService.getLocaleDate(
                        request.getDateUpdated(),
                        locale)
        );
        requestDTO.setPrice(localeDTOService.getLocalePrice(locale, request.getPrice()));
        requestDTO.setCause(Optional.ofNullable(request.getCause())
                .orElse(ApplicationConstants.APP_STRING_DEFAULT_VALUE));
        requestDTO.setStatus(statusDTOService.getStatusDTO(request.getStatus(), locale));
        requestDTO.setAuthorDTO(accountDTOService.getAccountDTO(request.getAuthor()));
        requestDTO.setUser(accountDTOService.getAccountDTO(request.getUser()));

        return requestDTO;
    }

    public Page<RequestDTO> createDTOPage(Pageable pageable, Locale locale, Page<Request> page) {
        Page<RequestDTO> dtoPage = new PageImpl(formatRequest(locale, page.toList()), pageable, page.getTotalElements());
        return dtoPage;
    }

    private List<RequestDTO> formatRequest(Locale locale, List<Request> requestsList) {
        return requestsList
                .stream()
                .map((request) -> getDTORequest(locale, request))
                .collect(Collectors.toList());
    }

}
