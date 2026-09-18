package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/Login.fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Load all CSS files
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());

            scene.getStylesheets().add(
                    getClass().getResource("/css/login.css").toExternalForm());

            scene.getStylesheets().add(
                    getClass().getResource("/css/dashboard.css").toExternalForm());

            scene.getStylesheets().add(
                    getClass().getResource("/css/table.css").toExternalForm());

            stage.setTitle("Car Rental Management System");

            // Window Size
            stage.setWidth(1200);
            stage.setHeight(750);

            // Minimum Size
            stage.setMinWidth(1000);
            stage.setMinHeight(650);

            // Center Window
            stage.centerOnScreen();

            // Disable resizing if desired
            // stage.setResizable(false);

            // Window Icon
            try {
                stage.getIcons().add(
                        new Image(getClass().getResourceAsStream("/images/logo.png"))
                );
            } catch (Exception e) {
                System.out.println("Logo not found.");
            }

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            System.out.println("Application failed to start.");
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}