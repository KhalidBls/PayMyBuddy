package com.paymybuddy.exchange.manager;

public interface Manager<DataObject> {

    void create(DataObject data);
    DataObject read(int id);
    void update(DataObject data);
    void delete(DataObject data);

}
