package com.paymybuddy.exchange.integration.services;

import com.paymybuddy.exchange.integration.config.DataBaseTestConfig;

import java.sql.Connection;

public class DataBasePrepareService {

    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //clear tables entries;
            connection.prepareStatement("truncate table bank_account").execute();
            connection.prepareStatement("truncate table descriptions").execute();
            connection.prepareStatement("truncate table transactions").execute();
            connection.prepareStatement("truncate table users").execute();
            connection.prepareStatement("truncate table users_relationship").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }

}
