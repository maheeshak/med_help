package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Cart;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.dto.tm.OrderPreviewTM;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oId, List<Cart> cartDTOList) throws SQLException {

        for (Cart dto : cartDTOList) {
            if (!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, Cart dto) throws SQLException {

        String sql = "INSERT INTO order_item(order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)";

        return CrudUtil.execute(sql, oId, dto.getCode(), dto.getQty(), dto.getUnitPrice());

    }

    public static List<OrderPreviewTM> getOrderItems(String order_id) throws SQLException {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, order_id);

        List<OrderPreviewTM> data = new ArrayList<>();

        while (resultSet.next()) {

            String item_id = resultSet.getString(2);
            Item item = ItemModel.searchById(item_id);
            String qty = resultSet.getString(3);
            double unit_price = resultSet.getDouble(4);

            double value = Double.parseDouble(qty);
            double total = unit_price * value;


            data.add(new OrderPreviewTM(item_id, item.getItem_name(), qty, unit_price, total));

        }
        return data;

    }
}
