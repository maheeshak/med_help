package lk.ijse.factory_management_system_te.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.dto.Item;
import lk.ijse.factory_management_system_te.dto.RawMaterial;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.dto.tm.ItemTM;
import lk.ijse.factory_management_system_te.dto.tm.RawMaterialTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.model.ItemModel;
import lk.ijse.factory_management_system_te.model.RawMaterialModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StockWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemType;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colPreview;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableView<RawMaterialTM> tblRawMaterial;

    @FXML
    private TableColumn<?, ?> colRawId;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colRawUnitPrice;

    @FXML
    private TableColumn<?, ?> colRawQty;

    @FXML
    private TableColumn<?, ?> colRawPreview;

    @FXML
    private TableColumn<?, ?> colRawRemove;

    @FXML
    private Label lblTotalRaw;

    @FXML
    private Label lblTotalItems;

    @FXML
    private JFXTextField txtItemSearch;
    @FXML
    private JFXTextField txtRawSearch;

    ObservableList<ItemTM> obItemList = FXCollections.observableArrayList();
    ObservableList<RawMaterialTM> obRawList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        getRawAll();
        setRawCellValueFactory();
        setItemValues();
        setRawValues();
    }

    private void setRawValues() {
        try {
            String value = RawMaterialModel.count();
            lblTotalRaw.setText(value);
        } catch (SQLException e) {
        }
    }

    private void setItemValues() {
        try {
            String value = ItemModel.count();
            lblTotalItems.setText(value);
        } catch (SQLException e) {
        }
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("item_type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAll() {


        List<Item> items = null;
        try {
            items = ItemModel.getAll();

            for (Item item : items) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/img/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(12);
                deleteView.setFitWidth(12);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setRemoveBtnOnAction(btnRemove); /*set button remove action*/


                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/img/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(12);
                previewView.setFitWidth(12);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                setPreviewBtnOnAction(btnPreview); //*set button preview action*/


                obItemList.add(new ItemTM(item.getItem_id(),
                        item.getItem_name(),
                        item.getItem_type(),
                        item.getQty(),
                        item.getUnit_price(), btnPreview, btnRemove));
            }
            tblItem.setItems(obItemList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRawCellValueFactory() {
        colRawId.setCellValueFactory(new PropertyValueFactory<>("raw_id"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("raw_name"));
        colRawUnitPrice.setCellValueFactory(new PropertyValueFactory<>("raw_price"));
        colRawQty.setCellValueFactory(new PropertyValueFactory<>("raw_qty"));
        colRawPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRawRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));

    }

    private void getRawAll() {


        List<RawMaterial> rawMaterials = null;
        try {
            rawMaterials = RawMaterialModel.getAll();

            for (RawMaterial rawMaterial : rawMaterials) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/img/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(12);
                deleteView.setFitWidth(12);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setRawRemoveBtnOnAction(btnRemove); //*set button remove action*/


                Button btnPreview = new Button();
                Image previewIcon = new Image(getClass().getResourceAsStream("/img/eye.png"));
                ImageView previewView = new ImageView(previewIcon);
                previewView.setFitHeight(12);
                previewView.setFitWidth(12);
                previewView.setPreserveRatio(true);
                btnPreview.setGraphic(previewView);
                btnPreview.setCursor(Cursor.HAND);
                btnPreview.setStyle("-fx-background-color:#87CEEB; ");

                setRawPreviewBtnOnAction(btnPreview); //*set button preview action*/


                obRawList.add(new RawMaterialTM(rawMaterial.getRaw_id(),
                        rawMaterial.getRaw_desc(),
                        rawMaterial.getUnit_price(),
                        rawMaterial.getQty(), btnPreview, btnRemove));
            }
            tblRawMaterial.setItems(obRawList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setRawRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblRawMaterial.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<RawMaterialTM> rawMaterials = tblRawMaterial.getItems();
                    RawMaterialTM rawMaterialTM = rawMaterials.get(selectedIndex);
                    String raw_id = rawMaterialTM.getRaw_id();


                    try {
                        boolean isRemove = RawMaterialModel.remove(raw_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obRawList.clear();
                            getRawAll();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "oops something went wrong !!!").show();
                        }
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SQL Error !!!").show();
                    }


                }
            }

        });
    }

    private void setRawPreviewBtnOnAction(Button btnPreview) {

        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblRawMaterial.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<RawMaterialTM> rawMaterials = tblRawMaterial.getItems();
                RawMaterialTM rawMaterialTM = rawMaterials.get(selectedIndex);
                String raw_id = rawMaterialTM.getRaw_id();

                try {
                    RawMaterial rawMaterial = RawMaterialModel.find(raw_id);

                    RawMaterialPreviewWindowFormController.rawMaterial = rawMaterial;

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/raw_material_preview_window_form.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Item Form");
                        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
                        stage.getIcons().add(icon);
                        stage.setResizable(false);
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent e) {
                                obRawList.clear();
                                getRawAll();
                                setRawValues();

                            }
                        });
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

    @FXML
    void btnItemNewOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add_item_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("ADD Item Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obItemList.clear();
                getAll();
                setItemValues();

            }
        });
        stage.show();
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/update_item_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Item Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obItemList.clear();
                getAll();
            }
        });
        stage.show();
    }

    @FXML
    void btnMaterialNewOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add_material_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("ADD Material Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obRawList.clear();
                getRawAll();
                setItemValues();

            }
        });

        stage.show();
    }

    @FXML
    void btnUpdateMaterialOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/update_material_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Material Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obRawList.clear();
                getRawAll();
                setRawValues();

            }
        });
        stage.show();
    }

    private void setRemoveBtnOnAction(Button btnRemove) {

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblItem.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<ItemTM> items = tblItem.getItems();
                    ItemTM itemTM = items.get(selectedIndex);
                    String itemId = itemTM.getItem_id();


                    try {
                        boolean isRemove = ItemModel.remove(itemId);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obItemList.clear();
                            getAll();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "oops something went wrong !!!").show();
                        }
                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.CONFIRMATION, "SQL Error !!!").show();
                    }


                }
            }

        });


    }

    private void setPreviewBtnOnAction(Button btnPreview) {

        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblItem.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<ItemTM> items = tblItem.getItems();
                ItemTM itemTM = items.get(selectedIndex);
                String itemId = itemTM.getItem_id();

                try {
                    Item item = ItemModel.find(itemId);

                    ItemPreviewWindowController.item = item;

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/item_preview_window.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Item Form");
                        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
                        stage.getIcons().add(icon);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }


    private void searchItemFilter() {
        FilteredList<ItemTM> filteredData = new FilteredList<>(obItemList, b -> true);
        txtItemSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ItemTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (ItemTM.getItem_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ItemTM.getItem_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (ItemTM.getItem_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ItemTM.getItem_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ItemTM.getItem_type().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (ItemTM.getItem_type().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<ItemTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblItem.comparatorProperty());
        tblItem.setItems(sortedData);
    }

    private void searchRawFilter() {
        FilteredList<RawMaterialTM> filteredData = new FilteredList<>(obRawList, b -> true);
        txtRawSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(RawMaterialTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (RawMaterialTM.getRaw_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (RawMaterialTM.getRaw_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (RawMaterialTM.getRaw_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (RawMaterialTM.getRaw_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<RawMaterialTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblRawMaterial.comparatorProperty());
        tblRawMaterial.setItems(sortedData);
    }


    @FXML
    void txtItemSearchOnKeyPressed(KeyEvent event) {
        searchItemFilter();
    }

    @FXML
    void txtRawSearchOnKeyPressed(KeyEvent event) {
        searchRawFilter();

    }
}
