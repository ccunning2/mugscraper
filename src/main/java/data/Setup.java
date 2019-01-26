package data;

import model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public  class Setup {

    public static void setup() {
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Person.class).configure().buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();
        session.close();
    }


}
