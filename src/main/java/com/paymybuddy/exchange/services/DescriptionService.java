package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.Description;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DescriptionService {


    public boolean create(Description description) throws SQLException {
        return DAOFactory.getDescriptionDAO().create(description);
    }

    public Description read(int id){
        return DAOFactory.getDescriptionDAO().read(id);
    }

    public boolean update(Description description) throws SQLException {
        return DAOFactory.getDescriptionDAO().update(description);
    }

    public boolean delete(int id) throws SQLException {
        return DAOFactory.getDescriptionDAO().delete(id);
    }

    public List<Description> listAll(){
        return DAOFactory.getDescriptionDAO().listAll();
    }

}
