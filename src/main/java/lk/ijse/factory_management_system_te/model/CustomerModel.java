package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomerModel {
    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer WHERE action='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Customer> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    String.valueOf(resultSet.getDouble(13)),
                    resultSet.getString(14),
                    resultSet.getString(15),
                    String.valueOf(LocalDate.now()),
                    resultSet.getString(18)));
        }
        return data;
    }

    public static int getCustomerValue() throws SQLException {
        Integer value = 0;
        String sql = "SELECT COUNT(cus_id) FROM customer WHERE action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            value = Integer.valueOf(resultSet.getString(1));
        }
        return value;
    }

    public static boolean add(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer(user_id, cus_id, company_name,address,mode_of_transport,delivery_address,contact," +
                "email, cus_nature,com_vat_reg_no,area_business,no_yrs_business, credit_amount," +
                "per_credit,acc_number, reg_date , bank) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql, customer.getUser_id(), customer.getCus_id(), customer.getName(), customer.getAddress(), customer.getMod_of_trans(), customer.getDelivery_address()
                , customer.getContact(), customer.getEmail(), customer.getCus_nature(), customer.getVat(), customer.getArea_business(), customer.getNo_yrs_business(),
                customer.getCredit_amount(), customer.getPer_credit(), customer.getAcc_number(), customer.getReg_date(), customer.getBank());

    }

    public static int getValueMonth() throws SQLException {
        String date = String.valueOf(LocalDate.now());

        String sql = "SELECT * FROM customer WHERE reg_date = ? AND action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql, date);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        return count;

    }

    public static List<String> getIds() throws SQLException {

        String sql = "SELECT cus_id FROM customer WHERE action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<String> ids = new ArrayList<>();

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static String searchById(String cus_id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cus_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, cus_id);

        if (resultSet.next()) {
            return resultSet.getString(3);

        }
        return null;
    }

    public static boolean remove(String cus_id) throws SQLException {
        String sql = "UPDATE customer SET action = ? WHERE cus_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, cus_id);
        return isDeleted;
    }


    public static Customer find(String cus_id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cus_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, cus_id);
        if (resultSet.next()) {
            return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                    resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getString(15), resultSet.getString(16), resultSet.getString(18));

        }
        return null;

    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET user_id = ?, company_name =?,address =?,mode_of_transport =?,delivery_address =?,contact =?," +
                "email =?, cus_nature =?,com_vat_reg_no =?,area_business =?,no_yrs_business =? , credit_amount = ?," +
                "per_credit =?,acc_number = ?, bank = ? WHERE cus_id = ?";
        return CrudUtil.execute(sql, customer.getUser_id(), customer.getName(), customer.getAddress(), customer.getMod_of_trans(), customer.getDelivery_address()
                , customer.getContact(), customer.getEmail(), customer.getCus_nature(), customer.getVat(), customer.getArea_business(), customer.getNo_yrs_business(),
                customer.getCredit_amount(), customer.getPer_credit(), customer.getAcc_number(), customer.getBank(), customer.getCus_id());


    }

    public static Integer[] getCustomerValueMonths() throws SQLException {
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

        String sql = "SELECT MONTH(reg_date), COUNT(cus_id) FROM customer WHERE action = 'true' GROUP BY MONTH(reg_date) ";

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

    public static String getCurrentCustID() throws SQLException {
        String sql = "SELECT * FROM customer WHERE action = 'true' ORDER BY cus_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(2);
        }
        return "C000";
    }
}
