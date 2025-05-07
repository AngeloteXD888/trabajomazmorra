package com.mazmorras.controller;

import java.io.IOException;

import com.mazmorras.model.Mapa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameLogicController {

    @FXML
    private void handleIrASecondaryView() {
        try {
            // Cargar la segunda vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondaryView.fxml"));
            Scene secondaryScene = new Scene(loader.load());

            Window someNode = null;
            // Obtener la ventana actual y cambiarla a la nueva vista
            Stage currentStage = (Stage) someNode.getScene().getWindow(); // someNode es un nodo de tu interfaz
            currentStage.setScene(secondaryScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Mapa mapa;
    
    public void MainController() {
        try {
            mapa = new Mapa("resources/mapas/nivel1.txt");
        } catch (IOException e) {
            System.out.println("Usando mapa por defecto");
            mapa = new Mapa(15, 10);
        }
    }
}
