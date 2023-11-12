package lk.ijse.factory_management_system_te.controller;


import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lk.ijse.factory_management_system_te.animation.Animation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingWindowFormController implements Initializable {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView imgLogo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Animation.scalaTransaction(imgLogo);

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i <= 100; i++) {
                        updateProgress(i, 50);
                        Thread.sleep(50);
                    }
                    return null;
                }
            };

            progressBar.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> {
                try {
                    Parent loginParent = FXMLLoader.load(getClass().getResource("/view/login_window_form.fxml"));
                    Scene loginScene = new Scene(loginParent);
                    Stage loginStage = new Stage();
                    loginStage.setTitle("Thotawattha Engineering - Login");
                    loginStage.setScene(loginScene);
                    Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
                    loginStage.getIcons().add(icon);
                    loginStage.show();

                    // Close the welcome stage
                    ((Stage) progressBar.getScene().getWindow()).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            new Thread(task).start();

    }
}
