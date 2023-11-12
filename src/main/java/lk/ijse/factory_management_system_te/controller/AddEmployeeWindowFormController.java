package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.factory_management_system_te.dto.Employee;
import lk.ijse.factory_management_system_te.model.EmployeeModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEmployeeWindowFormController implements Initializable {
    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtAddress;

    @FXML
    private DatePicker txtDob;

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

    @FXML
    private Label lblCurrentEmployeeID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDesignation();
        setGender();
        setCurrentEmployeeId();
    }

    private void setCurrentEmployeeId() {

        try {
         String employee_id = EmployeeModel.getCurrentEmployeeId();
            lblCurrentEmployeeID.setText(employee_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    void btnSaveOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if (isValid) {
            Employee employee = new Employee(
                    txtEmpId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtMobile.getText(),
                    String.valueOf(txtDob.getValue()),
                    Integer.valueOf(txtAge.getText()),
                    txtNationality.getText(),
                    txtReligion.getText(),
                    txtNic.getText(),
                    txtStatus.getText(),
                    txtSclAtt.getText(),
                    txtEduQul.getText(),
                    cmbDesignation.getSelectionModel().getSelectedItem(),
                    txtWorkExp.getText(),
                    cmbGender.getSelectionModel().getSelectedItem(),
                    txtSalary.getText());

            Boolean isEmpty = checkEmpty(employee);
            if (!isEmpty) {
                try {


                    Boolean isAdd = EmployeeModel.add(employee);
                    if (isAdd) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee is Added !!!").show();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Employee is not Added !!!").show();
                    }

                } catch (SQLException e) {
                 new Alert(Alert.AlertType.ERROR,"OOPS !!!").show();
                }
            }
        }


    }

    private void clearFields() {
        txtEmpId.setText("");
        txtName.setText("");
        txtNic.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        txtDob.setValue(null);
        txtStatus.setText("");
        txtNationality.setText("");
        txtReligion.setText("");
        txtMobile.setText("");
        txtSalary.setText("");
        txtSclAtt.setText("");
        txtEduQul.setText("");
        txtWorkExp.setText("");
    }

    private boolean checkValidity() {
        String id = txtEmpId.getText();
        String nic = txtNic.getText();
        String age = txtAge.getText();
        String mobile = txtMobile.getText();
        String salary = txtSalary.getText();

        boolean isValid = true;

        if (!RegexPatterns.getEmpIdPattern().matcher(id).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getNewIDPattern().matcher(nic).matches() || RegexPatterns.getOldIDPattern().matcher(nic).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC !!!").show();
            isValid = false;

        } else if (!RegexPatterns.getIntPattern().matcher(age).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Age !!!").show();
            isValid = false;

        } else if (!RegexPatterns.getMobilePattern().matcher(mobile).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Mobile !!!").show();
            isValid = false;

        } else if (!RegexPatterns.getDoublePattern().matcher(salary).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary !!!").show();
            isValid = false;

        }
        return isValid;

    }

    private Boolean checkEmpty(Employee employee) {

        return false;

    }


    @FXML
    void cmbDesignationOnAction(ActionEvent event) {
        txtSalary.requestFocus();
    }

    @FXML
    void cmbGenderOnAction(ActionEvent event) {
        txtStatus.requestFocus();
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtAge.requestFocus();
    }

    @FXML
    void txtAgeOnAction(ActionEvent event) {
        txtDob.requestFocus();
    }

    @FXML
    void txtDobOnAction(ActionEvent event) {
        cmbGender.requestFocus();
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtMobileOnAction(ActionEvent event) {
        cmbDesignation.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtNic.requestFocus();
    }

    @FXML
    void txtNationalityOnAction(ActionEvent event) {
        txtReligion.requestFocus();
    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtReligionOnAction(ActionEvent event) {
        txtMobile.requestFocus();
    }

    @FXML
    void txtSalaryOnAction(ActionEvent event) {
        txtSclAtt.requestFocus();
    }

    @FXML
    void txtStatusOnAction(ActionEvent event) {
        txtNationality.requestFocus();
    }
}
