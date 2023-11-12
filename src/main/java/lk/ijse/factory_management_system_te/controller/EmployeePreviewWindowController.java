package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.factory_management_system_te.dto.Employee;
import lk.ijse.factory_management_system_te.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeePreviewWindowController implements Initializable {
    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextArea txtSclAtt;

    @FXML
    private TextArea txtEduQul;

    @FXML
    private TextField txtNationality;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtReligion;

    @FXML
    private TextArea txtWorkExp;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private ComboBox<String> cmbDesignation;

    public static Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDesignation();
        setGender();
        setValues();
    }

    private void setValues() {
        txtEmpId.setText(employee.getEmp_id());
        txtName.setText(employee.getEmp_name());
        txtAddress.setText(employee.getEmp_address());
        txtMobile.setText(employee.getEmp_contact());
        txtDob.setText(employee.getDob());
        txtAge.setText(String.valueOf(employee.getAge()));
        txtNationality.setText(employee.getNationality());
        txtReligion.setText(employee.getReligion());
        txtNic.setText(employee.getNic());
        txtStatus.setText(employee.getCivil_status());
        txtSclAtt.setText(employee.getSch_attend());
        txtEduQul.setText(employee.getEdu_qul());
        cmbDesignation.setValue(employee.getDesignation());
        txtWorkExp.setText(employee.getWork_exp());
        cmbGender.setValue(employee.getGender());
        txtSalary.setText(employee.getCurrent_sal());
    }

    private void setGender() {
        String[] gender = {null, "Male", "Female"};
        cmbGender.getItems().addAll(gender);
    }

    private void setDesignation() {
        String[] designation = {null, "Engineer", "Security Officer", "Deliver"};
        cmbDesignation.getItems().addAll(designation);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtMobile.getText();
        String dob = txtDob.getText();
        Integer age = Integer.valueOf(txtAge.getText());
        String nationality = txtNationality.getText();
        String religion = txtReligion.getText();
        String nic = txtNic.getText();
        String status = txtStatus.getText();
        String att = txtSclAtt.getText();
        String eduQul = txtEduQul.getText();
        String designation = cmbDesignation.getSelectionModel().getSelectedItem();
        String workEx = txtWorkExp.getText();
        String gender = cmbGender.getSelectionModel().getSelectedItem();
        String salary = txtSalary.getText();
        try {
            boolean isUpdated = EmployeeModel.update(new Employee(id, name, address, contact, dob, age, nationality, religion, nic, status,
                    att, eduQul, designation, workEx, gender, salary));

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated !!!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not Updated !!!").show();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
