package lk.ijse.factory_management_system_te.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.model.ItemModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateItemWindowFormController implements Initializable {
    @FXML
    public AnchorPane root;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private JFXComboBox<String> cmbItemId;

    @FXML
    private Label lblItemType;

    @FXML
    private TextField txtNewQty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemId();
    }

    private void setItemId() {
        try {
            List<String> ids = ItemModel.getIds();
            cmbItemId.getItems().addAll(ids);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        String itemId = cmbItemId.getSelectionModel().getSelectedItem();

        try {
            Item item = ItemModel.searchById(itemId);
            lblItemType.setText(item.getItem_type());
            lblItemName.setText(item.getItem_name());
            lblQty.setText(item.getQty());
            lblUnitPrice.setText(String.valueOf(item.getUnit_price()));

            txtNewQty.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if(isValid) {
            String itemId = cmbItemId.getSelectionModel().getSelectedItem();
            String newQty = txtNewQty.getText();
            try {


                boolean isUpdated = ItemModel.updateItemQty(itemId, newQty);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Qty Updated !!!").show();
                    txtNewQty.setText("");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item Qty not Updated !!!").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Exception !!!").show();
            }
        }
    }

    private boolean checkValidity() {
        String qty = txtNewQty.getText();

        boolean isValid = true;

        if (!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty !!!").show();
            isValid=false;
        }
        return isValid;
    }
}
