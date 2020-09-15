package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.BankAccountDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.BankAccount;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    public void testUpdateBankAccount() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        bankAccountDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        BankAccount bankAccount = new BankAccount("FRBOUS","SGGP",1);
        assertTrue(bankAccountDAO.create(bankAccount));
        int id = getBankAccountByIdUser(1).getId();
        bankAccount.setId(id);

        bankAccount.setIban("je le change ici ");

        //WHEN
        assertTrue(bankAccountDAO.update(bankAccount));

        //THEN
        assertTrue(bankAccountDAO.read(id).getIban().equals("je le change ici "));
    }

    @Test
    public void testDeleteUserRelationship() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        bankAccountDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        BankAccount bankAccount = new BankAccount("FRBOUS","SGGP",1);
        assertTrue(bankAccountDAO.create(bankAccount));
        int id = getBankAccountByIdUser(1).getId();
        bankAccount.setId(id);

        //WHEN
        assertTrue(bankAccountDAO.delete(id));

        //THEN
        assertNull(getBankAccountByIdUser(1));
    }

    private BankAccount getBankAccountByIdUser(int idUser){
        for (BankAccount bankaccount: bankAccountDAO.listAll()) {
            if(bankaccount.getIdUser() == idUser)
                return bankaccount;
        }
        return null;
    }

}
