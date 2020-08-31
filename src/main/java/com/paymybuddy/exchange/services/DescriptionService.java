package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DescriptionService {

    @Autowired
    DAOFactory daoFactory;

    public boolean create(Description description) throws SQLException {
        return daoFactory.getDescriptionDAO().create(description);
    }

    public Description read(int id){
        return daoFactory.getDescriptionDAO().read(id);
    }

    public boolean update(Description description) throws SQLException {
        return daoFactory.getDescriptionDAO().update(description);
    }

    public boolean delete(int id) throws SQLException {
        return daoFactory.getDescriptionDAO().delete(id);
    }

    public List<Description> listAll(){
        return daoFactory.getDescriptionDAO().listAll();
    }

}
