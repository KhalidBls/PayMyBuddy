package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.config.DatabaseConfig;
import com.paymybuddy.exchange.constants.DBConstants;
import com.paymybuddy.exchange.models.Description;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DescriptionDAO implements DAO<Description>  {

    DatabaseConfig dataBaseConfig = new DatabaseConfig();

    @Override
    public boolean create(Description description) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.SAVE_DESCRIPTION);
            ps.setString(1,description.getContent());
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
    public Description read(int id) {
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Description description = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_DESCRIPTION);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                description = new Description();
                description.setId(rs.getInt("id"));
                description.setContent(rs.getString("content"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return description;
        }
    }

    @Override
    public boolean update(Description description) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.UPDATE_DESCRIPTION);
            ps.setString(1,description.getContent());
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
            ps = con.prepareStatement(DBConstants.DELETE_DESCRIPTION);
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
    public List<Description> listAll() {
        List<Description> allDescriptions = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Description description;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_ALL_BANK_ACCOUNT);
            rs = ps.executeQuery();
            while(rs.next()){
                description = new Description();
                description.setId(rs.getInt("id"));
                description.setContent(rs.getString("content"));
                allDescriptions.add(description);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return allDescriptions;
        }
    }
}
