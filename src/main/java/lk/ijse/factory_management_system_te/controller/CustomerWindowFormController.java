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
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<?, ?> colCusID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPreview;

    @FXML
    private TableColumn<?, ?> colRemove;
    @FXML
    private BarChart<String, Integer> barGrapch;
    @FXML
    private Label txtTotalCustomers;
    @FXML
    private Label lblMonthCus;

    Integer[] data = null;
    @FXML
    private JFXTextField txtSearch;
    ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        setBarGraphValues();
        setTotalCustomers();
        setTotalCustomersToday();

    }

    private void setTotalCustomersToday() {
        try {
            int count = CustomerModel.getValueMonth();

            lblMonthCus.setText(String.valueOf(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setTotalCustomers() {


        try {
            Integer value = CustomerModel.getCustomerValue();
            txtTotalCustomers.setText(String.valueOf(value));
        } catch (SQLException e) {
        }

    }

    private void setBarGraphValues() {


        try {
            data = CustomerModel.getCustomerValueMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("No. of Customers");
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

        barGrapch.getData().addAll(series);
    }

    private void setCellValueFactory() {
        colCusID.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAll() {


        try {
            List<Customer> customerList = CustomerModel.getAll();


            for (Customer customer : customerList) {
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
                obList.add(new CustomerTM(customer.getCus_id(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact(),
                        customer.getEmail(), btnPreview, btnRemove));

            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPreviewBtnOnAction(Button btnPreview) {

        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblCustomer.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<CustomerTM> items = tblCustomer.getItems();
                CustomerTM customerTM = items.get(selectedIndex);
                String cus_id = customerTM.getCus_id();
                Customer customer = null;
                try {
                    customer = CustomerModel.find(cus_id);

                    CustomerPreviewFormController.customer = customer;

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_Preview_Form.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Distributor Form");
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

    private void setRemoveBtnOnAction(Button btnRemove) {

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblCustomer.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<CustomerTM> items = tblCustomer.getItems();
                    CustomerTM customerTM = items.get(selectedIndex);
                    String cus_id = customerTM.getCus_id();

                    boolean isRemove = false;
                    try {
                        isRemove = CustomerModel.remove(cus_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obList.clear();
                            getAll();
                            setTotalCustomers();
                            setTotalCustomersToday();
                            barGrapch.getData().clear();
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

    @FXML
    void btnNewOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add_customer_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Distributor Register Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obList.clear();
                getAll();
                setTotalCustomers();
                setTotalCustomersToday();
                barGrapch.getData().clear();
                setBarGraphValues();
            }
        });
        stage.show();
    }

    private void searchFilter() {
        FilteredList<CustomerTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CustomerTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (CustomerTM.getCus_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (CustomerTM.getCus_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (CustomerTM.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (CustomerTM.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (CustomerTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (CustomerTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<CustomerTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedData);
    }

    @FXML
    void txtSearchOnKeyPressed(KeyEvent event) {
        searchFilter();
    }

}
