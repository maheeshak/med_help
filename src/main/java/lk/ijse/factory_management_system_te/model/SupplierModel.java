package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Supplier;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean add(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier(sup_id,sup_name,nic,address,contact,email,reg_date) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql, supplier.getSup_id(), supplier.getSup_name(), supplier.getSup_nic(),
                supplier.getSup_address(), supplier.getSup_contact(), supplier.getSup_email(),LocalDate.now());
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier WHERE action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Supplier> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Supplier(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
        }
        return data;
    }

    public static boolean remove(String sup_id) throws SQLException {
        String sql = "UPDATE supplier SET action = ? WHERE sup_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, sup_id);
        return isDeleted;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT sup_id FROM supplier WHERE action = 'true'";

        List<String> ids = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Supplier searchById(String supId) throws SQLException {
        String sql ="SELECT * FROM supplier WHERE sup_id = ? && action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql,supId);

        if(resultSet.next()){
            return new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
        }
        return null;
    }

    public static Supplier find(String sup_id) throws SQLException {
        String sql ="SELECT * FROM supplier WHERE sup_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,sup_id);

        if (resultSet.next()){

            return new Supplier(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));

        }
        return null;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql ="UPDATE supplier SET sup_name = ? , nic = ? , address = ? , contact = ? , email = ? WHERE sup_id = ?";
        return CrudUtil.execute(sql,supplier.getSup_name(),supplier.getSup_nic(),supplier.getSup_address(),supplier.getSup_contact(),supplier.getSup_email(),supplier.getSup_id());
    }

    public static String todaySupplier() throws SQLException {
        String date = String.valueOf(LocalDate.now());

        String sql = "SELECT * FROM supplier WHERE reg_date = ? AND action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql, date);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        return String.valueOf(count);

    }

    public static String totalSupplier() throws SQLException {

        String sql = "SELECT COUNT(sup_id) FROM supplier WHERE action ='true'";

        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "00";
    }

    public static Integer[] getSupplierValueMonths() throws SQLException {
        Integer[] data = new Integer[12];
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        String sql = "SELECT MONTH(reg_date), COUNT(sup_id) FROM supplier WHERE action = 'true' GROUP BY MONTH(reg_date) ";

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            switch (resultSet.getString(1)) {

                case "1":
                    jan = Integer.parseInt(resultSet.getString(2));
                    break;
                case "2":
                    feb = Integer.parseInt(resultSet.getString(2));
                    break;
                case "3":
                    mar = Integer.parseInt(resultSet.getString(2));
                    break;
                case "4":
                    apr = Integer.parseInt(resultSet.getString(2));
                    break;
                case "5":
                    may = Integer.parseInt(resultSet.getString(2));
                    break;
                case "6":
                    jun = Integer.parseInt(resultSet.getString(2));
                    break;
                case "7":
                    jul = Integer.parseInt(resultSet.getString(2));
                    break;
                case "8":
                    aug = Integer.parseInt(resultSet.getString(2));
                    break;
                case "9":
                    sep = Integer.parseInt(resultSet.getString(2));
                    break;
                case "10":
                    oct = Integer.parseInt(resultSet.getString(2));
                    break;
                case "11":
                    nov = Integer.parseInt(resultSet.getString(2));
                    break;
                case "12":
                    dec = Integer.parseInt(resultSet.getString(2));
                    break;

            }

            data[0] = jan;
            data[1] = feb;
            data[2] = mar;
            data[3] = apr;
            data[4] = may;
            data[5] = jun;
            data[6] = jul;
            data[7] = aug;
            data[8] = sep;
            data[9] = oct;
            data[10] = nov;
            data[11] = dec;
        }
        return data;


    }

    public static String getCurrentSupId() throws SQLException {
        String sql = "SELECT * FROM supplier WHERE action = 'true' ORDER BY sup_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "S000";
    }
}
