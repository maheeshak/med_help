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
import lk.ijse.factory_management_system_te.dto.Employee;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.dto.tm.EmployeeTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.model.EmployeeModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

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
    private Label lblEmployeeCount;

    @FXML
    private JFXTextField txtSearch;

    ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        setEmployeeValue();
    }

    private void setEmployeeValue() {
        try {
            String value = EmployeeModel.value();
            lblEmployeeCount.setText(value);
        } catch (SQLException e) {
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("emp_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("emp_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAll() {


        try {
            List<Employee> employeeList = EmployeeModel.getAll();


            for (Employee employee : employeeList) {
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
                obList.add(new EmployeeTM(employee.getEmp_id(),
                        employee.getEmp_name(),
                        employee.getEmp_address(),
                        employee.getEmp_contact(),
                        employee.getDesignation(), btnPreview, btnRemove));

            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblEmployee.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<EmployeeTM> items = tblEmployee.getItems();
                EmployeeTM employeeTM = items.get(selectedIndex);
                String emp_id = employeeTM.getEmp_id();

                try {
                    Employee employee = EmployeeModel.find(emp_id);

                    EmployeePreviewWindowController.employee = employee;

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_preview_window.fxml"));

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
            int selectedIndex = tblEmployee.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<EmployeeTM> items = tblEmployee.getItems();
                    EmployeeTM employeeTM = items.get(selectedIndex);
                    String emp_id = employeeTM.getEmp_id();


                    try {
                        boolean isRemove = EmployeeModel.remove(emp_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obList.clear();
                            getAll();
                            setEmployeeValue();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add_employee_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Employee Registration Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obList.clear();
                getAll();
                setEmployeeValue();
            }
        });

        stage.show();

    }

    private void searchFilter() {
        FilteredList<EmployeeTM> filteredData = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(EmployeeTM -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (EmployeeTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (EmployeeTM.getEmp_id().toLowerCase().contains(searchKeyword)) {
                    return true;

                } else if (EmployeeTM.getEmp_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (EmployeeTM.getEmp_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (EmployeeTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (EmployeeTM.getContact().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<EmployeeTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEmployee.comparatorProperty());
        tblEmployee.setItems(sortedData);
    }

    @FXML
    void txtSearchKeyPressedOnAction(KeyEvent event) {
        searchFilter();
    }
}
