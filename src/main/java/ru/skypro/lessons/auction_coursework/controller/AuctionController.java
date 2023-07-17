package ru.skypro.lessons.auction_coursework.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;
import ru.skypro.lessons.auction_coursework.projections.Bidder;
import ru.skypro.lessons.auction_coursework.projections.FullLot;
import ru.skypro.lessons.auction_coursework.service.BidService;
import ru.skypro.lessons.auction_coursework.service.LotsService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("lot")
@RequiredArgsConstructor
public class AuctionController {


    private final LotsService lotsService;
    private final BidService bidService;


    //    Получить информацию о первом ставившем на лот
    @GetMapping("/{id}/first")
    public Bidder getFirstBidder(@PathVariable long id) {
        return bidService.getFirstBidder(id);
    }

    //    Вернуть имя ставившего на данный лот наибольшее количество раз
    @GetMapping("/{id}/frequent")
    public Bidder findMostFrequentBidder(@PathVariable long id) {
        return bidService.getMostFrequentBidder(id);
    }

    //    Получить полную информацию о лоте
    @GetMapping("/{id}")
    public FullLot getFullLotInfoByID(@PathVariable long id) {
        return lotsService.getFullLotInfoByID(id);
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
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> exportLots(HttpServletResponse response) throws IOException {
        String fileName = "allLots.csv";
        response.setContentType("text/csv");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition", "attachment; filename=\"allLots.csv\"");

        try {
            lotsService.exportAllLots(response);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(response);
        } catch (Throwable t) {
            return new ResponseEntity<>("Лоты для загрузки отсутствуют.", HttpStatus.BAD_REQUEST);
        }
    }


}

