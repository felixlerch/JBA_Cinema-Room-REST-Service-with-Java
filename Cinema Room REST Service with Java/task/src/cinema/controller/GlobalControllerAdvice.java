package cinema.controller;

import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.WrongPasswordException;
import cinema.exceptions.WrongPurchaseTockenException;
import cinema.objects.ErrorResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {
            IndexOutOfBoundsException.class
            , SeatAlreadyPurchasedException.class
            , WrongPurchaseTockenException.class

    })
    public ResponseEntity<ErrorResponseMessage> handleCinemaSeatExceptions(Exception ex) {
        return ResponseEntity.status(400).body(new ErrorResponseMessage(ex.getMessage() == null ? "" : ex.getMessage()));
    }

    @ExceptionHandler(value = {
            WrongPasswordException.class
    })
    public ResponseEntity<ErrorResponseMessage> handleCinemaStatisticExceptions(Exception ex) {
        return ResponseEntity.status(401).body(new ErrorResponseMessage(ex.getMessage() == null ? "" : ex.getMessage()));
    }

}
