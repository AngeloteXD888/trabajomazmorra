package com.mazmorras.controller;

import com.mazmorras.model.Escenario;
import com.mazmorras.model.Protagonista;
import com.mazmorras.model.Celda;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class JuegoControlador {
    private Escenario modelo;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Text txtEstadisticas;

    public void setModelo(Escenario escenario) {
        this.modelo = escenario;
        inicializarVista();
    }

    private void inicializarVista() {
        if (modelo == null) {
            throw new IllegalStateException("Modelo no inicializado");
        }

        // Asumiendo que getMapa() devuelve Celda[][]
        Celda[][] mapa = (Celda[][]) modelo.getMapa(); 
        gridTablero.getChildren().clear();

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                Text celdaText = new Text();
                
                if (mapa[i][j].esPared()) {
                    celdaText.setText("#");
                    celdaText.setFill(Color.DARKGRAY);
                } else {
                    celdaText.setText(".");
                    celdaText.setFill(Color.LIGHTGRAY);
                    
                    if (mapa[i][j].getPersonaje() != null) {
                        celdaText.setText(mapa[i][j].getPersonaje() instanceof Protagonista ? "P" : "E");
                        celdaText.setFill(mapa[i][j].getPersonaje() instanceof Protagonista ? Color.BLUE : Color.RED);
                    }
                }
                
                gridTablero.add(celdaText, j, i);
            }
        }
    }
}