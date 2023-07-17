package ru.skypro.lessons.auction_coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.lessons.auction_coursework.model.Lot;
import ru.skypro.lessons.auction_coursework.model.Status;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLot {
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;

    public static CreateLot fromLot(Lot lot) {
        CreateLot createLot = new CreateLot();
        createLot.setTitle(lot.getTitle());
        createLot.setDescription(lot.getDescription());
        createLot.setStartPrice(lot.getStartPrice());
        createLot.setBidPrice(lot.getBidPrice());
        return createLot;
    }

    public Lot toLot(CreateLot createLot) {
        Lot lot = new Lot();
        lot.setStatus(Status.CREATED);
        lot.setTitle(this.getTitle());
        lot.setDescription(this.getDescription());
        lot.setStartPrice(this.getStartPrice());
        lot.setBidPrice(this.getBidPrice());
        return lot;
    }
}
