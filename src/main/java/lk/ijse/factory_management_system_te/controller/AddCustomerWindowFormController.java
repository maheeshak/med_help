package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.factory_management_system_te.dto.Customer;
import lk.ijse.factory_management_system_te.dto.tm.CustomerTM;
import lk.ijse.factory_management_system_te.model.CustomerModel;
import lk.ijse.factory_management_system_te.regexPatterns.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCustomerWindowFormController implements Initializable {
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
    private Label lblDate;
    @FXML
    private Label txtMsg;
    @FXML
    private Label lblCurrentCustId;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblDate.setText(String.valueOf(LocalDate.now()));
setCurrentCustID();
    }

    private void setCurrentCustID() {
        try {

            String custID = CustomerModel.getCurrentCustID();
            lblCurrentCustId.setText(custID);
        }catch (SQLException e){}

    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
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
        String regDate = String.valueOf(LocalDate.now());
        String bank = txtBank.getText();

        boolean isEmpty = checkEmpty(cusId, name, address, transport, delAddress, contact, email, nat_cus,
                vat, areaBuss, noYrs, creditAmount, perCredit, accNumber, regDate, bank);
        if (!isEmpty) {
            try {
                boolean isValid=checkValidity();
                if (isValid) {
                    boolean isAdded = CustomerModel.add(new Customer("U001", cusId, name, address, transport, delAddress, contact, email, nat_cus,
                            vat, areaBuss, noYrs, creditAmount, perCredit, accNumber, String.valueOf(LocalDate.now()), bank));
                    if (isAdded) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer Added !!!!").show();
                        clearTextFields();


                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer Not Added !!!!").show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean checkValidity() {
        String cust_id = txtCusId.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String primaryConPerson = txtPrimaryConPerson.getText();
        String amountCredit = txtAmountCredit.getText();


        boolean isValid = true;
        if (!RegexPatterns.getCustomerIdPattern().matcher(cust_id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer ID !!!").show();
            isValid = false;
        }else if(!RegexPatterns.getMobilePattern().matcher(contact).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Contact !!!").show();
            isValid = false;
        }else if(!RegexPatterns.getEmailPattern().matcher(email).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Email !!!").show();
            isValid = false;
        }else if(!RegexPatterns.getMobilePattern().matcher(primaryConPerson).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Primary Contact !!!").show();
            isValid = false;
        }else if(!RegexPatterns.getDoublePattern().matcher(amountCredit).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Credit Amount !!!").show();
            isValid = false;
        }

        return isValid;
    }

    private void clearTextFields() {
        txtCusId.setText("");
        txtName.setText("");
        txtOfficeAddress.setText("");
        txtDelAddress.setText("");
        txtTransport.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtNatOfCus.setText("");
        txtVat.setText("");
        txtPrimaryConPerson.setText("");
        txtAreaBuss.setText("");
        txtNoYrs.setText("");
        txtAmountCredit.setText("");
        txtPerCredit.setText("");
        txtBank.setText("");
        txtAccNumber.setText("");



    }

    private boolean checkEmpty(String... args) {
        boolean isEmpty = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("")) {
                isEmpty = true;
                txtMsg.setText("* Please Fill All Fields");
                break;
            } else {
                txtMsg.setText("");
            }
        }
        return isEmpty;
    }

    @FXML
    void txtEmailKeyTypeOnAction(KeyEvent event) {
        String emailText = txtEmail.getText();
        if (emailText.equals("")) {

        }
        if (!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setStyle("-fx-border-color: red");

        } else {
            txtEmail.setStyle(null);
        }
    }

    @FXML
    void txtMobileOnKeyType(KeyEvent event) {
        String contact = txtContact.getText();
        if (contact.equals("")) {

        }
        if (!txtContact.getText().matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
            txtContact.setStyle("-fx-border-color: red");

        } else {
            txtContact.setStyle(null);
        }
    }


    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtDelAddress.requestFocus();
    }

    @FXML
    void txtAmountOfCreditOnAction(ActionEvent event) {
        txtPerCredit.requestFocus();
    }

    @FXML
    void txtAreaOfBusinessOnAction(ActionEvent event) {
        txtNoYrs.requestFocus();
    }

    @FXML
    void txtCusIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtDeliveryAddressOnAction(ActionEvent event) {
        txtTransport.requestFocus();
    }


    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtNatOfCus.requestFocus();
    }


    @FXML
    void txtNameOfBankOnAction(ActionEvent event) {
        txtAccNumber.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtOfficeAddress.requestFocus();
    }

    @FXML
    void txtNatureOfCustomerOnAction(ActionEvent event) {
        txtVat.requestFocus();
    }

    @FXML
    void txtNoYrsBusOnAction(ActionEvent event) {
        txtAmountCredit.requestFocus();
    }

    @FXML
    void txtPeriodOfCreditOnAction(ActionEvent event) {
        txtBank.requestFocus();
    }

    @FXML
    void txtPrimaryContactOnAction(ActionEvent event) {
        txtAreaBuss.requestFocus();

    }

    @FXML
    void txtTelephoneOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtTransportOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }

    @FXML
    void txtVatOnAction(ActionEvent event) {
        txtPrimaryConPerson.requestFocus();
    }

}
