package com.paymybuddy.exchange.constants;

public class DBConstants {

    public static final String SAVE_USER = "insert into paymybuddy_prod.users(first_name, last_name, email, balance, password) values(?,?,?,?,?)";
    public static final String GET_USER = "SELECT * FROM paymybuddy_prod.users where id=?";
    public static final String UPDATE_USER = "update paymybuddy_prod.users set first_name=?, last_name=?, email=?, balance=?, password=? where id=?";
    public static final String DELETE_USER = "DELETE FROM paymybuddy_prod.users where id=?";
    public static final String GET_ALL_USERS = "SELECT * FROM paymybuddy_prod.users";
    public static final String GET_USER_BY_NAME = "SELECT * FROM paymybuddy_prod.users where first_name=? and last_name=?";

    public static final String SAVE_TRANSACTION = "insert into paymybuddy_prod.transactions(amount, id_user_sender, id_user_receiver, fees, id_description,type) values(?,?,?,?,?,?)";
    public static final String GET_TRANSACTION = "SELECT * FROM paymybuddy_prod.transactions where id=?";
    public static final String UPDATE_TRANSACTION = "update paymybuddy_prod.transactions set amount=?, id_user_sender=?, id_user_receiver=?, fees=?, id_description=? type=?, where id=?";
    public static final String DELETE_TRANSACTION = "DELETE FROM paymybuddy_prod.transactions where id=?";

}
