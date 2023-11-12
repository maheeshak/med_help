package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.db.DBConnection;
import lk.ijse.factory_management_system_te.dto.RawCart;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddItemModel {
    public static boolean addItem(String itemId, String itemName, String itemType, String qty, Double unitPrice, List<RawCart> rawCartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isAdd = ItemModel.add(itemId, itemName, itemType, qty, unitPrice);
            if (isAdd) {
                boolean isUpdated = RawMaterialModel.update(rawCartDTOList);
                if (isUpdated) {
                    boolean isAdded = RawMaterialItemModel.add(itemId,rawCartDTOList);
                    if(isAdded) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }


}
