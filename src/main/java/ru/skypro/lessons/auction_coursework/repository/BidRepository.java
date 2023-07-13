package ru.skypro.lessons.auction_coursework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.auction_coursework.model.Bid;


public interface BidRepository extends CrudRepository<Bid, Integer> {

}
