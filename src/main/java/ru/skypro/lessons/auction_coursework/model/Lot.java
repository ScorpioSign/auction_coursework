package ru.skypro.lessons.auction_coursework.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Min(3)
    @Max(64)
    private String title;
    @Min(1)
    @Max(4096)
    private String description;
    @Min(1)
    private int startPrice;
    @Min(1)
    private int bidPrice;

    @OneToMany(mappedBy = "lot")
   // @JoinColumn(name = "lot_id")
    private List<Bid> bids;


    public Lot(Long id, int statusId, String title, String description, int startPrice, int bidPrice) {
        this.id = id;
        this.status = getStatus(statusId);
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.bidPrice = bidPrice;
    }

    private Status getStatus(int statusId) {
        Status status;
        if(statusId == 1){
            status = Status.STARTED;
        }else if(statusId == 2){
            status = Status.STOPPED;
        }else{
            status = Status.CREATED;
        }
        return status;
    }


}
