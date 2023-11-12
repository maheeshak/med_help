package lk.ijse.factory_management_system_te.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.RawCart;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.dto.tm.AddItemTM;
import lk.ijse.factory_management_system_te.model.AddItemModel;
import lk.ijse.factory_management_system_te.model.ItemModel;
import lk.ijse.factory_management_system_te.model.RawMaterialModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddItemWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtItemNAme;

    @FXML
    private JFXComboBox<String> cmbItemType;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TableView<AddItemTM> tblRawDetails;

    @FXML
    private TableColumn<?, ?> colRawID;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXComboBox<String> cmbRawId;

    @FXML
    private Label lblDescription;

    @FXML
    private TextField txtRawQty;

    @FXML
    private Label lblCurrentItemId;

    private ObservableList<AddItemTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemType();
        setRawId();
        setCellValueFactory();
        setCurrentItemId();
    }

    private void setCurrentItemId() {
        try {
            String item_id=ItemModel.getCurrentItemId();
            lblCurrentItemId.setText(item_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRawId() {

        try {
            List<String> rawIds = RawMaterialModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : rawIds) {
                obList.add(id);
            }
            cmbRawId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setItemType() {
        String[] itemType = {null, "Hospital Furniture", "Rehabilitation Aids", "Medical Equipment", "Medical Consumables"};
        cmbItemType.getItems().addAll(itemType);
    }

    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        boolean isValid = checkQty();
        if (isValid) {
            String rawId = cmbRawId.getValue();
            String description = lblDescription.getText();
            int qty = Integer.parseInt(txtRawQty.getText());

            Button btnRemove = new Button();
            Image deleteIcon = new Image(getClass().getResourceAsStream("/img/remove_logo.png"));
            ImageView deleteView = new ImageView(deleteIcon);
            deleteView.setFitHeight(12);
            deleteView.setFitWidth(12);
            deleteView.setPreserveRatio(true);
            btnRemove.setGraphic(deleteView);
            btnRemove.setCursor(Cursor.HAND);
            btnRemove.setStyle("-fx-background-color:  #ff6666; ");

            setRemoveBtnOnAction(btnRemove);  /*set action to the btnRemove */

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblRawDetails.getItems().size(); i++) {
                    if (colRawID.getCellData(i).equals(rawId)) {
                        qty += (int) colQty.getCellData(i);
                        obList.get(i).setQty(qty);
                        tblRawDetails.refresh();
                        return;
                    }
                }
            }

            AddItemTM tm = new AddItemTM(rawId, description, qty, btnRemove);

            obList.add(tm);
            tblRawDetails.setItems(obList);

            txtRawQty.setText("");
        }

    }

    private boolean checkQty() {
        String qty = txtRawQty.getText();
        boolean isValid = true;

        if (!RegexPatterns.getIntPattern().matcher(qty).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qty !!!").show();
            isValid = false;
        }
        return isValid;
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int index = tblRawDetails.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select Row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


                if (result.orElse(no) == yes) {
                    index = tblRawDetails.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblRawDetails.refresh();
                }
            }

        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if (isValid) {
            String itemId = txtID.getText();
            String itemName = txtItemNAme.getText();
            String itemType = cmbItemType.getSelectionModel().getSelectedItem();
            String qty = txtQty.getText();
            Double unitPrice = Double.valueOf(txtUnitPrice.getText());

            List<RawCart> rawCartDTOList = new ArrayList<>();

            for (int i = 0; i < tblRawDetails.getItems().size(); i++) {
                AddItemTM tm = obList.get(i);

                RawCart rawCartDTO = new RawCart(tm.getRaw_id(), tm.getDesc(), tm.getQty());
                rawCartDTOList.add(rawCartDTO);
            }
            try {
                boolean isAdded = AddItemModel.addItem(itemId, itemName, itemType, qty, unitPrice, rawCartDTOList);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Added !").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item not Added !").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtItemNAme.setText("");
        txtQty.setText("");
        txtRawQty.setText("");
        txtUnitPrice.setText("");
    }

    private boolean checkValidity() {
        String id = txtID.getText();
        String qty = txtQty.getText();
        String unit_price = txtUnitPrice.getText();

        boolean isValid = true;
        if (!RegexPatterns.getItemIdPattern().matcher(id).matches()) {
            new Alert(Alert.AlertType.ERROR,"Invalid Item ID !!!").show();
            isValid = false;

        }else if(!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty !!!").show();
            isValid = false;
        }else if(!RegexPatterns.getDoublePattern().matcher(unit_price).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid unit price!!!").show();
            isValid = false;
        }

        return isValid;
    }

    @FXML
    void cmbRawIdOnAction(ActionEvent event) {
        String rawId = cmbRawId.getSelectionModel().getSelectedItem();

        try {
            RawMaterial rawMaterial = RawMaterialModel.searchById(rawId);
            lblDescription.setText(rawMaterial.getRaw_desc());
            txtRawQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }


    }

    void setCellValueFactory() {
        colRawID.setCellValueFactory(new PropertyValueFactory<>("raw_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }


    @FXML
    void cmbItemTypeOnAction(ActionEvent event) {
        txtItemNAme.requestFocus();
    }


    @FXML
    void txtIdOnAction(ActionEvent event) {
        cmbItemType.requestFocus();
    }

    @FXML
    void txtItemNameOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {
        cmbRawId.requestFocus();
    }


}
