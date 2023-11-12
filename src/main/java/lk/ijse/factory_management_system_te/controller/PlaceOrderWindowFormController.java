package lk.ijse.factory_management_system_te.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.Cart;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.dto.tm.PlaceOrderTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.model.ItemModel;
import lk.ijse.factory_management_system_te.model.OrderModel;
import lk.ijse.factory_management_system_te.model.PlaceOrderModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.awt.Color.red;

public class PlaceOrderWindowFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<PlaceOrderTM> tblOrderCart;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblNetTotal;

    @FXML
    private JFXComboBox<String> cmbDeliveryType;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOrderDate();
        setOrderId();
        setValuesCusID();
        setValuesItemID();
        setCellValueFactory();
        setValuesDeliveryTypes();
    }

    private void setValuesDeliveryTypes() {
        String[] deliveryTypes = {null, "On Store", "Delivery"};
        cmbDeliveryType.getItems().addAll(deliveryTypes);
    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setValuesItemID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.getCodes();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setValuesCusID() {
        List<String> ids = null;
        try {
            ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setOrderId() {

        try {
            String order_id = OrderModel.generateNextOrderId();
            lblOrderId.setText(order_id);
        } catch (SQLException e) {
        }

    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        boolean isValid = checkValidity();
        if (isValid) {
            String code = cmbItemCode.getValue();
            String description = lblDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double total = qty * unitPrice;

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
                for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tblOrderCart.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            PlaceOrderTM tm = new PlaceOrderTM(code, description, qty, unitPrice, total, btnRemove);

            obList.add(tm);
            tblOrderCart.setItems(obList);
            calculateNetTotal();

            txtQty.setText("");
        }

    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblOrderCart.refresh();
                calculateNetTotal();
            }

        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

            String oId = lblOrderId.getText();
            String cusId = cmbCustomerId.getValue();
            String netTotal = lblNetTotal.getText();
            String del_type = cmbDeliveryType.getValue();

            List<Cart> cartDTOList = new ArrayList<>();

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                PlaceOrderTM tm = obList.get(i);

                Cart cartDTO = new Cart(tm.getCode(), tm.getQty(), tm.getUnitPrice());
                cartDTOList.add(cartDTO);
            }

            try {

                boolean isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList, netTotal, del_type);
                if (isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }


    }

    private boolean checkValidity() {
        String qty = txtQty.getText();
        boolean isValid = true;

        if (!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid QTY !!!").show();
            isValid=false;
        }
        return isValid;
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String cus_id = cmbCustomerId.getSelectionModel().getSelectedItem();
        try {
            String customer = CustomerModel.searchById(cus_id);
            lblCustomerName.setText(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = cmbItemCode.getSelectionModel().getSelectedItem();

        try {
            Item item = ItemModel.searchById(code);
            fillItemFields(item);
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void fillItemFields(Item item) {
        lblDescription.setText(item.getItem_name());
        lblQtyOnHand.setText(item.getQty());
        lblUnitPrice.setText(String.valueOf(item.getUnit_price()));

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
}
