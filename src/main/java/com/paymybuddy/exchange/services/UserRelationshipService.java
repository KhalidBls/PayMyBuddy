package com.paymybuddy.exchange.services;

import com.paymybuddy.exchange.dao.DAOFactory;
import com.paymybuddy.exchange.models.UserRelationship;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserRelationshipService {

    public boolean create(UserRelationship userRelationship) throws SQLException {
        return DAOFactory.getUserRelationshipDAO().create(userRelationship);
    }

    public UserRelationship read(int id){
        return DAOFactory.getUserRelationshipDAO().read(id);
    }

    public boolean update(UserRelationship userRelationship) throws SQLException {
        return DAOFactory.getUserRelationshipDAO().update(userRelationship);
    }

    public boolean delete(int id) throws SQLException {
        return DAOFactory.getUserRelationshipDAO().delete(id);
    }

    public List<UserRelationship> listAll(){
        return DAOFactory.getUserRelationshipDAO().listAll();
    }

    public UserRelationship verifyRelationship(int idUserRelating,int idUserRelated){
        List<UserRelationship> allRelation = listAll();
        for (UserRelationship userRelationship : allRelation){
            if (userRelationship.getIdUserRelating() == idUserRelating
                    && userRelationship.getIdUserRelated() == idUserRelated){
                return userRelationship;}
        }
        return null;
    }

}
