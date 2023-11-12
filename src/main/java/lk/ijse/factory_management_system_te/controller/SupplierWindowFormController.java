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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.dto.Supplier;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.dto.tm.SupplierTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colPreview;

    @FXML
    private TableColumn<?, ?> colRemove;


    @FXML
    private Label lblToadySupplier;

    @FXML
    private Label lblTotalSupplier;

    @FXML
    private JFXTextField txtSearch;
    @FXML
    private BarChart<String, Integer> barGraph;

    ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
    Integer[] data = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        setTodaySupplierValue();
        setTotalSupplierValue();
        setBarGraphValues();

    }

    private void setBarGraphValues() {
        try {
            data = SupplierModel.getSupplierValueMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("No. of Suppliers");
        series.getData().add(new XYChart.Data("JAN", data[0]));
        series.getData().add(new XYChart.Data("FEB", data[1]));
        series.getData().add(new XYChart.Data("MAR", data[2]));
        series.getData().add(new XYChart.Data("APR", data[3]));
        series.getData().add(new XYChart.Data("MAY", data[4]));
        series.getData().add(new XYChart.Data("JUN", data[5]));
        series.getData().add(new XYChart.Data("JUL", data[6]));
        series.getData().add(new XYChart.Data("AUG", data[7]));
        series.getData().add(new XYChart.Data("SEP", data[8]));
        series.getData().add(new XYChart.Data("OCT", data[9]));
        series.getData().add(new XYChart.Data("NOV", data[10]));
        series.getData().add(new XYChart.Data("DEC", data[11]));

        barGraph.getData().addAll(series);
    }

    private void setTotalSupplierValue() {
        try {
            String suppliers = SupplierModel.totalSupplier();
            lblTotalSupplier.setText(suppliers);

        } catch (SQLException e) {
        }

    }

    private void setTodaySupplierValue() {
        try {
            String suppliers = SupplierModel.todaySupplier();
            lblToadySupplier.setText(suppliers);
        } catch (SQLException e) {
        }
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("sup_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAll() {

        try {
            List<Supplier> supplierList = SupplierModel.getAll();


            for (Supplier supplier : supplierList) {
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

                setPreviewBtnOnAction(btnPreview);

                obList.add(new SupplierTM(supplier.getSup_id(),
                        supplier.getSup_name(),
                        supplier.getSup_address(),
                        supplier.getSup_contact(),
                        supplier.getSup_email(), btnPreview, btnRemove));

            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnNewOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add_supplier_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Supplier Register Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obList.clear();
                getAll();
                setTotalSupplierValue();
                setTodaySupplierValue();
                barGraph.getData().clear();
                setBarGraphValues();
            }
        });
        stage.show();


    }

    private void setRemoveBtnOnAction(Button btnRemove) {

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<SupplierTM> items = tblSupplier.getItems();
                    SupplierTM supplierTM = items.get(selectedIndex);
                    String sup_id = supplierTM.getSup_id();

                    boolean isRemove = false;
                    try {
                        isRemove = SupplierModel.remove(sup_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obList.clear();
                            getAll();
                            setTotalSupplierValue();
                            setTodaySupplierValue();
                            barGraph.getData().clear();
                            setBarGraphValues();
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
            int selectedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<SupplierTM> items = tblSupplier.getItems();
                SupplierTM supplierTM = items.get(selectedIndex);
                String sup_id = supplierTM.getSup_id();

                try {
                    Supplier supplier = SupplierModel.find(sup_id);

                    SupplierPreviewWindowFormController.supplier = supplier;

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_preview_window_form.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Supplier Form");
                        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
                        stage.getIcons().add(icon);
                        stage.setResizable(false);
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent e) {
                                obList.clear();
                                getAll();
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

    private void searchFilter() {
        FilteredList<SupplierTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(SupplierTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (SupplierTM.getSup_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SupplierTM.getSup_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (SupplierTM.getSup_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SupplierTM.getSup_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SupplierTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (SupplierTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<SupplierTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblSupplier.comparatorProperty());
        tblSupplier.setItems(sortedData);
    }


    @FXML
    void txtSearchOnKeyPressed(KeyEvent event) {
        searchFilter();
    }
}
