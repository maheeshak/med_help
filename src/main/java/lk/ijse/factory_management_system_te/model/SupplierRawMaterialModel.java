package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.db.DBConnection;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierRawMaterialModel {
    public static boolean add(String supId, String raw_id, String total, LocalDate now) throws SQLException {
        String sql = "INSERT INTO supplier_raw_material(sup_id,raw_id,cost,date) VALUES(?,?,?,?)";
        return CrudUtil.execute(sql, supId, raw_id, total, now);
    }

    public static String expenses() throws SQLException {
        String sql = "SELECT SUM(cost) FROM supplier_raw_material";
        ResultSet resultSet = CrudUtil.execute(sql);
        String value = "000.00";
        if (resultSet.next()) {
            value = resultSet.getString(1);

        }
        return value;
    }

    public static boolean update(String rawId, String supId, String newQty, String total) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isUpdate = RawMaterialModel.updateQty(rawId, newQty);

            if (isUpdate) {
                boolean isAdd = SupplierRawMaterialModel.add(supId, rawId, total, LocalDate.now());

                if (isAdd) {
                    con.setAutoCommit(true);
                    return true;
                }

            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(true);
        }
    }
}
