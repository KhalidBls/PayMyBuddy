package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.UserDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.User;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDAOIT {

    private static UserDAO userDAO = new UserDAO();
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();


    @Test
    public void testCreateUser() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
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
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("test","DuRead","autre@mail.com",12.0,"je fais un test du read");
        assertTrue(userDAO.create(user));


        //WHEN
        User ourUser = userDAO.read(getUserByName("test","DuRead").getId());

        //THEN
        assertTrue(ourUser.getEmail().equals(user.getEmail()));
    }

    @Test
    public void testUpdateUser() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("test","DuUpdate","autre@mail.com",12.0,"je fais un test du read");
        assertTrue(userDAO.create(user));
        int id = getUserByName("test","DuUpdate").getId();
        user.setId(id);

        user.setEmail("je test update du mail");

        //WHEN
        assertTrue(userDAO.update(user));

        //THEN
        assertTrue(userDAO.read(id).getEmail().equals("je test update du mail"));
    }

    @Test
    public void testDeleteUser() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("test","DuDELETE","autre@mail.com",12.0,"je fais un test du delete");
        assertTrue(userDAO.create(user));
        int id = getUserByName("test","DuDELETE").getId();

        //WHEN
        assertTrue(userDAO.delete(id));

        //THEN
        assertNull(getUserByName("test","DuDELETE"));
    }



    public User getUserByName(String firstName,String lastName){
        for (User user : userDAO.listAll()) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

}
