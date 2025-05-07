package com.mazmorras.controller;

import java.io.IOException;

import com.mazmorras.model.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class JuegoControlador {
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
        
        if (celda.esPared()) {
            texto.setText("#");
            texto.setFill(Color.DARKGRAY);
        } else {
            texto.setText(".");
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
        if (gestorTurnos.getTurnoActual() instanceof Enemigo) {
            Enemigo enemigo = (Enemigo) gestorTurnos.getTurnoActual();
            enemigo.ejecutarTurno(modelo);
            actualizarVista();
            gestorTurnos.siguienteTurno();
        }
    }

    private void actualizarVista() {
        inicializarVista(); // Redibuja todo (simple pero ineficiente para mapas grandes)
    }

    private void actualizarEstadisticas() {
        txtEstadisticas.setText(
            "Salud: " + protagonista.getSalud() + "\n" +
            "Turno: " + gestorTurnos.getTurnoActual().getNombre()
        );
    }

    @SuppressWarnings("unused")
    public void iniciarJuego() throws IOException {
        System.out.println("Bienvenido al Juego de Mazmorras");
        // Inicializa el mapa, protagonista, enemigos, etc.
        Mapa mapa = new Mapa(null);
        Protagonista protagonista = new Protagonista("Héroe", 100, 10, 0);
    
        // Ejemplo de flujo de juego simple:
        while (!protagonista.estaMuerto()) {
            System.out.println("Elige una acción:");
            System.out.println("1. Explorar");
            System.out.println("2. Salir del juego");
    
            int opcion = InputReader.leerEntero();
    
            switch (opcion) {
                case 1:
                    System.out.println("Explorando...");
                    // Aquí iría la lógica de exploración
                    break;
                case 2:
                    System.out.println("Saliendo del juego...");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    
        System.out.println("¡Juego terminado!");
    }
    
}