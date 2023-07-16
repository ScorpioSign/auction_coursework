package ru.skypro.lessons.auction_coursework.service;


import ru.skypro.lessons.auction_coursework.exceptions.BidNotFoundException;
import ru.skypro.lessons.auction_coursework.exceptions.LotNotFoundException;
import ru.skypro.lessons.auction_coursework.projections.Bidder;

public interface BidService {
    Bidder getFirstBidder(Long id) throws LotNotFoundException;

    Bidder getMostFrequentBidder(Long id) throws LotNotFoundException, BidNotFoundException;


}
