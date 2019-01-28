package data;

import model.Booking;
import model.Charges;
import model.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO Split into separate DAO someday, maybe
public class Dao {

    private static final String PERSON_DATE_FORMAT = "MM/dd/yyyy";
    private static final String BOOKING_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    private Session session;

    public Dao() {
        this.session = Setup.setup();
    }

    public Person getPerson(Person p) {
        String hql = "FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName and p.dob = :DOB";
        Query query = createPersonQuery(p, hql);
        return (Person) query.getSingleResult();

    }

    public void saveEntity(Object o) {
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }

    public boolean personExists(Person p) {
        String hql = "SELECT COUNT(*) FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName and p.dob = :DOB";
        Query query = createPersonQuery(p, hql);
        return (Long) query.getSingleResult() >= 1;
    }

    public boolean chargesExists(Charges c) {
        String hql = "SELECT COUNT(*) FROM Charges c WHERE c.description = :description";
        Query query = createChargesQuery(c, hql);
        return (Long) query.getSingleResult() >= 1;
    }

    private Query createChargesQuery(Charges c, String hql) {
        Query query = session.createQuery(hql);
        query.setParameter("description", c.getDescription());
        return query;
    }

    private Query createPersonQuery(Person p, String hql) {

        Query query = session.createQuery(hql);
        query.setParameter("firstName", p.getFirstName());
        query.setParameter("lastName", p.getLastName());
        query.setParameter("DOB", p.getDob());
        return query;
    }

    public Date getPersonDate(String DOB) throws ParseException {
        return new SimpleDateFormat(PERSON_DATE_FORMAT).parse(DOB);
    }

    private Date getBookingDate(String bookingDate) throws ParseException {
        bookingDate = bookingDate.replaceAll("@", "");
        return new SimpleDateFormat(BOOKING_TIME_FORMAT).parse(bookingDate);
    }

    public Person createPerson(String firstName, String lastName, String race, String sex, String DOB) throws ParseException {
        return new Person(firstName, lastName, sex, race, getPersonDate(DOB));
    }

    public boolean bookingExists(Booking b) {
        String hql = "SELECT COUNT(*) FROM Booking b WHERE b.person = :person AND b.bookingTime = :bookingTime";
        Query query = createBookingQuery(b, hql);
        return (Long) query.getSingleResult() >= 1;
    }

    private Query createBookingQuery(Booking b, String hql) {
        Query query = session.createQuery(hql);
        query.setParameter("person", b.getPerson());
        query.setParameter("bookingTime", b.getBookingTime());
        return query;
    }


    public Charges getCharges(Charges c) {
        String hql = "FROM Charges c WHERE c.description = :description";
        Query query = createChargesQuery(c, hql);
        return (Charges) query.getSingleResult();
    }

    public Booking createBooking(Person p, String date) throws ParseException {
        return new Booking(p, getBookingDate(date));
    }
}
