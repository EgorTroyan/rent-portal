package com.egortroian.portal.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class Booking {
    public Booking() {
    }

    public Booking(LocalDateTime startDate, LocalDateTime endDate, User user, Bike bike) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.bike = bike;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

}
