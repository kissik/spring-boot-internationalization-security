package ua.org.workshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class Utility {

    @Autowired
    MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    public String getLanguageStringForStoreInDB(Locale locale){
        return messageSource.getMessage("locale.string", null, locale);
    }

}
