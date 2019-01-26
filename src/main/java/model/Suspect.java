package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Suspect {

    String firstName;
    String lastName;
    String race;
    String sex;
    String bookingDate;
    String bookingTime;
    List<String> charges = new ArrayList<>();
    String dateOfBirth;

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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }




    public void addCharges(String charge) {
        this.charges.add(charge);
    }

    public List<String> getCharges() {
        return charges;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suspect suspect = (Suspect) o;
        return firstName.equals(suspect.firstName) &&
                lastName.equals(suspect.lastName) &&
                race.equals(suspect.race) &&
                sex.equals(suspect.sex) &&
                bookingDate.equals(suspect.bookingDate) &&
                bookingTime.equals(suspect.bookingTime) &&
                dateOfBirth.equals(suspect.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, race, sex, bookingDate, bookingTime, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Suspect{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", race='" + race + '\'' +
                ", sex='" + sex + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", bookingTime='" + bookingTime + '\'' +
                ", charges=" + charges +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
