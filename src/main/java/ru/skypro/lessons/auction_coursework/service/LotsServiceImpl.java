package ru.skypro.lessons.auction_coursework.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.auction_coursework.dto.CreateLot;
import ru.skypro.lessons.auction_coursework.dto.FullLotDTO;

import ru.skypro.lessons.auction_coursework.exceptions.LotNotFoundException;
import ru.skypro.lessons.auction_coursework.model.Bid;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;
import ru.skypro.lessons.auction_coursework.projections.FullLot;
import ru.skypro.lessons.auction_coursework.repository.BidRepository;
import ru.skypro.lessons.auction_coursework.repository.LotRepository;
import ru.skypro.lessons.auction_coursework.repository.PagingLotRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@RequiredArgsConstructor
@Builder
@Service
public class LotsServiceImpl implements LotsService {
    private static final Logger logger = LoggerFactory.getLogger(LotsServiceImpl.class);
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final PagingLotRepository pagingLotRepository;


    @Override
    public FullLot getFullLotInfoByID(long id) {
        logger.info("Вызван метод получения полной информации по лоту");
        return lotRepository.getFullInfo(id).orElseThrow(LotNotFoundException::new);
    }


    @Override
    public void startLot(long id) {
        Lot lot = getLotById(id);
        logger.info("Торги по лоту " + id + " начаты");
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
    }

    @Override
    public void makeBet(long id, String bidderName) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Лот с ID = " + id + " не найден");
                    return new LotNotFoundException();
                });
        if (lot.getStatus() == Status.STARTED) {
            Bid bid = new Bid(bidderName);
            lot.getBids().add(bid);
            lot.setBids(lot.getBids());
            logger.info("Ставка по лоту " + id + " сделана");
            bidRepository.save(bid);
            lotRepository.save(lot);
        } else {
            logger.error("Статус лота не позволяет сделать ставку");
        }
    }

    @Override
    public void stopLot(long id) {
        Lot lot = getLotById(id);
        logger.info("Торги по лоту " + id + " остановлены");
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
    }

    @Override
    public Lot createLot(String title, String description, int startPrice, int bidPrice) {
        CreateLot createLot = new CreateLot(title, description, startPrice, bidPrice);
        Lot lot = createLot.toLot(createLot);
        lotRepository.save(lot);
        logger.info("Лот успешно создан");
        return lot;
    }

    @Override

    public List<Lot> getLotsByStatusAndPage(Status status, int page) {
        int sizePage = 10;
        PageRequest lotByPage = PageRequest.of(page, sizePage);
        Page<Lot> pageLot = pagingLotRepository.findAll(lotByPage);
        logger.info("Вызван метод получения лотов по фильтру статуса и номеру страницы");
        return pageLot.stream().filter(l -> l.getStatus() == status).toList();
    }


    @Override
    public void exportAllLots(HttpServletResponse response) throws IOException {

        logger.info("Вызван метод экспорта лотов");
        List<FullLotDTO> fullLotDTOS = lotRepository.findAll().stream()
                .map(FullLotDTO::fromLot)
                .peek(fullLotDTO -> fullLotDTO.setCurrentPrice(lotRepository.getCurrentPriceById(fullLotDTO.getId())))
                .toList();

        PrintWriter printWriter = response.getWriter();
        CSVPrinter printer = new CSVPrinter(printWriter, CSVFormat.DEFAULT);
        fullLotDTOS
                .forEach(fullLotDTO -> {
                    try {
                        printer.printRecord(
                                fullLotDTO.getId(),
                                fullLotDTO.getStatus(),
                                fullLotDTO.getTitle(),
                                fullLotDTO.getDescription(),
                                fullLotDTO.getStartPrice(),
                                fullLotDTO.getBidPrice());
                    } catch (IOException e) {
                        logger.error("Не удалось записать " + response, e);
                    }
                });
        printer.flush();
        printer.close();
        printWriter.close();
        logger.info("Метод экспорта лотов выполнен");
    }


    private Lot getLotById(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Лот с ID = " + id + " не найден");
                    return new LotNotFoundException();
                });
        return lot;
    }


}








