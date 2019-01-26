package model;

import javax.persistence.*;
import java.sql.Timestamp;

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private int person;


    @Column(name="booking_time")
    private Timestamp bookingTime;



}
