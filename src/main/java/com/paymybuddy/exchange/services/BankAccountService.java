package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.models.Transaction;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BankAccountService {

    public boolean create(BankAccount bankAccount) throws SQLException {
        return DAOFactory.getBankAccountDAO().create(bankAccount);
    }

    public BankAccount read(int id){
        return DAOFactory.getBankAccountDAO().read(id);
    }

    public boolean update(BankAccount bankAccount) throws SQLException {
        return DAOFactory.getBankAccountDAO().update(bankAccount);
    }

    public boolean delete(int id) throws SQLException {
        return DAOFactory.getBankAccountDAO().delete(id);
    }

    public List<BankAccount> listAll(){
        return DAOFactory.getBankAccountDAO().listAll();
    }

    public BankAccount getBankAccountByIdUser(int idUser){
        for (BankAccount bankAccount: listAll()) {
            if (bankAccount.getIdUser()==idUser)
                return bankAccount;
        }
        return null;
    }

}
