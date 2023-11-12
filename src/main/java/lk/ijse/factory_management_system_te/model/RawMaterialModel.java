package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.db.DBConnection;
import lk.ijse.factory_management_system_te.dto.Cart;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.dto.RawCart;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialModel {
    public static RawMaterial searchById(String rawId) throws SQLException {
        String sql = "SELECT * FROM raw_material WHERE  raw_id = ? && action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql, rawId);
        if (resultSet.next()) {
            return new RawMaterial(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4));
        }
        return null;

    }

    public static boolean update(List<RawCart> rawCartDTOList) throws SQLException {
        for (RawCart dto : rawCartDTOList) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(RawCart dto) throws SQLException {
        String sql = "UPDATE raw_material SET qty = (qty - ?) WHERE raw_id = ?";
        return CrudUtil.execute(sql, dto.getQty(), dto.getRaw_id());
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT raw_id FROM raw_material WHERE action = 'true'";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static boolean add(RawMaterial rawMaterial, String supId, String total, LocalDate now) throws SQLException {
        Connection con = null;
try {
        con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        boolean isAdded = RawMaterialModel.addMaterial(rawMaterial);

        if (isAdded) {
            boolean isAdd = SupplierRawMaterialModel.add(supId, rawMaterial.getRaw_id(), total, now);
            if (isAdd) {
                con.setAutoCommit(true);
                return true;
            }
        }
        return false;
    } catch (SQLException er) {
        er.printStackTrace();
        con.rollback();
        return false;
    } finally {
        System.out.println("finally");
        con.setAutoCommit(true);
    }

    }

    private static boolean addMaterial(RawMaterial rawMaterial) throws SQLException {
        String sql = "INSERT INTO raw_material (raw_id,raw_desc,unit_price,qty) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql, rawMaterial.getRaw_id(), rawMaterial.getRaw_desc(), rawMaterial.getUnit_price(), rawMaterial.getQty());
    }

    public static List<RawMaterial> getAll() throws SQLException {
        String sql ="SELECT * FROM raw_material WHERE action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<RawMaterial> rawMaterials =new ArrayList<>();
        while (resultSet.next()){
            rawMaterials.add(new RawMaterial(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getDouble(3),resultSet.getString(4)));
        }
        return rawMaterials;
    }

    public static boolean updateQty(String rawId, String newQty) throws SQLException {
        String sql = "UPDATE raw_material SET qty = (qty + ?) WHERE raw_id = ?";
        return CrudUtil.execute(sql,newQty,rawId);
    }

    public static boolean remove(String raw_id) throws SQLException {
        String sql = "UPDATE raw_material SET action = ? WHERE raw_id = ?";
        boolean action = false;
        return CrudUtil.execute(sql,action,raw_id);
    }

    public static RawMaterial find(String raw_id) throws SQLException {
        String sql = "SELECT * FROM raw_material WHERE action = 'true' && raw_id = ?" ;

        ResultSet resultSet = CrudUtil.execute(sql,raw_id);

        if (resultSet.next()){
            return new RawMaterial(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getDouble(3),resultSet.getString(4));
        }
        return null;
    }

    public static boolean UpdateQty(String raw_id, String unit_price) throws SQLException {
        String sql = "UPDATE raw_material SET unit_price = ? WHERE raw_id = ?";
        return CrudUtil.execute(sql,unit_price,raw_id);
    }

    public static String count() throws SQLException {
        String sql = "SELECT COUNT(raw_id) FROM raw_material WHERE action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "00";

    }

    public static String getCurrentRawId() throws SQLException {
        String sql = "SELECT * FROM raw_material WHERE action = 'true' ORDER BY raw_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "I000";
    }
}
