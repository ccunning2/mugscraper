package data;

import model.Booking;
import model.Charges;
import model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class Setup {

    static Session setup() {
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Charges.class)
                .addAnnotatedClass(Booking.class)
                .configure().buildSessionFactory();

        return sessionFactory.openSession();
//        session.beginTransaction();
//        session.save(p);
//        session.getTransaction().commit();
//        session.close();
    }


}
