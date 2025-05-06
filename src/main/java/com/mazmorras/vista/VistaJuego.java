package com.mazmorras.vista;

import com.mazmorras.modelo.Casilla;
import com.mazmorras.modelo.Enemigo;
import com.mazmorras.modelo.Jugador;
import com.mazmorras.modelo.ModeloJuego;
import com.mazmorras.modelo.Personaje;
import com.mazmorras.modelo.Posicion;
import com.mazmorras.util.Observador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VistaJuego implements Observador {
    @FXML private BorderPane raiz;
    @FXML private Canvas lienzo;
    @FXML private VBox panelInfo;
    @FXML private Label estadisticasJugador;
    @FXML private Label ordenTurnos;
    
    private Scene escena;
    
    public VistaJuego() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mazmorra/fxml/vista-juego.fxml"));
            loader.setController(this);
            raiz = loader.load();
            escena = new Scene(raiz, 800, 500);
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar VistaJuego", e);
        }
    }
    
    @Override
    public void update(Object observable) {
        if (observable instanceof ModeloJuego) {
            ModeloJuego modelo = (ModeloJuego) observable;
            dibujarMapa(modelo);
            actualizarEstadisticasJugador(modelo);
            actualizarOrdenTurnos(modelo);
        }
    }
    
    private void dibujarMapa(ModeloJuego modelo) {
        GraphicsContext gc = lienzo.getGraphicsContext2D();
        gc.clearRect(0, 0, lienzo.getWidth(), lienzo.getHeight());
        
        int tamanoCasilla = 30;
        Casilla[][] mapa = modelo.getMapa();
        
        for (int x = 0; x < mapa.length; x++) {
            for (int y = 0; y < mapa[0].length; y++) {
                if (mapa[x][y] == Casilla.PARED) {
                    gc.setFill(Color.DARKGRAY);
                } else {
                    gc.setFill(Color.LIGHTGRAY);
                }
                gc.fillRect(x * tamanoCasilla, y * tamanoCasilla, tamanoCasilla, tamanoCasilla);
            }
        }
        
        Jugador jugador = modelo.getJugador();
        Posicion posJugador = jugador.getPosicion();
        gc.setFill(Color.BLUE);
        gc.fillOval(posJugador.getX() * tamanoCasilla, posJugador.getY() * tamanoCasilla, tamanoCasilla, tamanoCasilla);
        
        for (Enemigo enemigo : modelo.getEnemigos()) {
            if (enemigo.estaVivo()) {
                Posicion posEnemigo = enemigo.getPosicion();
                gc.setFill(Color.RED);
                gc.fillRect(posEnemigo.getX() * tamanoCasilla, posEnemigo.getY() * tamanoCasilla, tamanoCasilla, tamanoCasilla);
            }
        }
    }
    
    private void actualizarEstadisticasJugador(ModeloJuego modelo) {
        Jugador jugador = modelo.getJugador();
        estadisticasJugador.setText(String.format(
            "Jugador: %s\nHP: %d/%d\nAtaque: %d\nDefensa: %d\nVelocidad: %d",
            jugador.getNombre(),
            jugador.getSalud(),
            jugador.getSaludMaxima(),
            jugador.getAtaque(),
            jugador.getDefensa(),
            jugador.getVelocidad()
        ));
    }
    
    private void actualizarOrdenTurnos(ModeloJuego modelo) {
        StringBuilder sb = new StringBuilder("Orden de Turnos:\n");
        for (Personaje personaje : modelo.getOrdenTurnos()) {
            sb.append(personaje.getNombre()).append(" (").append(personaje.getVelocidad()).append(")\n");
        }
        ordenTurnos.setText(sb.toString());
    }
    
    public Scene getEscena() {
        return escena;
    }
    
    public Canvas getLienzo() {
        return lienzo;
    }

    public Scene getScene() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScene'");
    }
}