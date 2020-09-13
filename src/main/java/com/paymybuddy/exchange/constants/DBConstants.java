package com.paymybuddy.exchange.constants;

public class DBConstants {

    public static final String SAVE_USER = "insert into users(first_name, last_name, email, balance, password) values(?,?,?,?,?)";
    public static final String GET_USER = "SELECT * FROM users where id=?";
    public static final String UPDATE_USER = "update users set first_name=?, last_name=?, email=?, balance=?, password=? where id=?";
    public static final String DELETE_USER = "DELETE FROM users where id=?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";

    public static final String SAVE_TRANSACTION = "insert into transactions(amount, id_user_sender, id_user_receiver, fees, id_description,type) values(?,?,?,?,?,?)";
    public static final String GET_TRANSACTION = "SELECT * FROM transactions where id=?";
    public static final String UPDATE_TRANSACTION = "update transactions set amount=?, id_user_sender=?, id_user_receiver=?, fees=?, id_description=? type=?, where id=?";
    public static final String DELETE_TRANSACTION = "DELETE FROM transactions where id=?";
    public static final String GET_ALL_TRANSACTIONS = "SELECT * FROM transactions";

    public static final String SAVE_BANK_ACCOUNT = "insert into bank_account(id_user, swift, iban) values(?,?,?)";
    public static final String GET_BANK_ACCOUNT = "SELECT * FROM bank_account where id=?";
    public static final String UPDATE_BANK_ACCOUNT = "update bank_account set iban=?, swift=?, id_user=? where id=?";
    public static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_account where id=?";
    public static final String GET_ALL_BANK_ACCOUNT = "SELECT * FROM bank_account";

    public static final String SAVE_DESCRIPTION = "insert into descriptions(content) values(?)";
    public static final String GET_DESCRIPTION ="SELECT * FROM descriptions where id=?";
    public static final String UPDATE_DESCRIPTION = "update descriptions set content=? where id=?";
    public static final String DELETE_DESCRIPTION ="DELETE FROM descriptions where id=?";
    public static final String GET_ALL_DESCRIPTION ="SELECT * FROM descriptions" ;

    public static final String SAVE_RELATIONSHIP = "insert into users_relationship (id_user_relating,id_user_related) values(?)";
    public static final String GET_RELATIONSHIP ="SELECT * FROM users_relationship where id=?" ;
    public static final String UPDATE_RELATIONSHIP = "update users_relationship set id_user_relating=? id_user_related=? date_creation=? where id=?";
    public static final String DELETE_RELATIONSHIP = "DELETE FROM users_relationship where id=?";
    public static final String GET_ALL_RELATIONSHIP = "SELECT * FROM users_relationship";
}
