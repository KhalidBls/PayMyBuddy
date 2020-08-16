package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.models.User;

public interface Manager<DataObject> {
    public boolean create(DataObject data);
    public DataObject read(int id);
    public boolean update(DataObject data);
    public boolean delete(int id);
}
