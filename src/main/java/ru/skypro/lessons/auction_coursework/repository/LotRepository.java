package ru.skypro.lessons.auction_coursework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;


import java.util.List;

public interface LotRepository extends CrudRepository<Lot, Integer> {
    List<Lot> findByStatus(Status status);



}
