package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    public boolean create(User user) throws SQLException {
        return DAOFactory.getUserDAO().create(user);
    }

    public List<User> listAll(){
        return DAOFactory.getUserDAO().listAll();
    }


    public User read(int id) {
        return DAOFactory.getUserDAO().read(id);
    }


    public boolean update(User userUpdated) throws SQLException {
        if(getUserByName(userUpdated.getFirstName(),userUpdated.getLastName()) != null)
            return DAOFactory.getUserDAO().update(userUpdated);
        return false;
    }

    public boolean delete(int id) throws SQLException {
            return DAOFactory.getUserDAO().delete(id);
    }

    public User getUserByName(String firstName,String lastName){
        for (User user : listAll()) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }
}