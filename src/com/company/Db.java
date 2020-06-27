package com.company;

import java.sql.*;
public class Db {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/java ?useSSL=false&serverTimezone=UTC";
    static  String USER;
    static  String PASS;
    private Connection con;
    private Statement stmt;

    public Db() {
        // TODO 自动生成的构造函数存根
        con=null;
        stmt=null;
    }
    public boolean connect() throws Exception
    {
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL,USER,PASS);
        return true;
    }
    public boolean close() throws Exception
    {
        stmt.close();
        con.close();
        return true;
    }
    public boolean insertORupdateORdelete(String sql) throws Exception	//插入、更新、删除操作
    {
        connect();
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        close();
        return true;
    }
    public ResultSet inquire(String sql) throws Exception	//返回ResultSet对象
    {
        connect();
        ResultSet rs = null;
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        return rs;
    }

}
