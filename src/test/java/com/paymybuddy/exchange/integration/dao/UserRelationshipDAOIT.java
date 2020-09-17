package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.UserRelationshipDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.UserRelationship;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRelationshipDAOIT {

    UserRelationshipDAO userRelationshipDAO = new UserRelationshipDAO();
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();


    @Test
    public void testCreateRelationship() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userRelationshipDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        UserRelationship relationship = new UserRelationship(3,4);
        //WHEN
        assertTrue(userRelationshipDAO.create(relationship));
        //THEN
        UserRelationship ourUserRelation = getUserRelationshipById(3,4);

        assertTrue(ourUserRelation.getIdUserRelated()==relationship.getIdUserRelated());
        assertTrue(ourUserRelation.getIdUserRelating()==relationship.getIdUserRelating());
    }

    @Test
    public void testReadUserRelationshipById() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userRelationshipDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        UserRelationship relationship = new UserRelationship(1,11);
        assertTrue(userRelationshipDAO.create(relationship));

        //WHEN
        UserRelationship ourRelationship = userRelationshipDAO.read(getUserRelationshipById(1,11).getId());

        //THEN
        assertTrue(ourRelationship.getIdUserRelated() == relationship.getIdUserRelated());
    }

    @Test
    public void testUpdateUserRelationship() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userRelationshipDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        UserRelationship relationship = new UserRelationship(1,11);
        assertTrue(userRelationshipDAO.create(relationship));
        int id = getUserRelationshipById(1,11).getId();
        relationship.setId(id);
        relationship.setTimestampOfCreation(getUserRelationshipById(1,11).getTimestampOfCreation());

        relationship.setIdUserRelating(44);

        //WHEN
        assertTrue(userRelationshipDAO.update(relationship));

        //THEN
        assertTrue(userRelationshipDAO.read(id).getIdUserRelating() == 44);
    }

    @Test
    public void testDeleteUserRelationship() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        userRelationshipDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        UserRelationship relationship = new UserRelationship(   3,33);
        assertTrue(userRelationshipDAO.create(relationship));
        int id = getUserRelationshipById(3,33).getId();
        relationship.setId(id);
        relationship.setTimestampOfCreation(getUserRelationshipById(3,33).getTimestampOfCreation());

        //WHEN
        assertTrue(userRelationshipDAO.delete(id));

        //THEN
        assertNull(getUserRelationshipById(3,33));
    }


    private UserRelationship getUserRelationshipById(int id1,int id2) {
        for (UserRelationship relationship : userRelationshipDAO.listAll()) {
            if (relationship.getIdUserRelating() == id1 && relationship.getIdUserRelated() == id2)
                return relationship;
        }
        return null;
    }
}
