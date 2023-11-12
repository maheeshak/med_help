package lk.ijse.factory_management_system_te.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.dto.Supplier;
import lk.ijse.factory_management_system_te.model.RawMaterialModel;
import lk.ijse.factory_management_system_te.model.SupplierModel;
import lk.ijse.factory_management_system_te.model.SupplierRawMaterialModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateMaterialWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label txtTotalPrice;

    @FXML
    private JFXComboBox<String> cmbRawId;

    @FXML
    private Label lblQty;

    @FXML
    private TextField txtNewQty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRawIds();
        setSupplierIds();

    }

    private void setSupplierIds() {
        try {
            List<String> supplierIds = SupplierModel.getIds();
            cmbSupplierId.getItems().addAll(supplierIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setRawIds() {
        try {
            List<String> rawIds = RawMaterialModel.getIds();
            cmbRawId.getItems().addAll(rawIds);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        try {
            String supplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
            Supplier supplier = SupplierModel.searchById(supplierId);
            lblSupplierName.setText(supplier.getSup_name());
            txtNewQty.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbRawIdOnAction(ActionEvent event) {

        try {
            String rawId = cmbRawId.getSelectionModel().getSelectedItem();
            RawMaterial rawMaterial = RawMaterialModel.searchById(rawId);

            lblDesc.setText(rawMaterial.getRaw_desc());
            lblQty.setText(rawMaterial.getQty());
            lblUnitPrice.setText(String.valueOf(rawMaterial.getUnit_price()));

            cmbSupplierId.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtnewQtyKeyTypedOnAction(KeyEvent event) {
        if (txtNewQty.getText().equals("")) {
            txtTotalPrice.setText("0.00");
        }


        try {
            Double unitPrice = Double.valueOf(lblUnitPrice.getText());
            Double newQty = Double.valueOf(txtNewQty.getText());
            Double total = unitPrice * newQty;
            txtTotalPrice.setText(String.valueOf(total));

        } catch (NumberFormatException e) {
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if(isValid) {
            String rawId = cmbRawId.getSelectionModel().getSelectedItem();
            String supId = cmbSupplierId.getSelectionModel().getSelectedItem();
            String newQty = txtNewQty.getText();
            String total = txtTotalPrice.getText();
            try {

                boolean isUpdated = SupplierRawMaterialModel.update(rawId, supId, newQty, total);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Raw Material is Updated !!!").show();
                    txtNewQty.setText("");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Raw Material is not Updated !!!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkValidity() {
        String qty = txtNewQty.getText();
        boolean isValid = true;

        if(!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty!!!").show();
            isValid = false;
        }
        return isValid;
    }
}
