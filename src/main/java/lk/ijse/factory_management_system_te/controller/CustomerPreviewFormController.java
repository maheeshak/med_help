package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerPreviewFormController implements Initializable {

    @FXML
    private TextField txtPerCredit;

    @FXML
    private TextField txtBank;

    @FXML
    private TextField txtAccNumber;

    @FXML
    private TextField txtAreaBuss;

    @FXML
    private TextField txtNoYrs;

    @FXML
    private TextField txtAmountCredit;

    @FXML
    private TextField txtNatOfCus;

    @FXML
    private TextField txtVat;

    @FXML
    private TextField txtPrimaryConPerson;

    @FXML
    private TextField txtTransport;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtOfficeAddress;

    @FXML
    private TextField txtDelAddress;

    @FXML
    private TextField txtName;

    @FXML
    private Label txtMsg;

    static Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCusId.setText(customer.getCus_id());
        txtName.setText(customer.getName());
        txtOfficeAddress.setText(customer.getAddress());
        txtDelAddress.setText(customer.getDelivery_address());
        txtContact.setText(customer.getContact());
        txtEmail.setText(customer.getEmail());
        txtNatOfCus.setText(customer.getCus_nature());
        txtVat.setText(customer.getVat());
        txtAreaBuss.setText(customer.getArea_business());
        txtNoYrs.setText(customer.getNo_yrs_business());
        txtAmountCredit.setText(customer.getCredit_amount());
        txtPerCredit.setText(customer.getPer_credit());
        txtAccNumber.setText(customer.getAcc_number());
        txtTransport.setText(customer.getMod_of_trans());
        txtBank.setText(customer.getBank());

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String cusId = txtCusId.getText();
        String name = txtName.getText();
        String address = txtOfficeAddress.getText();
        String transport = txtTransport.getText();
        String delAddress = txtDelAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String nat_cus = txtNatOfCus.getText();
        String vat = txtVat.getText();
        String areaBuss = txtAreaBuss.getText();
        String noYrs = txtNoYrs.getText();
        String creditAmount = txtAmountCredit.getText();
        String perCredit = txtPerCredit.getText();
        String accNumber = txtAccNumber.getText();
        String bank = txtBank.getText();

        boolean isEmpty = checkEmpty(cusId, name, address, transport, delAddress, contact, email, nat_cus,
                vat, areaBuss, noYrs, creditAmount, perCredit, accNumber);
        if (!isEmpty) {
            try {
                boolean isAdded = CustomerModel.update(new Customer("U001", cusId, name, address, transport, delAddress, contact, email, nat_cus,
                        vat, areaBuss, noYrs, creditAmount, perCredit, accNumber, String.valueOf(LocalDate.now()),bank));
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated !!!!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Updated !!!!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean checkEmpty(String ...args) {
        boolean isEmpty=false;
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("")){
                isEmpty=true;
                txtMsg.setText("* Please Fill All Fields");
                break;
            }else {
                txtMsg.setText("");
            }
        }
        return isEmpty;
    }
}
