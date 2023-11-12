package lk.ijse.factory_management_system_te.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.factory_management_system_te.animation.Animation;
import lk.ijse.factory_management_system_te.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterWindowFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtUserName;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label txtSignIn;

    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Animation.translateTransitionMore(imgLogo);
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserID.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        boolean isEmpty = checkEmpty(userId, userName, password);
        if(!isEmpty){

            boolean isSave= false;
            try {
                isSave = UserModel.save(userId,userName,password);
                if(isSave){
                    new Alert(Alert.AlertType.CONFIRMATION,"Registered !!!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Not Registered !!!").show();
                }
            } catch (SQLException e) {

               new Alert(Alert.AlertType.ERROR,"Username already exists !!!").show();
            }


        }


    }

    @FXML
    void txtSigInOnMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Thotawattha Engineering - Login Window");
        stage.centerOnScreen();
    }


    private boolean checkEmpty(String... args) {
        boolean isEmpty = false;
        if (args[0].equals("")) {
            txtUserID.setStyle("-fx-border-color: red ; -fx-border-radius: 30px");
            Animation.translateTransition(txtUserID);
            isEmpty = true;
        }else {
            txtUserID.setStyle("-fx-border-color: linear-gradient(to bottom, #00b1cc 0%, #0c4a9e 100%)");
        }
        if (args[1].equals("")) {
            txtUserName.setStyle("-fx-border-color: red ; -fx-border-radius: 30px");
            Animation.translateTransition(txtUserName);
            isEmpty = true;
        }else {
            txtUserName.setStyle("-fx-border-color: linear-gradient(to bottom, #00b1cc 0%, #0c4a9e 100%)");
        }
        if (args[2].equals("")) {
            txtPassword.setStyle("-fx-border-color: red ; -fx-border-radius: 30px");
            Animation.translateTransition(txtPassword);
            isEmpty = true;
        }else {
            txtPassword.setStyle("-fx-border-color: linear-gradient(to bottom, #00b1cc 0%, #0c4a9e 100%)");
        }
        return isEmpty;
    }


    @FXML
    void txtOtpOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtPasswordOnKeyTyped(KeyEvent event) {

    }

    @FXML
    void txtUserNameOnKeyTyped(KeyEvent event) {

    }
}
