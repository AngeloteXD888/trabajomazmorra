package com.mazmorras.controlador;

import com.mazmorras.modelo.Jugador;
import com.mazmorras.modelo.ModeloJuego;
import com.mazmorras.vista.VistaJuego;

import javafx.stage.Stage;

public class ControladorPrincipal {
    private Stage escenarioPrincipal;
    
    public ControladorPrincipal(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        mostrarCreacionJugador();
    }
    
    public void mostrarCreacionJugador() {
        VistaCreacionJugador vistaCreacion = new VistaCreacionJugador();
        new ControladorCreacionJugador();
        escenarioPrincipal.setScene(vistaCreacion.getEscena());
        escenarioPrincipal.show();
    }
    
    public void iniciarJuego(Jugador jugador) {
        ModeloJuego modelo = new ModeloJuego("mapa1.txt", "enemigos1.txt", jugador);
        VistaJuego vistaJuego = new VistaJuego();
        new ControladorJuego(modelo, vistaJuego);
        escenarioPrincipal.setScene(vistaJuego.getEscena());
    }
}