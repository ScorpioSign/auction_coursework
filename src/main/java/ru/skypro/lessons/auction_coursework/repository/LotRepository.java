package ru.skypro.lessons.auction_coursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.projections.FullLot;


import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long>, PagingAndSortingRepository<Lot, Long> {

    @Query(nativeQuery = true, value =
            "SELECT start_price + ((SELECT count(id) from bid where lot_id = ?1) * start_price) from lot where id = ?1")
    Integer getCurrentPriceById(Long id);

    @Query(value = "select l.id, l.title, l.bid_price as bidPrice , l.start_price as startPrice , l.description , l.status," +
            " (start_price+bid_price*(select count(lot_id) from bid b where b.lot_id = lot_id)) as currentPrice, " +
            "b2.bidder_name AS lastBidderName ,b2.bid_data AS lastBidderDateTime from lot l inner join bid b2" +
            " on l.id = 2 and b2.bid_data = (select max(b3.bid_data)from bid b3 where b3.lot_id = 2)",
            nativeQuery = true)
    Optional<FullLot> getFullInfo(long id);

}


