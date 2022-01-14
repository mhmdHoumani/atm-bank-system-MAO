package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginModel {
    
    private PreparedStatement stmt;
    private ResultSet rs;
    
    public LoginModel(){}
    
    public Boolean LoggingAccount(int username, int password){
        try {
            String sql = "SELECT cust_number "
                    + "FROM account "
                    + "WHERE acc_number=? AND acc_pass=?";
            stmt = Main.Main.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, username);
            stmt.setInt(2, password);
            rs = stmt.executeQuery();
            if(rs.next()){
                int custID = rs.getInt(1);
                String sql2 = "SELECT firstname FROM customer WHERE cust_ID=?";
                stmt = Main.Main.con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, custID);
                rs = stmt.executeQuery();
                if(rs.next()){
                    String name = rs.getString(1);
                    OperationsModel.setCustomerID(custID);
                    OperationsModel.setAccNumber(username);
                    OperationsModel.setAccPassword(password);
                    OperationsModel.setGreetingTXT("Welcome back " +name);
                    OperationsModel.setUserTXT("Username: " +username);
                }
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
