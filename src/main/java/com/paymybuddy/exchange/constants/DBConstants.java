package com.paymybuddy.exchange.constants;

public class DBConstants {

    public static final String SAVE_USER = "insert into paymybuddy_prod.users(first_name, last_name, email, balance, password) values(?,?,?,?,?)";
    public static final String GET_USER = "SELECT * FROM paymybuddy_prod.users where id=?";
    public static final String UPDATE_USER = "update paymybuddy_prod.users set first_name=?, last_name=?, email=?, balance=?, password=? where id=?";
    public static final String DELETE_USER = "DELETE FROM paymybuddy_prod.users where id=?";
    public static final String GET_ALL_USERS = "SELECT * FROM paymybuddy_prod.users";

    public static final String SAVE_TRANSACTION = "insert into paymybuddy_prod.transactions(amount, id_user_sender, id_user_receiver, fees, id_description,type) values(?,?,?,?,?,?)";
    public static final String GET_TRANSACTION = "SELECT * FROM paymybuddy_prod.transactions where id=?";
    public static final String UPDATE_TRANSACTION = "update paymybuddy_prod.transactions set amount=?, id_user_sender=?, id_user_receiver=?, fees=?, id_description=? type=?, where id=?";
    public static final String DELETE_TRANSACTION = "DELETE FROM paymybuddy_prod.transactions where id=?";
    public static final String GET_ALL_TRANSACTIONS = "SELECT * FROM paymybuddy_prod.transactions";

    public static final String SAVE_BANK_ACCOUNT = "insert into paymybuddy_prod.bank_account(id_user, swift, iban) values(?,?,?)";
    public static final String GET_BANK_ACCOUNT = "SELECT * FROM paymybuddy_prod.bank_account where id=?";
    public static final String UPDATE_BANK_ACCOUNT = "update paymybuddy_prod.bank_account set iban=?, swift=?, id_user=? where id=?";
    public static final String DELETE_BANK_ACCOUNT = "DELETE FROM paymybuddy_prod.bank_account where id=?";
    public static final String GET_ALL_BANK_ACCOUNT = "SELECT * FROM paymybuddy_prod.bank_account";

    public static final String SAVE_DESCRIPTION = "insert into paymybuddy_prod.descriptions(content) values(?)";
    public static final String GET_DESCRIPTION ="SELECT * FROM paymybuddy_prod.descriptions where id=?";
    public static final String UPDATE_DESCRIPTION = "update paymybuddy_prod.descriptions set content=?";
    public static final String DELETE_DESCRIPTION ="DELETE FROM paymybuddy_prod.descriptions where id=?";
}
