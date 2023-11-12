package lk.ijse.factory_management_system_te.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.factory_management_system_te.dto.tm.OrderSummaryTM;
import lk.ijse.factory_management_system_te.model.OrderModel;
import lk.ijse.factory_management_system_te.model.SupplierRawMaterialModel;
import lk.ijse.factory_management_system_te.timeDate.TimeDate;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private PieChart pieChart1;

    @FXML
    private PieChart pieChart2;

    @FXML
    private PieChart pieChart3;

    @FXML
    private Label lblSale;

    @FXML
    private Label lblExpences;
    @FXML
    private Label lblIncome;

    @FXML
    private TableView<OrderSummaryTM> tblOrders;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblGreetings;

    @FXML
    private ImageView wishImageView;


    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTimeDate();
        setValuePieChart();
        setTotalSaleValue();
        setExpensesValue();
        setIncomeValue();
        getAll();
        setCellValueFactory();

    }

    private void setTimeDate() {
        TimeDate.localDateAndTime(lblDate, lblTime);
        TimeDate.setGreeting(lblGreetings,wishImageView);
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void getAll() {
        ObservableList<OrderSummaryTM> obList = FXCollections.observableArrayList();

        try {
            List<OrderSummaryTM> summary = OrderModel.getAllSummary();

            for (OrderSummaryTM order : summary) {
                obList.add(new OrderSummaryTM(order.getOrder_id(), order.getAmount(),
                        order.getDate(), order.getStatus()));
            }
            tblOrders.setItems(obList);
        } catch (SQLException e) {
        }

    }

    private void setTotalSaleValue() {

        try {
            String sale = OrderModel.sale();
            lblSale.setText(sale);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setIncomeValue() {
        try {
            double sales = Double.parseDouble(OrderModel.sale());
            double expenses = Double.parseDouble(SupplierRawMaterialModel.expenses());

            String income = String.valueOf(sales - expenses);
            lblIncome.setText(income);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setExpensesValue() {
        String expenses = null;
        try {
            expenses = SupplierRawMaterialModel.expenses();
            lblExpences.setText(expenses);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setValuePieChart() throws SQLException {
        double sales = Double.parseDouble(OrderModel.sale());
        double expenses = Double.parseDouble(SupplierRawMaterialModel.expenses());
        double income = sales - expenses;

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("S", sales),
                        new PieChart.Data("E", expenses),
                        new PieChart.Data("I", income));
        ObservableList<PieChart.Data> pieChartData2 =
                FXCollections.observableArrayList(
                        new PieChart.Data("E", expenses),
                        new PieChart.Data("S", sales),
                        new PieChart.Data("I", income));
        ObservableList<PieChart.Data> pieChartData3 =
                FXCollections.observableArrayList(
                        new PieChart.Data("S", sales),
                        new PieChart.Data("E", expenses),
                        new PieChart.Data("I", income));

        pieChart1.getData().addAll(pieChartData);
        pieChart2.getData().addAll(pieChartData2);
        pieChart3.getData().addAll(pieChartData3);
    }
}
