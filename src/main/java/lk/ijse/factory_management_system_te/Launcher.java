package lk.ijse.factory_management_system_te;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/view/loading_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Thotawattha Engineering - Welcome");

        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }
}
