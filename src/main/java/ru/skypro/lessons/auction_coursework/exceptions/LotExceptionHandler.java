package ru.skypro.lessons.auction_coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class LotExceptionHandler {
    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<?> handleLotNotFound(LotNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("лот не найден");
    }

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<?> handleBidNotFound(LotNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ставка не найдена");
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIOException(IOException ioException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
