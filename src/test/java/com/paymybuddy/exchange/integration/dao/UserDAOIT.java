package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.constants.DBConstants;
import com.paymybuddy.exchange.dao.UserDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.models.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDAOIT {

    UserDAO userDAO = new UserDAO();
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();



    @Test
    public void testCreateUser() throws SQLException {
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("bob","bobby","bob@mail.com",12.0,"je refais un test");
        //WHEN
        assertTrue(userDAO.create(user));
        //THEN
        User ourUser = getUserByName("bob","bobby");

        assertTrue(user.getFirstName().equals(ourUser.getFirstName()));
        assertTrue(user.getLastName().equals(ourUser.getLastName()));
        assertTrue(user.getEmail().equals(ourUser.getEmail()));
    }
    @Test
    public void testReadUserById() throws SQLException {
        //GIVEN
        User user = new User("test","DuRead","autre@mail.com",12.0,"je fais un test du read");
        assertTrue(userDAO.create(user));


        //WHEN
        User ourUser = userDAO.read(getUserByName("test","DuRead").getId());

        //THEN
        assertTrue(ourUser.getEmail().equals(user.getEmail()));
    }



    public User getUserByName(String firstName,String lastName){
        for (User user : userDAO.listAll()) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

}
