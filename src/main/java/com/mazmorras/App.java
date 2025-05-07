package com.mazmorras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar FXML desde resources
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/mazmorras/view/CreacionProtagonistaView.fxml")
        );
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Juego de Mazmorras");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}