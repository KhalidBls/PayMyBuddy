package com.paymybuddy.exchange.config;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.Properties;


public class DatabaseConfig {

    public void initDataBaseRequestFromFile() throws SQLException, ClassNotFoundException, FileNotFoundException {
        Connection con = getConnection();
        System.out.println("Connection established......");
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("src/main/resources/Data.sql"));
        //Running the script
        sr.runScript(reader);
        closeConnection(con);
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dataBaseProperties().getProperty("db.url"),
                dataBaseProperties().getProperty("db.user"),
                dataBaseProperties().getProperty("db.password"));
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                System.out.println("Closing DB connection");
            } catch (SQLException e) {
                System.out.println("Error while closing connection");
            }
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
                System.out.println("Closing Prepared Statement");
            } catch (SQLException e) {
                System.out.println("Error while closing prepared statement");
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                System.out.println("Closing Result Set");
            } catch (SQLException e) {
                System.out.println("Error while closing result set");
            }
        }
    }

    private Properties dataBaseProperties() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("./config.properties")) {

            prop.load(input);
        } catch (IOException io) {
            System.out.println("Failed to store data base properties");
        }
        return prop;
    }

}
