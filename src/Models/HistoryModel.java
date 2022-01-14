package Models;

import Main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HistoryModel {
    
    public ResultSet printHistory( ) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM transaction WHERE acc_num1=?;";
        PreparedStatement stmt = Main.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, OperationsModel.getAccNumber());
        ResultSet set = stmt.executeQuery();
        return set;
    }
}
