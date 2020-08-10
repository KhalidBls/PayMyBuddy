package com.paymybuddy.exchange.manager;

import com.paymybuddy.exchange.models.User;

import java.util.List;

public interface Manager<DataObject> {
    void create(DataObject data);

    DataObject read(int id);

    void update(DataObject data);

    void delete(DataObject data);

}
