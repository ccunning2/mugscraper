package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="booking_time")
    private Date bookingTime;


    @OneToMany
    @JoinTable(name = "booking_charges", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "charges_id"))
    private Set<Charges> chargesSet;


}
