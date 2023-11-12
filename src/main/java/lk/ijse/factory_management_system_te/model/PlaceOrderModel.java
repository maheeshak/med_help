package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.db.DBConnection;
import lk.ijse.factory_management_system_te.dto.Cart;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {
    public static boolean placeOrder(String oId, String cusId, List<Cart> cartDTOList, String netTotal, String del_type) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = OrderModel.save(oId, cusId, LocalDate.now(),netTotal,del_type);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQty(cartDTOList);
                if (isUpdated) {
                    boolean isOrderDetailSaved = OrderDetailModel.save(oId, cartDTOList);
                    if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }
}
