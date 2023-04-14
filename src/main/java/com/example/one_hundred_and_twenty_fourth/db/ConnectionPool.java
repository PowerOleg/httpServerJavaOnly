package com.example.one_hundred_and_twenty_fourth.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {
    private String databaseUrl;
    private String userName;
    private String password;
    private int maxPoolSize = 10;
    private int connNum = 0;            //минимум всегда существующих коннекшенов


    private static final String SQL_VERIFYCONN = "select 1";
    private Stack<Connection> freePool = new Stack<>();
    private Set<Connection> occupiedPool = new HashSet<>();








    private static ConnectionPool instance = null;
    private ConnectionPool() {}

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }























    public synchronized Connection getConnection() throws SQLException {
        Connection conn = null;
        if (isFull()) {
            throw new SQLException("The connection pool is full");
        }
        conn = getConnectionFromFreePool();
        if (conn == null) {
            conn = createConnectionForPool();
        }
        conn = makeAvailable(conn);
        return conn;
    }

    public synchronized void returnConnection(Connection connection) throws SQLException {
        if (connection == null) {
            throw new NullPointerException();
        }
        if (!occupiedPool.remove(connection)) {
            throw new SQLException("The connection is returned already of it isn't for this pool");
        }
        freePool.push(connection);                                                                      //1
    }






















    private synchronized boolean isFull() {
        return ((freePool.size() == 0) && (connNum >= maxPoolSize));
    }

    private Connection createConnectionForPool() {
        Connection connection = createNewConnection();
        connNum++;
        occupiedPool.add(connection);
        return connection;
    }

    private Connection createNewConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection(databaseUrl, userName, password);
//                    "jdbc:mysql://localhost:3306/bd2", "root", "nasKFABFKAWFBAjk6135KBAFKAJB FAKLWJFN11354fafsefdxf3dx51351531fdxfdx1fdxf351531KUBJVJVBADKWDBAKDB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
                return connection;
    }

    private Connection getConnectionFromFreePool() {
        Connection connection = null;
        if (freePool.size() > 0) {
            connection = freePool.pop();                                                            //1
            occupiedPool.add(connection);                                                           //1
        }
        return connection;
    }

    private Connection makeAvailable(Connection connection) throws SQLException {
        if (isConnectionAvailable(connection)) {
            return connection;
        }
        occupiedPool.remove(connection);
        connNum--;
        connection.close();

        connection = createNewConnection();
        occupiedPool.add(connection);
        connNum++;
        return connection;
    }

    private boolean isConnectionAvailable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(SQL_VERIFYCONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }



    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getConnNum() {
        return connNum;
    }

    public Stack<Connection> getFreePool() {
        return freePool;
    }

    public Set<Connection> getOccupiedPool() {
        return occupiedPool;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setConnNum(int connNum) {
        this.connNum = connNum;
    }
}
