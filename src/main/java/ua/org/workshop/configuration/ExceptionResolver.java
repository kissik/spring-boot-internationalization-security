package ua.org.workshop.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.web.AdminRoleController;
import ua.org.workshop.web.Pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import java.util.Locale;

/**
 * @author kissik
 */
@Component
public class ExceptionResolver extends AbstractHandlerExceptionResolver {
    private static final Logger LOGGER = LogManager.getLogger(AdminRoleController.class);

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o,
            Exception ex) {
        LOGGER.info("handleExceptionArray - Catching: " + ex.getClass().getSimpleName());
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
        LOGGER.info(simpleName);
        LOGGER.info(message);
        modelAndView.addObject(
                ApplicationConstants.ModelAttribute.ERROR_MESSAGE,
                message
                );

        return modelAndView;
    }
}
