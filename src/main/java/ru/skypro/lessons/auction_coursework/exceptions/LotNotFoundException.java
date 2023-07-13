package ru.skypro.lessons.auction_coursework.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LotNotFoundException extends RuntimeException{
    private final int id;

    public int getId() {
        return id;
    }
}