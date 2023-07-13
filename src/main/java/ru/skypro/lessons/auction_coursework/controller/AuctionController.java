package ru.skypro.lessons.auction_coursework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.auction_coursework.dto.FullLot;
import ru.skypro.lessons.auction_coursework.model.Bid;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;
import ru.skypro.lessons.auction_coursework.service.LotsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("lot")
@RequiredArgsConstructor
public class AuctionController {


    private final LotsService lotsService;

    //    Получить информацию о первом ставившем на лот
    @GetMapping("/{id}/first")
    public Bid getFirstBidder(@PathVariable int id) {
        return lotsService.getFirstBidder(id);
    }

    //    Вернуть имя ставившего на данный лот наибольшее количество раз
    @GetMapping("/{id}/frequent")
    public String getFrequentBidder(@PathVariable int id) {
        return lotsService.getFrequentBidder(id);
    }

    //    Получить полную информацию о лоте
    @GetMapping("/{id}")
    public FullLot getEmployeeFullLotById(@PathVariable int id) {
        return lotsService.getEmployeeFullLotById(id);
    }


    //    Начать торги по лоту
    @PostMapping("/{id}/start")
    public void startLot(@PathVariable int id) {
        lotsService.startLot(id);
    }


    //    Сделать ставку по лоту
    @PostMapping("/{id}/bid")
    public void makeBet(@PathVariable int id,
                        @RequestParam("bidderName") String bidderName) {
        lotsService.makeBet(id, bidderName);
    }


    //    Остановить торги по лоту
    @PostMapping("/{id}/stop")
    public void stopLot(@PathVariable int id) {
        lotsService.stopLot(id);
    }


    //    Создать новый лот
    @PostMapping
    public Lot createLot(@RequestParam String title,
                         @RequestParam String description,
                         @RequestParam int startPrice,
                         @RequestParam int bidPrice) {
        return lotsService.createLot(title, description, startPrice, bidPrice);
    }

    //    Получить все лоты, основываясь на фильтре статуса и номере страницы
    @GetMapping()
    public List<Lot> getLotsByStatusAndPage(@RequestParam("status") Status status,
                                            @RequestParam("page") int page) {
        return lotsService.getLotsByStatusAndPage(status, page);
    }

    //    Экспортировать все лоты в файл CSV
    @GetMapping("/export")
    public String exportLots() throws IOException {
        return lotsService.exportLots();
    }


}

