package com.mazmorras.controlador;

import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

import com.mazmorras.modelo.Jugador;
import com.mazmorras.modelo.ModeloJuego;
import com.mazmorras.util.Configuracion;
import com.mazmorras.vista.VistaJuego;

public class ControladorJuego {
    private ModeloJuego modelo;
    private VistaJuego vista;
    private Stage escenario;
    private boolean pausado;
    private Map<KeyCode, Runnable> accionesTeclado;

    public ControladorJuego(Stage escenarioPrincipal) {
        this.escenario = escenarioPrincipal;
        this.pausado = false;
        
        // Inicializar modelo con valores predeterminados
        Jugador jugador = new Jugador("Héroe", 100, 10, 5, 5, 3);
        this.modelo = new ModeloJuego(
            Configuracion.MAPA_PREDETERMINADO,
            Configuracion.ENEMIGOS_PREDETERMINADOS,
            jugador
        );
        
        // Inicializar vista
        this.vista = new VistaJuego();
        
        // Configurar escenario
        configurarEscenario();
        
        // Configurar controles
        inicializarAccionesTeclado();
        configurarManejadoresTeclado();
        
        // Conectar modelo y vista
        modelo.agregarObservador(vista);
        
        // Mostrar vista inicial
        escenario.setScene(vista.getEscena());
        escenario.show();
    }

    public ControladorJuego(ModeloJuego modelo2, VistaJuego vistaJuego) {
        //TODO Auto-generated constructor stub
    }

    private void configurarEscenario() {
        escenario.setTitle("Explorador de Mazmorras - Nivel 1");
        escenario.setWidth(Configuracion.ANCHO_VENTANA);
        escenario.setHeight(Configuracion.ALTO_VENTANA);
        escenario.centerOnScreen();
        
        escenario.setOnCloseRequest(event -> {
            System.out.println("Partida cerrada");
            // Aquí podrías añadir lógica para guardar el progreso
        });
    }

    private void inicializarAccionesTeclado() {
        accionesTeclado = new HashMap<>();
        
        // Movimiento del jugador
        accionesTeclado.put(KeyCode.UP, () -> modelo.moverJugador(0, -1));
        accionesTeclado.put(KeyCode.W, () -> modelo.moverJugador(0, -1));
        accionesTeclado.put(KeyCode.DOWN, () -> modelo.moverJugador(0, 1));
        accionesTeclado.put(KeyCode.S, () -> modelo.moverJugador(0, 1));
        accionesTeclado.put(KeyCode.LEFT, () -> modelo.moverJugador(-1, 0));
        accionesTeclado.put(KeyCode.A, () -> modelo.moverJugador(-1, 0));
        accionesTeclado.put(KeyCode.RIGHT, () -> modelo.moverJugador(1, 0));
        accionesTeclado.put(KeyCode.D, () -> modelo.moverJugador(1, 0));
        
        // Acciones del juego
        accionesTeclado.put(KeyCode.SPACE, this::usarHabilidadEspecial);
        accionesTeclado.put(KeyCode.P, this::alternarPausa);
        accionesTeclado.put(KeyCode.ESCAPE, this::mostrarMenuPausa);
        accionesTeclado.put(KeyCode.I, this::mostrarInventario);
    }

    private void configurarManejadoresTeclado() {
        EventHandler<KeyEvent> manejadorTeclado = evento -> {
            if (modelo.isJuegoTerminado() || pausado) {
                return;
            }
            
            KeyCode tecla = evento.getCode();
            if (accionesTeclado.containsKey(tecla)) {
                accionesTeclado.get(tecla).run();
                verificarEstadoJuego();
            }
        };
        
        vista.getEscena().setOnKeyPressed(manejadorTeclado);
    }

    private void usarHabilidadEspecial() {
        // Implementación específica según el tipo de jugador
        vista.mostrarMensaje("¡Habilidad especial activada!");
    }

    private void alternarPausa() {
        pausado = !pausado;
        vista.mostrarMensaje(pausado ? "JUEGO EN PAUSA" : "");
    }

    private void mostrarMenuPausa() {
        // Implementar lógica para mostrar menú de pausa
        alternarPausa();
    }

    private void mostrarInventario() {
        // Implementar lógica para mostrar inventario
        vista.mostrarMensaje("Inventario abierto");
    }

    private void verificarEstadoJuego() {
        if (modelo.isJuegoTerminado()) {
            String mensaje = modelo.isVictoria() 
                ? "¡HAS GANADO! Puntuación: " + modelo.getJugador().getPosicion()
                : "¡GAME OVER!";
            
            vista.mostrarMensaje(mensaje);
            pausado = true;
        }
    }

    // Método para reiniciar el juego
    public void reiniciarJuego() {
        Jugador jugador = modelo.getJugador(); // Mantiene el mismo jugador
        this.modelo = new ModeloJuego(
            Configuracion.MAPA_PREDETERMINADO,
            Configuracion.ENEMIGOS_PREDETERMINADOS,
            jugador
        );
        this.pausado = false;
        modelo.agregarObservador(vista);
        vista.limpiarMensaje();
    }

    // Getters
    public ModeloJuego getModelo() {
        return modelo;
    }

    public VistaJuego getVista() {
        return vista;
    }

    public boolean isPausado() {
        return pausado;
    }
}