package com.mazmorras;

import com.mazmorras.controlador.ControladorJuego;
import com.mazmorras.util.Configuracion;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            configurarEscenario(escenarioPrincipal);
                        new ControladorJuego(escenarioPrincipal);
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarEscenario(Stage escenario) {
        escenario.setTitle("Explorador de Mazmorras");
        escenario.setWidth(Configuracion.ANCHO_VENTANA);
        escenario.setHeight(Configuracion.ALTO_VENTANA);
        escenario.setMinWidth(800);
        escenario.setMinHeight(600);
        escenario.centerOnScreen();
        
        // Manejar el cierre de la aplicación
        escenario.setOnCloseRequest(event -> {
            System.out.println("Aplicación cerrada");
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        // Iniciar la aplicación JavaFX
        launch(args);
    }
}