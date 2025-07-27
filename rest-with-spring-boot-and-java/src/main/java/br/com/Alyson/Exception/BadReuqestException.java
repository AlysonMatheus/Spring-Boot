package br.com.Alyson.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadReuqestException extends RuntimeException {
    public BadReuqestException() {
        super("Unsupported file extension !");
    }
    public BadReuqestException(String message) {
        super(message);
    }
}
