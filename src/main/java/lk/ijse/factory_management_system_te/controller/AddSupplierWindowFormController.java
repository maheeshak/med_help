package lk.ijse.factory_management_system_te.controller;

import com.sun.source.tree.UsesTree;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.factory_management_system_te.dto.Supplier;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.model.SupplierModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddSupplierWindowFormController implements Initializable {
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblDate;

    @FXML
    private Label cuurentSupID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        setCurrentSupID();
    }

    private void setCurrentSupID() {
        try {

            String sup_id = SupplierModel.getCurrentSupId();
            cuurentSupID.setText(sup_id);
        }catch (SQLException e){}
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();


        try {
            boolean isValid = CheckValidity();
            if (isValid) {
                boolean isAdded = SupplierModel.add(new Supplier(id, name, nic, address, contact, email));
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added !!!!").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Supplier Not Added !!!!").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID !!!!").show();

        }


    }

    private void clearFields() {
        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtId.setText("");
    }

    private boolean CheckValidity() {
        String id = txtId.getText();
        String contact = txtContact.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();


        boolean isValid = true;
        if (!RegexPatterns.getSupplierIdPattern().matcher(id).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Supplier ID !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(contact).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getNewIDPattern().matcher(nic).matches() || RegexPatterns.getOldIDPattern().matcher(nic).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC !!!").show();
            isValid = false;


        } else if (!RegexPatterns.getEmailPattern().matcher(email).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email !!!").show();
            isValid = false;
        }

        return isValid;
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {
        txtNic.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtSubIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtSupNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

}

