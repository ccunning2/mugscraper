package data;

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
        String hql = "FROM Person p WHERE p.first_name = :firstName AND p.last_name = :lastName and p.dob = :DOB";
        Query query = createPersonQuery(p, hql);
        return (Person) query.getSingleResult();

    }

    public void savePerson(Person p) {
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }

    public boolean personExists(Person p) {
        String hql = "SELECT COUNT(*) FROM Person p WHERE p.first_name = :firstName AND p.last_name = :lastName and p.dob = :DOB";
        Query query = createPersonQuery(p, hql);
        return (int) query.getSingleResult() >= 1;
    }

    private Query createPersonQuery(Person p, String hql) {

        Query query = session.createQuery(hql);
        query.setParameter("firstName", p.getFirstName());
        query.setParameter("lastName", p.getLastName());
        query.setParameter("DOB", p.getDob());
        return query;
    }

    private Date getPersonDate(String DOB) throws ParseException {
        return new SimpleDateFormat(PERSON_DATE_FORMAT).parse(DOB);
    }

    public Person createPerson(String firstName, String lastName, String race, String sex, String DOB) throws ParseException {
        return new Person(firstName, lastName, sex, race, getPersonDate(DOB));
    }


}
