package com.mazmorras;


import com.mazmorras.controlador.ControladorCreacionJugador;
import com.mazmorras.controlador.ControladorJuego;
import com.mazmorras.modelo.Jugador;
import com.mazmorras.modelo.ModeloJuego;
import com.mazmorras.vista.VistaCreacionJugador;
import com.mazmorras.vista.VistaJuego;

import javafx.stage.Stage;

public class Main {
    
    private Stage escenarioPrincipal;
    
    public Main(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        mostrarCreacionJugador();
    }
    
    public void mostrarCreacionJugador() {
        VistaCreacionJugador vistaCreacion = new VistaCreacionJugador();
        new ControladorCreacionJugador(escenarioPrincipal);
        escenarioPrincipal.setScene(vistaCreacion.getScene());
        escenarioPrincipal.show();
    }
    
    public void iniciarJuego(Jugador jugador) {
        ModeloJuego modelo = new ModeloJuego("map1.txt", "enemies1.txt", jugador);
        VistaJuego vistaJuego = new VistaJuego();
        new ControladorJuego(modelo, vistaJuego);
        escenarioPrincipal.setScene((vistaJuego).getScene());
    }
}
