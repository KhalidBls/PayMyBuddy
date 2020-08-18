package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    DAOFactory daoFactory;

    public boolean create(User user) throws SQLException {
        return daoFactory.getUserDAO().create(user);
    }

    public List<User> listAll(){
        return daoFactory.getUserDAO().listAll();
    }


    public User read(int id) {
        return daoFactory.getUserDAO().read(id);
    }


    public void update(User userUpdated) throws SQLException {
        daoFactory.getUserDAO().update(userUpdated);
    }

    public void delete(int id) throws SQLException {
        daoFactory.getUserDAO().delete(id);
    }
}
