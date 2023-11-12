package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.model.RawMaterialModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RawMaterialPreviewWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblRawId;


    @FXML
    private Label lblDesc;

    @FXML
    private Label lblQty;

    @FXML
    private TextField txtUnitPrice;

    public static RawMaterial rawMaterial;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValues();

    }

    private void setValues() {
        lblRawId.setText(rawMaterial.getRaw_id());
        lblDesc.setText(rawMaterial.getRaw_desc());
        lblQty.setText(rawMaterial.getQty());
        txtUnitPrice.setText(String.valueOf(rawMaterial.getUnit_price()));


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if (isValid) {

            String raw_id = lblRawId.getText();
            String unit_price = txtUnitPrice.getText();
            try {

                boolean isUpdated = RawMaterialModel.UpdateQty(raw_id, unit_price);

                if (isUpdated) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Raw material Unit Price is Updated !!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Raw material Unit Price is not Updated !!!").show();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean checkValidity() {
        String unit_price = txtUnitPrice.getText();
        boolean isValid =true;

        if(!RegexPatterns.getDoublePattern().matcher(unit_price).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Unit Price !!!").show();
            isValid = false;
        }
        return isValid;
    }
}
