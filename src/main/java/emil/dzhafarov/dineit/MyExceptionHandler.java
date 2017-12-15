package emil.dzhafarov.dineit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> unauthorized(HttpServletRequest req, Exception exception) {
       return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}