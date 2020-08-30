package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    DAOFactory daoFactory;

    public boolean create(BankAccount bankAccount) throws SQLException {
        return daoFactory.getBankAccountDAO().create(bankAccount);
    }

    public BankAccount read(int id){
        return daoFactory.getBankAccountDAO().read(id);
    }

    public boolean update(BankAccount bankAccount) throws SQLException {
        return daoFactory.getBankAccountDAO().update(bankAccount);
    }

    public boolean delete(int id) throws SQLException {
        return daoFactory.getBankAccountDAO().delete(id);
    }

    public List<BankAccount> listAll(){
        return daoFactory.getBankAccountDAO().listAll();
    }

}
