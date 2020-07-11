package ua.org.workshop.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.web.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author kissik
 */
@Slf4j
@Component
public class ExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o,
            Exception ex) {
        log.info("handleExceptionArray - Catching {} ", ex.getClass().getSimpleName());
        return errorModelAndView(httpServletRequest.getLocale(), ex);
    }

    private ModelAndView errorModelAndView(Locale locale, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        String simpleName = ex.getClass().getSimpleName();
        String message = simpleName.equals(WorkshopException.class.getSimpleName()) ?
                ex.getMessage() :
                ApplicationConstants.ModelAttribute.DEFAULT_ERROR_BUNDLE_MESSAGE;

        modelAndView.setViewName(Pages.WORSHOP_ERROR_PAGE);
        modelAndView.addObject(
                ApplicationConstants.ModelAttribute.EXCEPTION,
                simpleName);
        log.info(simpleName);
        log.info(message);
        modelAndView.addObject(
                ApplicationConstants.ModelAttribute.ERROR_MESSAGE,
                message
                );

        return modelAndView;
    }
}
