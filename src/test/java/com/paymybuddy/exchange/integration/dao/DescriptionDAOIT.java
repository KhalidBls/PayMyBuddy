package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.BankAccountDAO;
import com.paymybuddy.exchange.dao.DescriptionDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.models.Description;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DescriptionDAOIT {


    DescriptionDAO descriptionDAO = new DescriptionDAO();
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();


    @Test
    public void testCreateDescription() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        descriptionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Description description = new Description("mon commentaire");
        //WHEN
        assertTrue(descriptionDAO.create(description));
        //THEN
        Description ourDescription = getDescriptionByContent(description.getContent());

        assertTrue(ourDescription.getContent().equals("mon commentaire"));
    }

    @Test
    public void testReadDescriptionById() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        descriptionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Description description = new Description("mon commentaire");
        assertTrue(descriptionDAO.create(description));

        //WHEN
        Description ourDesc = descriptionDAO.read(getDescriptionByContent(description.getContent()).getId());

        //THEN
        assertTrue(ourDesc.getContent().equals("mon commentaire"));
    }

    @Test
    public void testUpdateDescription() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        descriptionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Description description = new Description("mon commentaire");
        assertTrue(descriptionDAO.create(description));
        int id = getDescriptionByContent(description.getContent()).getId();
        description.setId(id);

        description.setContent("je le change ici ");

        //WHEN
        assertTrue(descriptionDAO.update(description));

        //THEN
        assertTrue(descriptionDAO.read(id).getContent().equals("je le change ici "));
    }

    @Test
    public void testDeleteUserRelationship() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        descriptionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Description description = new Description("mon commentaire");
        assertTrue(descriptionDAO.create(description));
        int id = getDescriptionByContent(description.getContent()).getId();
        description.setId(id);

        //WHEN
        assertTrue(descriptionDAO.delete(id));

        //THEN
        assertNull(getDescriptionByContent(description.getContent()));
    }



    private Description getDescriptionByContent(String content){
        for (Description description: descriptionDAO.listAll()) {
            if(description.getContent().equals(content))
                return description;
        }
        return null;
    }

}
