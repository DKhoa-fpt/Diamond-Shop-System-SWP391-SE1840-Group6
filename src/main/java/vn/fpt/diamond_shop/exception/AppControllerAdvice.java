package vn.fpt.diamond_shop.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.fpt.diamond_shop.controller.BaseController;
import vn.fpt.diamond_shop.security.exception.BadRequestException;
import vn.fpt.diamond_shop.security.exception.ResourceNotFoundException;

@ControllerAdvice
@Log4j2
public class AppControllerAdvice extends BaseController {

    @ExceptionHandler(value = {
            BadRequestException.class,
            DiamondShopException.class,
            ResourceNotFoundException.class,
            RuntimeException.class
    })
    public ResponseEntity<?> badReq(RuntimeException e) {
        log.error(e.getMessage(), e);
        return err(e.getMessage(), null);
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<?> internalErr(Exception e) {
        log.error(e.getMessage(), e);
        return internalErr(e.getMessage(), e, null);
    }

    @ExceptionHandler(value = {
            Throwable.class
    })
    public ResponseEntity<?> throwable(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return internalErr(throwable.getMessage(), throwable, null);
    }
}
