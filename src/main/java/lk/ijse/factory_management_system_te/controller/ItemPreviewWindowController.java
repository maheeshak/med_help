package lk.ijse.factory_management_system_te.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.dto.tm.AddItemTM;
import lk.ijse.factory_management_system_te.dto.tm.ItemTM;
import lk.ijse.factory_management_system_te.model.RawMaterialItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemPreviewWindowController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtItemNAme;

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
    private TextField txtItemType;

    public static Item item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValue();
        setTableValue();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colRawID.setCellValueFactory(new PropertyValueFactory<>("raw_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void setTableValue() {
        ObservableList<AddItemTM> obList = FXCollections.observableArrayList();

        String itemId = item.getItem_id();

        List<AddItemTM> itemTM = null;
        try {
            itemTM = RawMaterialItemModel.serachById(itemId);


        for (AddItemTM items : itemTM){
            obList.add(items);

        }
        tblRawDetails.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setValue() {
        txtID.setText(item.getItem_id());
        txtItemNAme.setText(item.getItem_name());
        txtQty.setText(item.getQty());
        txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
        txtItemType.setText(item.getItem_type());
    }
}
