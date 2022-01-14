package Models;

import Main.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterModel {
    
    private PreparedStatement customer = null, account = null;
    private ResultSet rs;
    
    public RegisterModel(){}
    
    public boolean checkInformation(String firstname, String lastname, String phoneNumber) {
        
        try {
            String sql = "SELECT * FROM customer WHERE firstname=? AND lastname=? AND phonenumber=?";
            customer = Main.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            customer.setString(1, firstname);
            customer.setString(2, lastname);
            customer.setString(3, phoneNumber);
            rs = customer.executeQuery();
            if(rs.next())
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(RegisterModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public Boolean createAccount(String firstname, String lastname, String phonenumber,
            String address, String BDay, String gender) throws SQLException{
        String sql = "INSERT INTO "
                + "customer(firstname, lastname, phonenumber, address, date_of_birth, gender) "
                + "VALUES(?,?,?,?,?,?);";
        customer = Main.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        customer.setString(1, firstname);
        customer.setString(2, lastname);
        customer.setString(3, phonenumber);
        customer.setString(4, address);
        customer.setString(5, BDay);
        customer.setString(6, gender);
        if(customer.executeUpdate() == 1){
            System.out.println("Data insert successfully");
            rs = customer.getGeneratedKeys();
            if(rs.next()){
                int cust_ID = rs.getInt(1);
                String sql2 = "INSERT INTO account(acc_pass, acc_balance, cust_number) VALUES(?,?,?);";
                account = Main.con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                int pass = (int) (Math.random() * 10000);
                account.setInt(1, pass);
                account.setInt(2, 0);
                account.setInt(3, cust_ID);
                if(account.executeUpdate() == 1){
                    System.out.println("Account created successfully");
                    rs = account.getGeneratedKeys();
                    if(rs.next()){
                        int acc_number = rs.getInt(1);
                        OperationsModel.setCustomerID(cust_ID);
                        OperationsModel.setAccNumber(acc_number);
                        OperationsModel.setAccPassword(pass);
                        OperationsModel.setUserTXT("Username: " +acc_number);
                        OperationsModel.setGreetingTXT("Hello " +firstname);
                        rs.close();
                        customer.close();
                        account.close();
                    }
                }
            }
        }
        else{
            return false;
        }
        return true;
    }
    
    public boolean is_8Digit(String nb) {
        String[] arr = nb.split("");
        return arr.length == 8;
    }
}