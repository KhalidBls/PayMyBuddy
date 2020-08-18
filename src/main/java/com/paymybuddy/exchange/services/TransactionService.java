package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TransactionService {

    @Autowired
    DAOFactory daoFactory;

    public boolean create(Transaction transaction) throws SQLException {
        return daoFactory.getTransactionDAO().create(transaction);
    }

    public Transaction read(int id) {
        return daoFactory.getTransactionDAO().read(id);
    }
}
