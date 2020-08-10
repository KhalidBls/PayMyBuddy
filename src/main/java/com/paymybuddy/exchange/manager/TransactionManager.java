package com.paymybuddy.exchange.manager;

import com.paymybuddy.exchange.models.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TransactionManager implements Manager<Transaction> {

    protected Session session = null;

    public TransactionManager(){
        session = getHibernateSession();
    }

    @Override
    public void create(Transaction transaction) {
        update(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        session.beginTransaction();
        session.save(transaction) ;
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Transaction read(int id) {
        session.beginTransaction();
        Transaction transaction = session.find(Transaction.class,id);
        session.getTransaction().commit();
        session.close();
        return transaction;
    }

    @Override
    public void delete(Transaction transaction) {
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
