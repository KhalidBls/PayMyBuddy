package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.configuration.DatabaseConfig;
import com.paymybuddy.exchange.constants.DBConstants;
import com.paymybuddy.exchange.models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TransactionDAO implements DAO<Transaction> {

    public DatabaseConfig dataBaseConfig = new DatabaseConfig();


    public boolean create(Transaction transaction) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.SAVE_TRANSACTION);
            ps.setDouble(1,transaction.getAmount());
            ps.setInt(2,transaction.getIdUserSender());
            ps.setInt(3,transaction.getIdUserReceiver());
            ps.setDouble(4,transaction.getFees());
            ps.setInt(5,transaction.getIdDescription());
            ps.setString(6,transaction.getType());
            ps.execute();
            con.commit();
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
            return false;
        }finally {
            con.setAutoCommit(true);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return true;
        }
    }



    @Override
    public Transaction read(int id) {
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Transaction transaction = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_TRANSACTION);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setIdUserSender(rs.getInt("id_user_sender"));
                transaction.setIdUserReceiver(rs.getInt("id_user_receiver"));
                transaction.setFees(rs.getDouble("fees"));
                transaction.setIdDescription(rs.getInt("id_description"));
                transaction.setType(rs.getString("type"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return transaction;
        }
    }

    @Override
    public boolean update(Transaction transaction) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.UPDATE_TRANSACTION);
            ps.setDouble(1,transaction.getAmount());
            ps.setInt(2,transaction.getIdUserSender());
            ps.setInt(3,transaction.getIdUserReceiver());
            ps.setDouble(4,transaction.getFees());
            ps.setInt(5,transaction.getIdDescription());
            ps.setString(6,transaction.getType().toString());
            ps.setInt(7,transaction.getId());
            ps.execute();
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(true);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return true;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.DELETE_TRANSACTION);
            ps.setInt(1,id);
            ps.executeUpdate();
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(true);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return true;
        }
    }

    @Override
    public List<Transaction> listAll() {
        List<Transaction> allTransactions = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Transaction transaction = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_ALL_TRANSACTIONS);
            rs = ps.executeQuery();
            while(rs.next()){
                transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setIdUserSender(rs.getInt("id_user_sender"));
                transaction.setIdUserReceiver(rs.getInt("id_user_receiver"));
                transaction.setFees(rs.getDouble("fees"));
                transaction.setIdDescription(rs.getInt("id_description"));
                transaction.setType(rs.getString("type"));
                allTransactions.add(transaction);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return allTransactions;
        }
    }
}
