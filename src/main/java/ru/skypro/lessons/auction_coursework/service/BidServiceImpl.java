package ru.skypro.lessons.auction_coursework.service;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.auction_coursework.exceptions.BidNotFoundException;
import ru.skypro.lessons.auction_coursework.exceptions.LotNotFoundException;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.repository.BidRepository;
import ru.skypro.lessons.auction_coursework.repository.LotRepository;
import ru.skypro.lessons.auction_coursework.projections.Bidder;

import java.util.Optional;

@Builder
@Service
public class BidServiceImpl implements BidService {
    private static final Logger logger = LoggerFactory.getLogger(LotsServiceImpl.class);
    private final BidRepository bidRepository;
    private final LotRepository lotRepository;


    public BidServiceImpl(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    @Override
    public Bidder getFirstBidder(Long id) throws LotNotFoundException, BidNotFoundException {
        Optional<Lot> lotById = lotRepository.findById(id);
        if (lotById.isEmpty()) {
            throw new LotNotFoundException();
        }
        if ((lotById.get().getBids().isEmpty())) {
            throw new BidNotFoundException();
        }
        return bidRepository.getFirstBidder(id);
    }

    @Override
    public Bidder getMostFrequentBidder(Long id) throws LotNotFoundException, BidNotFoundException {
        Optional<Lot> lotById = lotRepository.findById(id);
        if (lotById.isEmpty()) {
            throw new LotNotFoundException();
        }
        if ((lotById.get().getBids().isEmpty())) {
            throw new BidNotFoundException();
        }
        return bidRepository.findMostFrequentBidder(id);
    }


}