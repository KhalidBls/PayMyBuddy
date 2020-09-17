package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.exceptions.RelationshipException;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    UserRelationshipService userRelationshipService;


    public boolean create(Transaction transaction) throws SQLException {
        if(DAOFactory.getTransactionDAO().create(transaction)){
            makeTransaction(transaction.getIdUserSender(),transaction.getIdUserReceiver(),transaction.getAmount(),transaction);
            return true;
        }
            return false;
    }

    public Transaction read(int id) {
        return DAOFactory.getTransactionDAO().read(id);
    }

    public boolean update(Transaction transaction) throws SQLException {
        return DAOFactory.getTransactionDAO().update(transaction);
    }

    public boolean delete(int id) throws SQLException {
        return DAOFactory.getTransactionDAO().delete(id);
    }

    public List<Transaction> listAll() {
        return DAOFactory.getTransactionDAO().listAll();
    }

    private boolean makeTransaction(int idUserSender, int idUserReceiver, double amount,Transaction transaction){
        try {

            if(!userRelationshipService.verifyRelationship(idUserSender,idUserReceiver))
                throw new RelationshipException();

            User userSender = DAOFactory.getUserDAO().read(idUserSender);
            User userReceiver = DAOFactory.getUserDAO().read(idUserReceiver);

            double fees = amount * 0.05;
            userSender.setBalance(userSender.getBalance() - amount - fees);
            userReceiver.setBalance(userReceiver.getBalance() + amount);
            transaction.setFees(fees);
            DAOFactory.getUserDAO().update(userSender);
            DAOFactory.getUserDAO().update(userReceiver);
            update(transaction);
            return true;
        }catch (SQLException | RelationshipException e){
            e.printStackTrace();
            return false;
        }
    }

}
