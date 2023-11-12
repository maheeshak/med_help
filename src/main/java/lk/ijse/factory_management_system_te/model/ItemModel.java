package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Cart;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static List<String> getCodes() throws SQLException {

        List<String> codes = new ArrayList<>();

        String sql = "SELECT item_id FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static Item searchById(String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";


        ResultSet resultSet = CrudUtil.execute(sql, code);
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
        }
        return null;
    }

    public static boolean updateQty(List<Cart> cartDTOList) throws SQLException {
        for (Cart dto : cartDTOList) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(Cart dto) throws SQLException {
        String sql = "UPDATE item SET qty = (qty - ?) WHERE item_id = ?";
        return CrudUtil.execute(sql, dto.getQty(), dto.getCode());
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT item_id FROM item WHERE action = 'true'";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static boolean add(String itemId, String itemName, String itemType, String qty, Double unitPrice) throws SQLException {
        String sql = "INSERT INTO item (item_id,item_name,item_type,qty,unit_price) VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql, itemId, itemName, itemType, qty, unitPrice);
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item WHERE action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(new Item(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
        }
        return items;
    }

    public static boolean updateItemQty(String itemId, String newQty) throws SQLException {
        String sql = "UPDATE item SET qty = (qty + ?) WHERE item_id = ?";

        return CrudUtil.execute(sql, newQty, itemId);
    }

    public static boolean remove(String itemId) throws SQLException {
        String sql = "UPDATE item SET action = ? WHERE item_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, itemId);
        return isDeleted;
    }

    public static Item find(String itemId) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        if (resultSet.next()) {
            return new Item(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5));
        }
        return null;
    }

    public static String count() throws SQLException {
        String sql = "SELECT COUNT(item_id) FROM item WHERE action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "00";

    }

    public static String getCurrentItemId() throws SQLException {
        String sql = "SELECT * FROM item WHERE action = 'true' ORDER BY item_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "I000";
    }
}
