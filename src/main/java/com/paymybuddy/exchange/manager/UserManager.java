package com.paymybuddy.exchange.manager;


import com.paymybuddy.exchange.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class UserManager implements Manager<User> {

    protected Session session = null;

    public UserManager(){
        session = getHibernateSession();
    }

    @Override
    public void create(User user) {
        update(user);
    }

    @Override
    public User read(int id) {
        session.beginTransaction();
        User user = session.find(User.class,id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void update(User user) {
        session.beginTransaction();
        session.save(user) ;
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        session.beginTransaction();
        session.delete(user) ;
        session.getTransaction().commit();
        session.close();
    }

    public List<User> listUsers() {
        session.beginTransaction();
        List<User> users = (List<User>) session.createQuery( "from User" ).list();
        session.getTransaction().commit();
        session.close();
        return users;
    }


    public static Session getHibernateSession() {
        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();
        final Session session = sf.openSession();
        return session;
    }



}
