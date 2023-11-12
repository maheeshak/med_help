package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Order;
import lk.ijse.factory_management_system_te.dto.OrderDetails;
import lk.ijse.factory_management_system_te.dto.tm.OrderSummaryTM;
import lk.ijse.factory_management_system_te.util.CrudUtil;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException {
        String sql = "SELECT order_id FROM cus_order ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] strings = currentOrderId.split("O00-");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O00-" + id;
        }
        return "O00-1";
    }

    public static boolean save(String oId, String cusId, LocalDate now, String netTotal, String del_type) throws SQLException {
        String sql = "INSERT INTO cus_order(cus_id, order_id, order_date,order_amount,delivery_type) VALUES (?, ?, ?,?,?)";

        return CrudUtil.execute(sql, cusId, oId, now, netTotal, del_type);

    }

    public static int ordersValue() throws SQLException {
        Integer value = 0;
        String sql = "SELECT COUNT(order_id) FROM cus_order WHERE order_date = ? && action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql, String.valueOf(LocalDate.now()));
        if (resultSet.next()) {
            value = Integer.valueOf(resultSet.getString(1));
        }
        return value;
    }

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM cus_order WHERE action='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Order> data = new ArrayList<>();

        while (resultSet.next()) {

            String cus_id = resultSet.getString(1);
            String name = CustomerModel.searchById(cus_id);


            data.add(new Order(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), name, resultSet.getString(5)));

        }
        return data;

    }

    public static String sale() throws SQLException {
        String sql = "SELECT SUM(order_amount) FROM cus_order WHERE action = 'true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        String value = "000.00";
        if (resultSet.next()) {
            value = resultSet.getString(1);

        }
        return value;
    }


    public static List<OrderSummaryTM> getAllSummary() throws SQLException {
        String sql = "SELECT * FROM cus_order WHERE action = 'true' ORDER BY order_id DESC";

        ResultSet resultSet = CrudUtil.execute(sql);

        List<OrderSummaryTM> summaryList = new ArrayList<>();
        while (resultSet.next()) {
            summaryList.add(new OrderSummaryTM(resultSet.getString(2), resultSet.getDouble(4), resultSet.getString(3)
                    , resultSet.getString(5)));
        }


        return summaryList;
    }

    public static boolean remove(String order_id) throws SQLException {
        String sql = "UPDATE cus_order SET action = ? WHERE order_id = ?";

        return CrudUtil.execute(sql, false, order_id);
    }

    public static String getCount() throws SQLException {
        String sql = "SELECT COUNT(order_id) FROM cus_order WHERE action = 'true'";

        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "00";
    }

    public static Integer[] getOrderValueMonths() throws SQLException {
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

        String sql = "SELECT MONTH(order_date), COUNT(order_id) FROM cus_order WHERE action = 'true' GROUP BY MONTH(order_date) ";

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

    public static OrderDetails find(String order_id) throws SQLException {
        String sql = "SELECT * FROM cus_order WHERE order_id = ? AND action = 'true'";

        ResultSet resultSet = CrudUtil.execute(sql, order_id);

        if (resultSet.next()) {
            String name = CustomerModel.searchById(resultSet.getString(1));
            return new OrderDetails(order_id, resultSet.getString(1), name, resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5));
        }
        return null;
    }
}
