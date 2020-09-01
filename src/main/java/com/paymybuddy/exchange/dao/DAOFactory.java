package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.*;



public class DAOFactory {

    public static DAO<User> getUserDAO(){
        return new UserDAO();
    }

    public static DAO<Transaction> getTransactionDAO(){
        return new TransactionDAO();
    }

    public static DAO<BankAccount> getBankAccountDAO(){return new BankAccountDAO();}

    public static DAO<Description> getDescriptionDAO(){return new DescriptionDAO();}

    public static DAO<UserRelationship> getUserRelationshipDAO(){return new UserRelationshipDAO();}

}
