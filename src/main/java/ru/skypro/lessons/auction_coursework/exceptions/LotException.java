package ru.skypro.lessons.auction_coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotException {
    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<?> handleLotNotFound(LotNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("лот с id = %d не найден".formatted(e.getId()));
    }

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<?> handleBidNotFound(LotNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ставка не найдена");
    }
}
