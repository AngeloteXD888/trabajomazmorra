package com.mazmorras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mazmorras/view/CreacionProtagonistaView.fxml")
            );
            
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            
            primaryStage.setTitle("Juego de Mazmorras");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la interfaz:");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicación:");
            e.printStackTrace();
        }
    }
}