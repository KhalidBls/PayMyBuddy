package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.User;

import java.sql.SQLException;
import java.util.List;

public interface DAO<DataObject> {
     boolean create(DataObject data) throws SQLException;
     DataObject read(int id);
     boolean update(DataObject data) throws SQLException;
     boolean delete(int id) throws SQLException;
     List<DataObject> listAll();

}
