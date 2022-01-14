package DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn = null;

    private DBConnection() {
    }

    ;

    public static Connection makeConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3308/atm_db";
                String user = "root";
                String pass = "theinternship1234";
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException | ClassNotFoundException ex) {
                //Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                //throw ex;
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        } else {
            System.out.println("Already connected to database...");
        }
        return conn;
    }

    public static void DBDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
