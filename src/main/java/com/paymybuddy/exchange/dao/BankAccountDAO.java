package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.config.DatabaseConfig;
import com.paymybuddy.exchange.constants.DBConstants;
import com.paymybuddy.exchange.models.BankAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAO implements DAO<BankAccount> {

    public DatabaseConfig dataBaseConfig = new DatabaseConfig();

    @Override
    public boolean create(BankAccount bankAccount) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.SAVE_BANK_ACCOUNT);
            ps.setInt(1,bankAccount.getIdUser());
            ps.setString(2,bankAccount.getSwift());
            ps.setString(3,bankAccount.getIban());
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
    public BankAccount read(int id) {
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        BankAccount bankAccount = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_BANK_ACCOUNT);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                bankAccount = new BankAccount();
                bankAccount.setId(rs.getInt("id"));
                bankAccount.setIban(rs.getString("iban"));
                bankAccount.setSwift(rs.getString("swift"));
                bankAccount.setIdUser(rs.getInt("id_user"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return bankAccount;
        }
    }

    @Override
    public boolean update(BankAccount bankAccount) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.UPDATE_BANK_ACCOUNT);
            ps.setString(1,bankAccount.getIban());
            ps.setString(2,bankAccount.getSwift());
            ps.setInt(3,bankAccount.getIdUser());
            ps.setInt(4,bankAccount.getId());
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
            ps = con.prepareStatement(DBConstants.DELETE_BANK_ACCOUNT);
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
    public List<BankAccount> listAll() {
        List<BankAccount> allBankAccount = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        BankAccount bankAccount;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_ALL_BANK_ACCOUNT);
            rs = ps.executeQuery();
            while(rs.next()){
                bankAccount = new BankAccount();
                bankAccount.setId(rs.getInt("id"));
                bankAccount.setIban(rs.getString("iban"));
                bankAccount.setSwift(rs.getString("swift"));
                bankAccount.setIdUser(rs.getInt("id_user"));
                allBankAccount.add(bankAccount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return allBankAccount;
        }
    }
}
