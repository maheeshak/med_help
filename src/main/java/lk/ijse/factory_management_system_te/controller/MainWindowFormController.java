package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane pane;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_window_form.fxml"));
        pane.getChildren().add(anchorPane);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);

    }

    @FXML
    void btnDistributorOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);

    }
    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/order_window_form.fxml"));
            pane.getChildren().clear();
            pane.getChildren().add(anchorPane);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/supplier_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
    }
    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employee_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
    }
    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/stock_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);

    }
    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/report_window_form.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Thotawattha Engineering - Login Window");
        stage.centerOnScreen();
    }
}
