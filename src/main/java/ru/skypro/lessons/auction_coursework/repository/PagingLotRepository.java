package ru.skypro.lessons.auction_coursework.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skypro.lessons.auction_coursework.model.Lot;

public interface PagingLotRepository extends PagingAndSortingRepository<Lot, Long> {


}