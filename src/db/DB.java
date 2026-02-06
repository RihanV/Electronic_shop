package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/electronic_shop";
        String user = "root";
        String password = "Rihan@123";

        Connection conn = DriverManager.getConnection(url, user, password);
        return DriverManager.getConnection(url, user, password);
    }
}
