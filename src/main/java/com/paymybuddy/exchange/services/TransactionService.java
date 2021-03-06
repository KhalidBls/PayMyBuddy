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

    @Autowired
    UserService userService;


    public boolean create(Transaction transaction) throws SQLException {
        if(DAOFactory.getTransactionDAO().create(transaction)){
            makeTransaction(transaction);
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

    private boolean makeTransaction(Transaction transaction){
        try {

            if(userRelationshipService.verifyRelationship(transaction.getIdUserSender(),transaction.getIdUserReceiver())==null)
                throw new RelationshipException();

            User userSender = userService.read(transaction.getIdUserSender());
            User userReceiver = userService.read(transaction.getIdUserReceiver());
            transaction.setId(getTransactionByIdUserAndSender(transaction.getIdUserSender(),transaction.getIdUserReceiver()).getId());

            double fees =  (double) Math.round((transaction.getAmount() * 0.05) * 100)/100;
            userSender.setBalance((double) Math.round((userSender.getBalance() - transaction.getAmount() - fees)*100)/100);
            userReceiver.setBalance((double) Math.round((userReceiver.getBalance() + transaction.getAmount())*100)/100);
            transaction.setFees(fees);
            userService.update(userSender);
            userService.update(userReceiver);
            update(transaction);
            return true;
        }catch (SQLException | RelationshipException e){
            e.printStackTrace();
            return false;
        }
    }

    public Transaction getTransactionByIdUserAndSender(int idUserSender, int idUserReceiver){
        for (Transaction transaction: listAll()) {
            if (transaction.getIdUserSender()==idUserSender && transaction.getIdUserReceiver()==idUserReceiver)
                return transaction;
        }
        return null;
    }

}
