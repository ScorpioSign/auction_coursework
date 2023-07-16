package ru.skypro.lessons.auction_coursework.projections;

import java.time.LocalDateTime;

public interface Bidder {
    String getBidderName();
    LocalDateTime getBidDate();
}
