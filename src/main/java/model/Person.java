package model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String last_name;

    @Column(name="sex")
    private String sex;

    @Column(name="race")
    private String race;

    @Column(name="dob")
    private Timestamp dob; //TODO parse date to TimeStamp

    @OneToMany(mappedBy = "person")
    private Set<Booking> bookings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }
}
