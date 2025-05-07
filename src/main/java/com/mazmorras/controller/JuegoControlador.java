package com.mazmorras.controller;

import com.mazmorras.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class JuegoControlador implements EscenarioObserver {
    private Escenario modelo;
    private GestorTurnos gestorTurnos;
    private Protagonista protagonista;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Text txtEstadisticas;

    public void setModelo(Escenario escenario) {
        this.modelo = escenario;
        this.protagonista = modelo.getProtagonista();
        this.gestorTurnos = new GestorTurnos();
        this.gestorTurnos.agregarPersonajes(modelo.getTodosPersonajes());
        
        // Registrar este controlador como observador del escenario
        // (Requiere implementar un método para agregar observadores en la clase Escenario)
        
        inicializarVista();
        configurarEventosTeclado();
        actualizarEstadisticas();
    }

    private void inicializarVista() {
        gridTablero.getChildren().clear();
        
        Celda[][] mapa = modelo.getMapa();
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                Text celdaText = crearRepresentacionCelda(mapa[y][x]);
                gridTablero.add(celdaText, x, y);
            }
        }
    }

    private Text crearRepresentacionCelda(Celda celda) {
        Text texto = new Text();
        texto.setStyle("-fx-font-family: monospace; -fx-font-size: 20px;");
        
        if (celda.esPared()) {
            texto.setText("█"); // Unicode block character
            texto.setFill(Color.DARKGRAY);
        } else {
            texto.setText("·");
            texto.setFill(Color.LIGHTGRAY);
            
            if (celda.tienePersonaje()) {
                Personaje p = celda.getPersonaje();
                if (p instanceof Protagonista) {
                    texto.setText("P");
                    texto.setFill(Color.BLUE);
                } else {
                    texto.setText("E");
                    texto.setFill(Color.RED);
                }
            }
        }
        
        return texto;
    }

    private void configurarEventosTeclado() {
        gridTablero.setOnKeyPressed(this::manejarTeclaPresionada);
        gridTablero.setFocusTraversable(true);
        gridTablero.requestFocus(); // Importante para capturar eventos de teclado
    }

    private void manejarTeclaPresionada(KeyEvent event) {
        if (!gestorTurnos.getTurnoActual().equals(protagonista)) return;
        
        int dx = 0, dy = 0;
        KeyCode tecla = event.getCode();
        
        // Sintaxis compatible con Java 8
        if (tecla == KeyCode.UP || tecla == KeyCode.W) {
            dy = -1;
        } else if (tecla == KeyCode.DOWN || tecla == KeyCode.S) {
            dy = 1;
        } else if (tecla == KeyCode.LEFT || tecla == KeyCode.A) {
            dx = -1;
        } else if (tecla == KeyCode.RIGHT || tecla == KeyCode.D) {
            dx = 1;
        } else {
            return; // Tecla no reconocida
        }
        
        modelo.moverPersonaje(protagonista, dx, dy);
        actualizarVista();
        gestorTurnos.siguienteTurno();
        actualizarEstadisticas();
        
        // Turno de los enemigos
        while (gestorTurnos.getTurnoActual() instanceof Enemigo) {
            Enemigo enemigo = (Enemigo) gestorTurnos.getTurnoActual();
            enemigo.ejecutarTurno(modelo);
            actualizarVista();
            gestorTurnos.siguienteTurno();
            actualizarEstadisticas();
        }
        
        // Verificar fin del juego
        if (gestorTurnos.isJuegoTerminado()) {
            mostrarFinJuego();
        }
    }

    private void mostrarFinJuego() {
        // Implementar lógica para mostrar pantalla de fin de juego
        System.out.println("¡Juego terminado!");
    }

    private void actualizarVista() {
        inicializarVista(); // Redibuja todo el tablero
    }

    private void actualizarEstadisticas() {
        if (protagonista != null) {
            txtEstadisticas.setText(
                "Nombre: " + protagonista.getNombre() + " | " +
                protagonista.getSalud() + " | " +
                "Turno actual: " + gestorTurnos.getTurnoActual().getNombre()
            );
        }
    }
    
    @FXML
    private void reiniciarJuego() {
        // Volver a la pantalla de creación de personaje
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mazmorras/view/CreacionProtagonistaView.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 600);
            Stage stage = (Stage) gridTablero.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Juego de Mazmorras");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void salirJuego() {
        Platform.exit();
    }

    @Override
    public void onEscenarioCambiado(Escenario escenario) {
        // Actualizar vista cuando el escenario cambia
        Platform.runLater(this::actualizarVista);
    }
}