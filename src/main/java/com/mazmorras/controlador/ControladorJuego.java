package com.mazmorras.controlador;

import com.mazmorras.modelo.Jugador;
import com.mazmorras.modelo.ModeloJuego;
import com.mazmorras.vista.VistaJuego;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class ControladorJuego {
    private ModeloJuego modelo;
    private VistaJuego vista;
    private boolean juegoPausado;
    
    public ControladorJuego(ModeloJuego modelo, VistaJuego vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.juegoPausado = false;
        
        configurarManejadoresTeclado();
        modelo.agregarObservador(vista);
    }
    
    private void configurarManejadoresTeclado() {
        EventHandler<KeyEvent> manejadorTeclado = evento -> {
            if (modelo.isJuegoTerminado() || juegoPausado) {
                if (evento.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                    if (juegoPausado) {
                        reanudarJuego();
                    } else {
                        pausarJuego();
                    }
                }
                return;
            }
            
            switch (evento.getCode()) {
                case UP:
                case W:
                    modelo.moverJugador(0, -1);
                    break;
                case DOWN:
                case S:
                    modelo.moverJugador(0, 1);
                    break;
                case LEFT:
                case A:
                    modelo.moverJugador(-1, 0);
                    break;
                case RIGHT:
                case D:
                    modelo.moverJugador(1, 0);
                    break;
                case SPACE:
                    // Acción adicional como usar habilidad especial
                    break;
                case ESCAPE:
                    pausarJuego();
                    break;
                default:
                    break;
            }
            
            verificarEstadoJuego();
        };
        
        vista.getEscena().setOnKeyPressed(manejadorTeclado);
    }
    
    private void verificarEstadoJuego() {
        if (modelo.isJuegoTerminado()) {
            if (modelo.isVictoria()) {
                vista.mostrarMensaje("¡Has ganado!");
            } else {
                vista.mostrarMensaje("¡Game Over!");
            }
        }
    }
    
    // Método para reiniciar el juego
    public void reiniciarJuego(Jugador jugador) {
        ModeloJuego nuevoModelo = new ModeloJuego("mapa1.txt", "enemigos1.txt", jugador);
        this.modelo = nuevoModelo;
        modelo.agregarObservador(vista);
        vista.limpiarMensaje();
        this.juegoPausado = false;
    }
    
    // Métodos para pausar/reanudar el juego
    public void pausarJuego() {
        if (!modelo.isJuegoTerminado() && !juegoPausado) {
            juegoPausado = true;
            vista.mostrarMensaje("Juego Pausado");
        }
    }
    
    public void reanudarJuego() {
        if (juegoPausado) {
            juegoPausado = false;
            vista.limpiarMensaje();
        }
    }
}