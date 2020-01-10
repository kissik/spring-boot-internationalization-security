package ua.org.workshop.web;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.zip.DataFormatException;

@Controller
public class ExceptionsController {
    private static final Logger logger = LogManager.getLogger(ExceptionsController.class);

    /**
     * Whoops, throw an IOException
     */
    @RequestMapping(value = "/ioexception", method = RequestMethod.GET)
    public String throwAnException(Locale locale, Model model) throws IOException {

        logger.info("This will throw an IOException");

        boolean throwException = true;

        if (throwException) {
            throw new IOException("This is my IOException");
        }

        return "error";
    }

    /**
     * Catch IOException and redirect to a 'personal' page.
     */
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException ex) {

        logger.info("handleIOException - Catching: " + ex.getClass().getSimpleName());
        return errorModelAndView(ex);
    }

    /**
     * Get the users details for the 'personal' page
     */
    private ModelAndView errorModelAndView(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("name", ex.getClass().getSimpleName());
        //modelAndView.addObject("user", userDao.getFullName());

        return modelAndView;
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String throwNoHandlerFoundException(Locale locale, Model model)
            throws NoHandlerFoundException {

        logger.info("This will throw a NoHandlerFoundException, which is Spring's 404 not found");

        boolean throwException = true;

        if (throwException) {
            throw new NoHandlerFoundException(RequestMethod.GET.name(), "/404", null);
        }

        return "error";
    }

    @RequestMapping(value = "/nullpointer", method = RequestMethod.GET)
    public String throwNullPointerException(Locale locale, Model model) throws NoHandlerFoundException {

        logger.info("This will throw a NullPointerException");

        @SuppressWarnings("null")
        String str = null; // Ensure that this is null.
        str.length();

        return "error";
    }

    @ExceptionHandler({ NullPointerException.class, NoHandlerFoundException.class })
    public ModelAndView handleExceptionArray(Exception ex) {

        logger.info("handleExceptionArray - Catching: " + ex.getClass().getSimpleName());
        return errorModelAndView(ex);
    }

    /**
     * Throw a DataFormatException
     */
    @RequestMapping(value = "/dataformat", method = RequestMethod.GET)
    public String throwDataFormatException(Locale locale, Model model) throws DataFormatException {

        logger.info("This will throw an DataFormatException");

        boolean throwException = true;

        if (throwException) {
            throw new DataFormatException("This is my DataFormatException");
        }

        return "error";
    }

    /**
     * If you add/alter in the ResponseStatus - then the server won't cope. Set
     * to OK and you get a blank screen. Set to an error (300+) and you'll see
     * the web server's default page - so go and fix the server configuration.
     */
    @ExceptionHandler(DataFormatException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "My Response Status Change....!!")
    public void handleDataFormatException(DataFormatException ex, HttpServletResponse response) {

        logger.info("Handlng DataFormatException - Catching: " + ex.getClass().getSimpleName());
    }
}
