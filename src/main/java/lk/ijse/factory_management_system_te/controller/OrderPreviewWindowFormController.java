package lk.ijse.factory_management_system_te.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.OrderDetails;
import lk.ijse.factory_management_system_te.dto.tm.OrderPreviewTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPreviewWindowFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private TableView<OrderPreviewTM> tblOrderCart;

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
    private Label lblNetTotal;

    @FXML
    private Label lblCustId;

    @FXML
    private Label lblDelType;

    public static OrderDetails orderDetails;
    public static List<OrderPreviewTM> orderPreviewTM;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setOrderValues();
        getAll();
    }

    private void getAll() {
        ObservableList<OrderPreviewTM> obList = FXCollections.observableArrayList();

        for (OrderPreviewTM orderPreview : orderPreviewTM) {

            obList.add(orderPreview);


        }

        tblOrderCart.setItems(obList);


    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


    }

    private void setOrderValues() {
        lblOrderDate.setText(orderDetails.getOrder_date());
        lblOrderId.setText(orderDetails.getOrder_id());
        lblCustId.setText(orderDetails.getCus_id());
        lblCustomerName.setText(orderDetails.getCus_name());
        lblDelType.setText(orderDetails.getStatus());
        lblNetTotal.setText(String.valueOf(orderDetails.getOrder_amount()));
    }
}
