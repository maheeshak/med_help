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
import lk.ijse.factory_management_system_te.model.ItemModel;
import lk.ijse.factory_management_system_te.model.RawMaterialModel;
import lk.ijse.factory_management_system_te.model.SupplierModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddMaterialWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtRawId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label txtTotalPrice;

    @FXML
    private Label lblCurrentItemId;

    @FXML
    private Label lblCurrentRawId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCurrentRawId();
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

    private void setCurrentRawId() {
        try {
            String raw_id= RawMaterialModel.getCurrentRawId();
            lblCurrentRawId.setText(raw_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveONAction(ActionEvent event) {
        boolean isValid= CheckValidity();
        if (isValid) {
            String raw_id = txtRawId.getText();
            String desc = txtDescription.getText();
            String qty = txtQty.getText();
            Double unit_price = Double.valueOf(txtUnitPrice.getText());

            String supId = cmbSupplierId.getSelectionModel().getSelectedItem();
            String total = txtTotalPrice.getText();
            try {
                boolean isAdded = RawMaterialModel.add(new RawMaterial(raw_id, desc, unit_price, qty), supId, total, LocalDate.now());

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Raw Material Added !!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Raw Material not Added !!!").show();
                }
            } catch (SQLException e) {
            }
        }
    }

    private boolean CheckValidity() {
        String rawId = txtRawId.getText();
        String qty = txtQty.getText();
        String unit_price = txtUnitPrice.getText();

        boolean isValid = true;

        if (!RegexPatterns.getRawIdPattern().matcher(rawId).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Raw ID !!!").show();
            isValid=false;
        }else if(!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty !!!").show();
            isValid=false;
        }else if(!RegexPatterns.getDoublePattern().matcher(unit_price).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Unit Price !!!").show();
            isValid=false;
        }
        return isValid;

    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        String supId = cmbSupplierId.getSelectionModel().getSelectedItem();
        try {
            Supplier supplier = SupplierModel.searchById(supId);
            lblSupplierName.setText(supplier.getSup_name());
            txtDescription.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtUnitPriceOnKeyTyped(KeyEvent event) {
        Double qty = Double.valueOf(txtQty.getText());
        Double unit_price = Double.valueOf(txtUnitPrice.getText());
        Double total = qty * unit_price;
        txtTotalPrice.setText(String.valueOf(total));
    }


    @FXML
    void txtDescOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtRawIdOnAction(ActionEvent event) {
        cmbSupplierId.requestFocus();
    }


}
