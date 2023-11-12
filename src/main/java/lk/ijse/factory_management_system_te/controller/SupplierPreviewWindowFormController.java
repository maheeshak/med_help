package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.factory_management_system_te.dto.Supplier;
import lk.ijse.factory_management_system_te.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierPreviewWindowFormController implements Initializable {
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

    public static Supplier supplier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValueSupplier();
    }

    private void setValueSupplier() {
        txtId.setText(supplier.getSup_id());
        txtName.setText(supplier.getSup_name());
        txtNic.setText(supplier.getSup_nic());
        txtAddress.setText(supplier.getSup_address());
        txtContact.setText(supplier.getSup_contact());
        txtEmail.setText(supplier.getSup_email());


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();



        try {
           boolean isUpdated = SupplierModel.update(new Supplier(id,name,nic,address,contact,email));

           if(isUpdated){
               new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated !!!").show();
           }else {
               new Alert(Alert.AlertType.ERROR,"Supplier not Updated !!!").show();

           }
        }catch (SQLException e){
            e.printStackTrace();
        }



    }
}
