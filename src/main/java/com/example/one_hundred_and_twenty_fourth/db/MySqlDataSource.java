//package com.example.one_hundred_and_twenty_fourth.db;
//
//import javax.sql.DataSource;
//
//public class MySqlDataSource {
//
//    private static DataSource datasource;
//    private static String driver = "com.mysql.cj.jdbc.Driver";
//    private static String url = "jdbc:mysql://localhost:3306/bd2";
//    private static String username = "root";
//    private static String password = "sadnasKFABFKAWFBAjk6135KBAFKAJB FAKLWJFN11354fafsefdxf3dx51351531fdxfdx1fdxf351531KUBJVJVBADKWDBAKDB";
//
//    public MySqlDataSource() {
//        datasource = new DataSource(configurePoolProperties(driver, url, username, password));
//    }
//
//    private PoolProperties configurePoolProperties(String driver, String url, String username, String password) {
//        PoolProperties properties = new PoolProperties();
//        properties.setDriverClassName(driver);
//        properties.setUrl(url);
//        properties.setUsername(username);
//        properties.setPassword(password);
//        return properties;
//    }
