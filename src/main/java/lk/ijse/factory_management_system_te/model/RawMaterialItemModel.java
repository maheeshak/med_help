package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Cart;
import lk.ijse.factory_management_system_te.dto.RawCart;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.dto.tm.AddItemTM;
import lk.ijse.factory_management_system_te.dto.tm.ItemTM;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialItemModel {
    public static boolean add(String itemId, List<RawCart> rawCartDTOList) throws SQLException {
        for (RawCart dto : rawCartDTOList) {
            if (!save(itemId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String itemId, RawCart dto) throws SQLException {

        String sql = "INSERT INTO raw_material_item (item_id,raw_id,qty) VALUES (?, ?, ?)";

        return CrudUtil.execute(sql, itemId, dto.getRaw_id(), dto.getQty());

    }

    public static List<AddItemTM> serachById(String itemId) throws SQLException {
        String sql = "SELECT * FROM raw_material_item WHERE item_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        List<AddItemTM> items = new ArrayList<>();
        while (resultSet.next()) {
            String rawId = resultSet.getString(2);
            Integer qty = Integer.valueOf(resultSet.getString(3));

            RawMaterial rawMaterial = RawMaterialModel.searchById(rawId);
            String raw_desc = rawMaterial.getRaw_desc();

            items.add(new AddItemTM(rawId, raw_desc, qty));
        }

        return items;
    }
}
