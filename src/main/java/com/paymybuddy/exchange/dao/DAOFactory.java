package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.models.User;
import org.springframework.stereotype.Service;

@Service
 public class DAOFactory {

    public static DAO<User> getUserDAO(){
        return new UserDAO();
    }

    public static DAO<Transaction> getTransactionDAO(){
        return new TransactionDAO();
    }

}
