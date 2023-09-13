package com.crochepoint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crochepoint.exceptions.DBException;

public class ConnectionSQLite {
    static private Connection conn = null;
    static private boolean startedDB = false;

    public static void startDB() {
        if (startedDB)
            return;
        List<String> list = new ArrayList<>();
        list.add("create table if not exists simple_time(id string primary key, timeStart DateTime null, timeEnd DateTime null, listTime_id String not null)");
        list.add("create table if not exists list_times(id string primary key, created DateTime not null)");
        execQuery(list);
        startedDB = true;
    }

    private static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void execQuery(List<String> querys) {
        Statement statement;
        try {
            statement = getConnection().createStatement();
            for (String query:querys) {
                    statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static void execQuery(String query) {
        List<String> querys = new ArrayList<>();
        querys.add(query);
        execQuery(querys);
    }

    public static ResultSet resultQuery(String query) {
        Statement statement;
        try {
            statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static PreparedStatement prepareStatement(String sql) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}
