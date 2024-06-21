package io.github.sachithariyathilaka.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Movie entity class.
 *
 * @author  Sachith Ariyathilaka
 * @version 1.0.0
 * @since   2024/06/21
 */
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "price")
    private String price;

    @Column(name = "showTime")
    private String showTime;

    @Column(name = "owner")
    private String owner;

    @Column(name = "status")
    private boolean status;

    @Column(name = "availableTickets")
    private int availableTickets;
}
