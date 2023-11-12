package lk.ijse.factory_management_system_te.controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.factory_management_system_te.model.UserModel;
import lk.ijse.factory_management_system_te.smtp.Mail;
import lk.ijse.factory_management_system_te.animation.Animation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtOtp;

    @FXML
    private ImageView imgLogo;

    @FXML
    private PasswordField txtPassword;
    @FXML
    private JFXButton btnOtp;

    @FXML
    private TextField txtEmail;

    private Integer otpMail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Animation.translateTransitionMore(imgLogo);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String otp = txtOtp.getText();

        boolean isEmpty = checkEmptyValue(userName, password, otp);
        if (!isEmpty) {
            try {
                boolean isValid = UserModel.valid(userName, password);
                if (isValid & otp.equals(String.valueOf(otpMail)) ) {

                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_window_form.fxml"));
                    Scene scene = new Scene(anchorPane);

                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Thotawattha Engineering");
                    stage.centerOnScreen();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    private boolean checkEmptyValue(String userName, String password, String otp) {
        boolean isEmpty = false;
        if (userName.equals("")) {
            txtUserName.setStyle("-fx-border-color: red ; -fx-background-radius: 30px");
            Animation.translateTransition(txtUserName);
            isEmpty = true;
        }
        if (password.equals("")) {
            txtPassword.setStyle("-fx-border-color: red ; -fx-background-radius: 30px");
            Animation.translateTransition(txtPassword);
            isEmpty = true;
        }
        if (otp.equals("")) {
            txtOtp.setStyle("-fx-border-color: red ; -fx-background-radius: 30px");
            Animation.translateTransition(txtOtp);
            isEmpty = true;
        }
        return isEmpty;

    }


    @FXML
    void txtOtpOnKeyTyped(KeyEvent event) {
        txtOtp.setStyle("-fx-border-color: linear-gradient(to bottom, #00b1cc 0%, #0c4a9e 100%) ; -fx-background-radius: 30px");
    }


    @FXML
    void txtEmailOnKeyTyped(KeyEvent event) {
        boolean valid = checkValid(txtEmail);
        if(valid){
            btnOtp.setDisable(false);
        }else {
            btnOtp.setDisable(true);
        }
    }

    @FXML
    void btnOtpOnAction(ActionEvent event) {
        Random random = new Random();
        otpMail = random.nextInt(100000);

        Mail mail = new Mail();
        mail.setMsg("Welcome to Thotawattha Engineering.\n\nFor your first login you'll need the OTP.\nYour OTP is :" + otpMail + "\n" +
                "\nTime : " +
                Time.valueOf(LocalTime.now()) + "\n" +
                "Date : " +
                Date.valueOf(LocalDate.now()));//email message
        mail.setTo(txtEmail.getText()); //receiver's mail
        mail.setSubject("Alert"); //email subject

        Thread thread = new Thread(mail);
        thread.start();
    }


    @FXML
    void txtSignUpOnMouseClicked(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/register_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Thotawattha Engineering - Register Window");
        stage.centerOnScreen();

    }

    private boolean checkValid(TextField txtEmail) {
        String emailText = txtEmail.getText();
        if (emailText.equals("")) {
            System.out.println("error");
        }
        if (!txtEmail.getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setStyle("-fx-border-color: red ; -fx-background-radius: 30px");
            return false;
        } else {
            txtEmail.setStyle("-fx-border-color: linear-gradient(to bottom, #00b1cc 0%, #0c4a9e 100%) ; -fx-background-radius: 30px");
        return true;
        }
    }
}
