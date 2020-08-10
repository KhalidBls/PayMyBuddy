package com.paymybuddy.exchange.manager;

import com.paymybuddy.exchange.models.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TransactionManager {

    protected Session session = null;

    public TransactionManager(){
        session = getHibernateSession();
    }

    public void createTransaction(Transaction transaction) {
        updateUser(transaction);
    }

    public void updateUser(Transaction transaction) {
        session.beginTransaction();
        session.save(transaction) ;
        session.getTransaction().commit();
        session.close();
    }


    public void deleteTransaction(Transaction transaction) {
        session.beginTransaction();
        session.delete(transaction) ;
        session.getTransaction().commit();
        session.close();
    }

    public static Session getHibernateSession() {
        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();
        final Session session = sf.openSession();
        return session;
    }

}
