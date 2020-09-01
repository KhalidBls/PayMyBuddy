package com.paymybuddy.exchange.dao;

import com.paymybuddy.exchange.config.DatabaseConfig;
import com.paymybuddy.exchange.constants.DBConstants;
import com.paymybuddy.exchange.models.UserRelationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRelationshipDAO  implements DAO<UserRelationship>{

    DatabaseConfig dataBaseConfig = new DatabaseConfig();

    @Override
    public boolean create(UserRelationship userRelationship) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.SAVE_RELATIONSHIP);
            ps.setInt(1,userRelationship.getIdUserRelating());
            ps.setInt(2,userRelationship.getIdUserRelated());
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
    public UserRelationship read(int id) {
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        UserRelationship userRelationship = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_RELATIONSHIP);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                userRelationship = new UserRelationship();
                userRelationship.setId(rs.getInt("id"));
                userRelationship.setIdUserRelating(rs.getInt("id_user_relating"));
                userRelationship.setIdUserRelated(rs.getInt("id_user_related"));
                userRelationship.setTimestampOfCreation(rs.getLong("date_creation"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return userRelationship;
        }
    }

    @Override
    public boolean update(UserRelationship userRelationship) throws SQLException {
        Connection con = null;
        PreparedStatement ps=null;
        try {
            con = dataBaseConfig.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DBConstants.UPDATE_RELATIONSHIP);
            ps.setInt(1,userRelationship.getIdUserRelating());
            ps.setInt(2,userRelationship.getIdUserRelated());
            ps.setLong(3,userRelationship.getTimestampOfCreation());
            ps.setInt(4,userRelationship.getId());
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
            ps = con.prepareStatement(DBConstants.DELETE_RELATIONSHIP);
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
    public List<UserRelationship> listAll() {
        List<UserRelationship> allUserRelationship = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        UserRelationship userRelationship;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_ALL_RELATIONSHIP);
            rs = ps.executeQuery();
            while(rs.next()){
                userRelationship = new UserRelationship();
                userRelationship.setId(rs.getInt("id"));
                userRelationship.setIdUserRelating(rs.getInt("id_user_relating"));
                userRelationship.setIdUserRelated(rs.getInt("id_user_related"));
                userRelationship.setTimestampOfCreation(rs.getLong("date_creation"));
                allUserRelationship.add(userRelationship);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return allUserRelationship;
        }
    }

}
