package com.paymybuddy.exchange.constants;

public class DBConstants {

    public static final String SAVE_USER = "insert into users(first_name, last_name, email, balance, password) values(?,?,?,?,?)";
    public static final String GET_USER = "SELECT * FROM users where id=?";
    public static final String UPDATE_USER = "update users set first_name=?, last_name=?, email=?, balance=?, password=? where id=?";
    public static final String DELETE_USER = "DELETE FROM users where id=?";

    public static final String SAVE_TRANSACTION = "insert into transactions(amount, id_user_sender, id_user_receiver, fees, id_description,type) values(?,?,?,?,?,?)";
    public static final String GET_TRANSACTION = "SELECT * FROM transactions where id=?";
    public static final String UPDATE_TRANSACTION = "update TRANSACTIONS set amount=?, id_user_sender=?, id_user_receiver=?, fees=?, id_description=? type=?, where id=?";
    public static final String DELETE_TRANSACTION = "DELETE FROM transactions where id=?";

}
