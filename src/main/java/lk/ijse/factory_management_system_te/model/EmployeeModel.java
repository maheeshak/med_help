package lk.ijse.factory_management_system_te.model;

import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.dto.Employee;
import lk.ijse.factory_management_system_te.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static Boolean add(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (emp_id,emp_name,emp_address,emp_contact,dob,age,nationality,religion," +
                "nic,civil_stat,sch_attend,edu_qual,designation,work_exp,gender,current_sal) VALUES(?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql, employee.getEmp_id(), employee.getEmp_name(), employee.getEmp_address(),
                employee.getEmp_contact(), employee.getDob(), employee.getAge(), employee.getNationality(),
                employee.getReligion(), employee.getNic(), employee.getCivil_status(), employee.getSch_attend(),
                employee.getEdu_qul(), employee.getDesignation(), employee.getWork_exp(), employee.getGender(),
                employee.getCurrent_sal());
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee WHERE action='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Employee> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15),
                    resultSet.getString(16)));
        }
        return data;
    }

    public static boolean remove(String emp_id) throws SQLException {
        String sql = "UPDATE employee SET action = ? WHERE emp_id = ?";

        String action = "false";
        boolean isDeleted = CrudUtil.execute(sql, action, emp_id);
        return isDeleted;
    }

    public static Employee find(String emp_id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, emp_id);
        if (resultSet.next()) {
            return new Employee(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),
                    resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getString(15), resultSet.getString(16));

        }
        return null;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET emp_name = ? ,emp_address = ? ,emp_contact = ? ,dob = ? ,age = ? ,nationality = ? ,religion = ? ," +
                "nic = ? ,civil_stat = ? ,sch_attend = ? ,edu_qual = ? ,designation = ? ,work_exp = ? ,gender = ? ,current_sal = ? WHERE emp_id = ? ";

        return CrudUtil.execute(sql, employee.getEmp_name(),
                employee.getEmp_address(),
                employee.getEmp_contact(),
                employee.getDob(),
                employee.getAge(),
                employee.getNationality(),
                employee.getReligion(),
                employee.getNic(),
                employee.getCivil_status(),
                employee.getSch_attend(),
                employee.getEdu_qul(),
                employee.getDesignation(),
                employee.getWork_exp(),
                employee.getGender(),
                employee.getCurrent_sal(), employee.getEmp_id());
    }

    public static String value() throws SQLException {
        String sql = "SELECT COUNT(emp_id) FROM employee WHERE action ='true'";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "00";
    }

    public static String getCurrentEmployeeId() throws SQLException {
        String sql = "SELECT * FROM employee WHERE action = 'true' ORDER BY emp_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "E000";

    }
}
