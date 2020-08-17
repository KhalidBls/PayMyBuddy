package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.User;

import java.sql.SQLException;
import java.util.List;

public interface Manager<DataObject> {
    public boolean create(DataObject data) throws SQLException;
    public DataObject read(int id);
    public boolean update(DataObject data) throws SQLException;
    public boolean delete(int id) throws SQLException;
}
