package com.paymybuddy.exchange.manager;


import com.paymybuddy.exchange.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class UserManager {

    protected Session session = null;

    public UserManager(){
        session = getHibernateSession();
    }

    public void createUser(User user) {
        updateUser(user);
    }

    public void updateUser(User user) {
        session.beginTransaction();
        session.save(user) ;
        session.getTransaction().commit();
        session.close();
    }


    public void deleteUser(User user) {
        session.beginTransaction();
        session.delete(user) ;
        session.getTransaction().commit();
        session.close();
    }


    public User readUser(int id) {
        session.beginTransaction();
        User user = session.find(User.class,id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public static Session getHibernateSession() {

        final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();

        final Session session = sf.openSession();
        return session;
    }



}
