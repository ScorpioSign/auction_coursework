package ru.skypro.lessons.auction_coursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.lessons.auction_coursework.model.Bid;
import ru.skypro.lessons.auction_coursework.projections.Bidder;


public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT bidder_name as bidderName, bid_data as bidDate FROM bid WHERE lot_id=?1 ORDER BY bid_data LIMIT 1", nativeQuery = true)
    Bidder getFirstBidder(Long lotId);

    @Query(nativeQuery = true, value =
            "SELECT bidder_name as bidderName, max(bid_data) as bidDate FROM bid WHERE lot_id = ?1 GROUP BY bidder_name" +
                    " ORDER BY count(*) desc limit 1")
    Bidder findMostFrequentBidder(Long lotId);

    @Query(nativeQuery = true, value =
            "SELECT bidder_name as bidderName,bid_data as bidDate FROM bid WHERE lot_id=?1 ORDER BY bid_data desc LIMIT 1")
    Bidder findLastBidder(Long lotId);

}


