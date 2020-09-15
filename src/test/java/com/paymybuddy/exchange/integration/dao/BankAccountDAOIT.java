package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.BankAccountDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.BankAccount;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountDAOIT {

    BankAccountDAO bankAccountDAO = new BankAccountDAO();
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();


    @Test
    public void testCreateBankAccount() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        bankAccountDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        BankAccount bankAccount = new BankAccount("FRBOUS","SGGP",1);
        //WHEN
        assertTrue(bankAccountDAO.create(bankAccount));
        //THEN
        BankAccount ourBankAccount = getBankAccountByIdUser(1);

        assertTrue(ourBankAccount.getIdUser()==bankAccount.getIdUser());
        assertTrue(ourBankAccount.getIban().equals(bankAccount.getIban()));
        assertTrue(ourBankAccount.getSwift().equals(bankAccount.getSwift()));
    }

    @Test
    public void testReadBankAccountById() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        bankAccountDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        BankAccount bankAccount = new BankAccount("FRBOUS","SGGP",1);
        assertTrue(bankAccountDAO.create(bankAccount));

        //WHEN
        BankAccount ourBankAccount = bankAccountDAO.read(getBankAccountByIdUser(1).getId());

        //THEN
        assertTrue(ourBankAccount.getSwift().equals(bankAccount.getSwift()));
    }
/*
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
    */
    private BankAccount getBankAccountByIdUser(int idUser){
        for (BankAccount bankaccount: bankAccountDAO.listAll()) {
            if(bankaccount.getIdUser() == idUser)
                return bankaccount;
        }
        return null;
    }

}
