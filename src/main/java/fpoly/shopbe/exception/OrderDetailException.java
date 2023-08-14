package fpoly.shopbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderDetailException extends RuntimeException{
    public OrderDetailException(String message) {
        super(message);
    }
}
