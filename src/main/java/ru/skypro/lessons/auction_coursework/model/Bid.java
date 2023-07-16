package ru.skypro.lessons.auction_coursework.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "bidder_name")
    private String bidderName;
    @Column(name = "bid_data")
    private LocalDateTime bidDate;

    @ManyToOne
    private Lot lot;

    public Bid(String bidderName) {
        this.bidderName = bidderName;
        this.bidDate = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime());
    }

}
