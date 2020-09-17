package com.paymybuddy.exchange.integration.dao;

import com.paymybuddy.exchange.dao.BankAccountDAO;
import com.paymybuddy.exchange.dao.TransactionDAO;
import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;
import com.paymybuddy.exchange.integration.services.DataBasePrepareService;
import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.models.Transaction;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionDAOIT {

    TransactionDAO transactionDAO = new TransactionDAO();
    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService = new DataBasePrepareService();


    @Test
    public void testCreateTransaction() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        transactionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Transaction transaction = new Transaction(18.0,2,22,2,"notre type");
        //WHEN
        assertTrue(transactionDAO.create(transaction));
        //THEN
        Transaction ourTransaction = getTransactionByIdUserAndSender(2,22);

        assertTrue(ourTransaction.getAmount()==transaction.getAmount());
        assertTrue(ourTransaction.getType().equals(transaction.getType()));
        assertTrue(ourTransaction.getIdDescription()==2);
    }


    @Test
    public void testReadTransactionsById() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        transactionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Transaction transaction = new Transaction(18.0,2,22,2,"notre type");
        assertTrue(transactionDAO.create(transaction));

        //WHEN
        Transaction ourTransaction = transactionDAO.read(getTransactionByIdUserAndSender(2,22).getId());

        //THEN
        assertTrue(ourTransaction.getType().equals(transaction.getType()));
    }

    @Test
    public void testUpdateTransaction() throws SQLException {
        dataBasePrepareService.clearDataBaseEntries();
        transactionDAO.dataBaseConfig = dataBaseTestConfig;
        //GIVEN
        Transaction transaction = new Transaction(18.0,2,22,2,"notre type");
        assertTrue(transactionDAO.create(transaction));
        int id = getTransactionByIdUserAndSender(2,22).getId();
        transaction.setId(id);

        transaction.setType("je le change ici ");

        //WHEN
        assertTrue(transactionDAO.update(transaction));

        //THEN
        assertTrue(transactionDAO.read(id).getType().equals("je le change ici "));
    }

        @Test
        public void testDeleteTransaction() throws SQLException {
            dataBasePrepareService.clearDataBaseEntries();
            transactionDAO.dataBaseConfig = dataBaseTestConfig;
            //GIVEN
            Transaction transaction = new Transaction(18.0,2,22,2,"notre type");
            assertTrue(transactionDAO.create(transaction));
            int id = getTransactionByIdUserAndSender(2,22).getId();
            transaction.setId(id);

            //WHEN
            assertTrue(transactionDAO.delete(id));

            //THEN
            assertNull(getTransactionByIdUserAndSender(2,22));
        }

    private Transaction getTransactionByIdUserAndSender(int idUserSender, int idUserReceiver){
        for (Transaction transaction: transactionDAO.listAll()) {
            if (transaction.getIdUserSender()==idUserSender && transaction.getIdUserReceiver()==idUserReceiver)
                return transaction;
        }
        return null;
    }

}
