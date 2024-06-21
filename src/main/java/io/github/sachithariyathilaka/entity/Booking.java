package io.github.sachithariyathilaka.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Booking entity class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "movie")
    private String movie;

    @Column(name = "date")
    private String date;

    @Column(name = "ticketCount")
    private int ticketCount;

    @Column(name = "showTime")
    private String showTime;

    @Column(name = "paymentStatus")
    private boolean paymentStatus;

    @Column(name = "userId")
    private int userId;
}
