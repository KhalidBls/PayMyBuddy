package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    DAOFactory daoFactory;

    public boolean create(Transaction transaction) throws SQLException {
        if(daoFactory.getTransactionDAO().create(transaction)){
            makeTransaction(transaction.getIdUserSender(),transaction.getIdUserReceiver(),transaction.getAmount(),transaction);
            return true;
        }
            return false;
    }

    public Transaction read(int id) {
        return daoFactory.getTransactionDAO().read(id);
    }

    public boolean update(Transaction transaction) throws SQLException {
        return daoFactory.getTransactionDAO().update(transaction);
    }

    public boolean delete(int id) throws SQLException {
        return daoFactory.getTransactionDAO().delete(id);
    }

    private void makeTransaction(int idUserSender, int idUserReceiver, double amount,Transaction transaction) throws SQLException {
        User userSender = daoFactory.getUserDAO().read(idUserSender);
        User userReceiver = daoFactory.getUserDAO().read(idUserReceiver);
        double fees = amount*0.05;
        userSender.setBalance(userSender.getBalance()-amount-fees);
        userReceiver.setBalance(userReceiver.getBalance()+amount);
        transaction.setFees(fees);
        daoFactory.getUserDAO().update(userSender);
        daoFactory.getUserDAO().update(userReceiver);
        update(transaction);
    }


    public List<Transaction> listAll() {
        return daoFactory.getTransactionDAO().listAll();
    }
}
