package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) {

        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/supplier_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/customer_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/employee_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/item_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRawOnAction(ActionEvent event) {
        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/raw_material_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        try {
            try {
                JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/order_summary_report.jrxml");


                JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
                JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
