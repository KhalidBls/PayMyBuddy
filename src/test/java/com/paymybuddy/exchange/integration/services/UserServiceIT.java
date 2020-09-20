package com.paymybuddy.exchange.integration.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.dao.UserDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.User;
import com.paymybuddy.exchange.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest( DAOFactory.class )
public class UserServiceIT {


    private static UserService userService = new UserService();
    private static UserDAO userDAO = new UserDAO();
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();

    @Test
    public void testCreateUserFromServiceWithGoodUserShouldReturnTrue() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //given
        User user = new User("test","DuCreateService","autre@mail.com",12.0,"je fais un test du read");
        mockStatic(DAOFactory.class);
        //WHEN
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);

        //THEN
        assertTrue(userService.create(user));
    }

    @Test
    public void testReadUser() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //given
        User user = new User("test","DuReadService","autre@mail.com",12.0,"je fais un test du read");
        mockStatic(DAOFactory.class);

        //WHEN
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);
        assertTrue(userService.create(user));
        int id = getUserByName("test","DuReadService").getId();
        //THEN
        assertTrue(userService.read(id).getEmail().equals(user.getEmail()));
    }

    @Test
    public void testUpdateUserWhenExistingShouldReturnTrue() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("test","DuReadService","autre@mail.com",12.0,"je fais un test du update");
        mockStatic(DAOFactory.class);
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);
        assertTrue(userService.create(user));
        int id = getUserByName("test","DuReadService").getId();
        user.setId(id);
        user.setPassword("Je met à jour ici");

        //WHEN
        assertTrue(userService.update(user));

        //THEN
        assertTrue(userService.read(id).getPassword().equals(user.getPassword()));

    }

    @Test
    public void testUpdateWhenNotUserExistingShouldReturnFalse() throws SQLException {
        User user = new User("test","DuReadService","autre@mail.com",12.0,"je fais un test du update");
        mockStatic(DAOFactory.class);
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);


        assertFalse(userService.update(user));
    }

    @Test
    public void testDeleteUserWhenExistingShouldReturnTrue() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        User user = new User("test","DuDeleteService","autre@mail.com",12.0,"je fais un test du update");
        mockStatic(DAOFactory.class);
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);
        assertTrue(userService.create(user));
        int id = getUserByName("test","DuDeleteService").getId();
        user.setId(id);
        user.setPassword("Je met à jour ici");

        //WHEN
        assertTrue(userService.delete(id));

        //THEN
        assertNull(getUserByName("test","DuDeleteService"));
    }

    @Test
    public void testGetUserByNameWhenExisting() throws SQLException {
        //GIVEN
        User user = new User("test","DuDeleteService","autre@mail.com",12.0,"je fais un test du update");
        mockStatic(DAOFactory.class);
        PowerMockito.when(DAOFactory.getUserDAO()).thenReturn(userDAO);
        User user1 = new User("test","du get alllll","autre@mail.com",12.0,"autre chose");
        assertTrue(userService.create(user));
        assertTrue(userService.create(user1));

        assertTrue(userService.getUserByName("test","DuDeleteService").getPassword().equals("je fais un test du update"));
        assertTrue(userService.getUserByName("test","du get alllll").getPassword().equals("autre chose"));

    }

    private User getUserByName(String firstName,String lastName){
        for (User user : userDAO.listAll()) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }



}
