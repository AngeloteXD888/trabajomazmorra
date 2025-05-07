package com.mazmorras;

import java.io.IOException;

import com.mazmorras.controller.JuegoControlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
     public static void main(String[] args) throws IOException {
        // Inicializar el juego o controlador principal
        JuegoControlador juego = new JuegoControlador();
        juego.iniciarJuego();
    }

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

}