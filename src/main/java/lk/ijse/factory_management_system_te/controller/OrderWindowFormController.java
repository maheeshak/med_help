package lk.ijse.factory_management_system_te.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.dto.Order;
import lk.ijse.factory_management_system_te.dto.OrderDetails;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.dto.tm.OrderPreviewTM;
import lk.ijse.factory_management_system_te.dto.tm.OrderTm;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.model.OrderDetailModel;
import lk.ijse.factory_management_system_te.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private TableView<OrderTm> tblView;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colCuID;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStatus;


    @FXML
    private TableColumn<?, ?> colPreview;

    @FXML
    private TableColumn<?, ?> colRemove;
    @FXML
    private Label lblOrderCount;

    @FXML
    private Label lblTotalCount;

    @FXML
    private BarChart<String,Integer> barGraph;

    ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    Integer[] data = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBarGraphValues();
        setTotalOrdersValue();
        getAll();
        setCellValueFactory();
        setTotalCount();

    }

    private void setTotalCount() {
        try {
            String count = OrderModel.getCount();
            lblTotalCount.setText(count);
        }catch (SQLException e){}
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colCuID.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAll() {


        try {
            List<Order> orderList  = OrderModel.getAll();


            for (Order order : orderList) {
                Button btnRemove = new Button();
                Image deleteIcon = new Image(getClass().getResourceAsStream("/img/remove_logo.png"));
                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitHeight(12);
                deleteView.setFitWidth(12);
                deleteView.setPreserveRatio(true);
                btnRemove.setGraphic(deleteView);
                btnRemove.setCursor(Cursor.HAND);
                btnRemove.setStyle("-fx-background-color:  #ff6666; ");

                setRemoveBtnOnAction(btnRemove); //set button remove action


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


                        obList.add(new OrderTm(order.getOrder_id(),
                                order.getCus_id(),
                                order.getCus_name(),
                                order.getOrder_date(), order.getStatus()
                                , btnPreview, btnRemove));

                    }

            tblView.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPreviewBtnOnAction(Button btnPreview) {
        btnPreview.setOnAction((e) -> {
            int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {


                ObservableList<OrderTm> items = tblView.getItems();
                OrderTm orderTm = items.get(selectedIndex);
                String order_id = orderTm.getOrder_id();
                Customer customer = null;
                try {
                    OrderDetails orderDetails= OrderModel.find(order_id);
                    OrderPreviewWindowFormController.orderDetails = orderDetails;

                    List<OrderPreviewTM> orderPreviewTM =OrderDetailModel.getOrderItems(order_id);
                    OrderPreviewWindowFormController.orderPreviewTM =orderPreviewTM;


                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/order_preview_window_form.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Distributor Form");
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

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                new Alert(Alert.AlertType.ERROR, "Please select row first !!!").show();
            } else {
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {


                    ObservableList<OrderTm> orders = tblView.getItems();
                    OrderTm orderTm = orders.get(selectedIndex);
                    String order_id = orderTm.getOrder_id();


                    try {
                        boolean isRemove =OrderModel.remove(order_id);

                        if (isRemove) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Deleted !!!").show();
                            obList.clear();
                            getAll();
                            setTotalCount();
                            setTotalOrdersValue();
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


    private void setTotalOrdersValue() {

       Integer value=0;
        try {
            value=OrderModel.ordersValue();
            lblOrderCount.setText(String.valueOf(value));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setBarGraphValues() {
        try {
            data = OrderModel.getOrderValueMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        XYChart.Series<String,Integer> series=new XYChart.Series();
        series.setName("No. of Orders");
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
    @FXML
    void btnPlaceOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/view/place_order_window_form.fxml"));
        Scene scene =new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Place order Form");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                obList.clear();
                getAll();
                setTotalCount();
                setTotalOrdersValue();
                barGraph.getData().clear();
                setBarGraphValues();
            }
        });
        stage.show();
    }
}
