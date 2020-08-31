package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.models.Description;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.models.User;
import org.springframework.stereotype.Repository;
import sun.security.krb5.internal.crypto.Des;


@Repository
public class DAOFactory {

    public static DAO<User> getUserDAO(){
        return new UserDAO();
    }

    public static DAO<Transaction> getTransactionDAO(){
        return new TransactionDAO();
    }

    public static DAO<BankAccount> getBankAccountDAO(){return new BankAccountDAO();}

    public static DAO<Description> getDescriptionDAO(){return new DescriptionDAO();}

}
