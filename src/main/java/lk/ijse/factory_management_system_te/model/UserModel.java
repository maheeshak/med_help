package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean save(String userId, String userName, String password) throws SQLException {
        String sql="INSERT INTO admin(user_id,user_name,password) VALUES(?,?,?)";
        return CrudUtil.execute(sql,userId,userName,password);
    }

    public static boolean valid(String userName, String password) throws SQLException {
        String sql="SELECT * FROM admin WHERE user_name = ?";
        ResultSet resultSet=CrudUtil.execute(sql,userName);
        if(resultSet.next()){
            if(password.equals(resultSet.getString(3))){
                return true;
            }
            return false;
        }
        return false;
    }
}
