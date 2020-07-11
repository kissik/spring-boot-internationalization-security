package ua.org.workshop.web.dto.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.web.UserRoleController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Slf4j
public class LocaleDTOService {
    private MessageSource messageSource;

    LocaleDTOService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    String getLocaleDate(LocalDate localeDate, Locale locale) {
        return localeDate.format(
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(locale)
        );
    }

    String getLocalePrice(Locale locale, BigDecimal price) {
        String result = messageSource
                .getMessage(ApplicationConstants.BUNDLE_CURRENCY_STRING,
                        null, locale);
        BigDecimal rate = tryParseBigDecimal(messageSource.getMessage(ApplicationConstants.BUNDLE_CURRENCY_RATE_INTEGER, null, locale),
                ApplicationConstants.APP_DEFAULT_RATING_VALUE);
        result = price.multiply(rate) + " " + result;
        return result;
    }

    private static BigDecimal tryParseBigDecimal(String value, BigDecimal defaultValue) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            log.error("Number format exception : {}", e.getMessage());
        }
        return defaultValue;
    }

}
