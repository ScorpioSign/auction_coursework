package ru.skypro.lessons.auction_coursework.service;


import jakarta.servlet.http.HttpServletResponse;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;
import ru.skypro.lessons.auction_coursework.projections.FullLot;

import java.io.IOException;
import java.util.List;

public interface LotsService {

    FullLot getFullLotInfoByID(long id);


    void startLot(long id);

    void makeBet(long id, String bidderName);

    void stopLot(long id);

    Lot createLot(String title, String description, int startPrice, int bidPrice);

    List<Lot> getLotsByStatusAndPage(Status status, int page);

    //String exportLots() throws IOException;

    void exportAllLots(HttpServletResponse response) throws IOException;

   // int getCurrentPriceById(Lot lot);


}