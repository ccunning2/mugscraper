package model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @Column(name = "image_path")
    private String imagePath;


    @OneToMany
    @JoinTable(name = "booking_charges", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "charges_id"))
    private Set<Charges> chargesSet;

    public Booking(Person person, Date bookingTime) {
        this.person = person;
        this.bookingTime = bookingTime;
        this.chargesSet = new HashSet<>();
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void addCharge(Charges c) {
        this.chargesSet.add(c);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
