package model;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="sex")
    private String sex;

    @Column(name="race")
    private String race;

    @Temporal(TemporalType.DATE)
    @Column(name="dob")
    private Date dob;

    @OneToMany(mappedBy = "person")
    private Set<Booking> bookings;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Person(String firstName, String lastName, String sex, String race, Date dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.race = race;
        this.dob = dob;
        this.bookings = new HashSet<>();
    }

    public Person() {
    }
}
