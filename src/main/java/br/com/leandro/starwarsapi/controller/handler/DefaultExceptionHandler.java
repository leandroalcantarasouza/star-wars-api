package br.com.leandro.starwarsapi.controller.handler;

import br.com.leandro.starwarsapi.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.*;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final String ERROR = "error";
    private static final String VIOLATIONS = "violations";
    private static final String TICKET = "ticket";
    private static final String UNKNOWN_ERROR = "UnknownError";
    private static final String REQUEST_ERROR = "RequestError";
    private static final String VALIDATION_FAILURE = "ValidationFailure";

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.error("handleConstraintViolationException -" , ex);
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, resolveMessage(VALIDATION_FAILURE));
        map.put(VIOLATIONS, convertConstraintViolationsInRestFieldMessage(ex.getConstraintViolations()));

        return map;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error("handleIllegalArgumentException -" , ex);
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, resolveMessage(VALIDATION_FAILURE));
        map.put(VIOLATIONS, ex.getMessage());

        return map;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    public @ResponseBody Map<String, Object> handleBusinessException(BusinessException ex) {
        LOGGER.error("handleBusinessException -" , ex);
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, resolveMessage(VALIDATION_FAILURE));

        List<String> violations = new ArrayList<>();
        violations.add(resolveMessage(ex.getMessageCode(), ex.getMessageArgs()));
        map.put(VIOLATIONS, violations);

        return map;
    }

    private List<RestFieldMessage> convertConstraintViolationsInRestFieldMessage(Set<ConstraintViolation<?>> constraintViolations) {
        List<RestFieldMessage> result = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            result.add(new RestFieldMessage(constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage(),
                    constraintViolation.getInvalidValue()));
        }
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Map<String, Object> handleUncaughtException(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        String resolveMessage = resolveMessage(UNKNOWN_ERROR);
        map.put(ERROR, resolveMessage);
        long timeInMillis = Instant.now().toEpochMilli();
        map.put(TICKET, timeInMillis);
        LOGGER.error("handleUncaughtException - " + resolveMessage + "[Ticket: " + timeInMillis + "]", ex);
        return map;
    }

    private String resolveMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, new Locale("pt", "BR"));
    }
}
