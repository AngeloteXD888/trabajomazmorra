package com.mazmorras;

import com.mazmorras.controller.JuegoControlador;
import com.mazmorras.model.Escenario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Inicializar el escenario del juego
        Escenario escenario = inicializarEscenario();
        
        // 2. Cargar la vista principal
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource("/com/mazmorras/view/MainView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        
        // 3. Configurar el controlador
        JuegoControlador controlador = fxmlLoader.getController();
        controlador.setModelo(escenario);
        
        // 4. Configurar y mostrar la ventana
        configurarVentanaPrincipal(stage, scene);
    }

    private Escenario inicializarEscenario() {
        Escenario escenario = new Escenario();
        try {
            // Cargar mapa y enemigos desde recursos
            escenario.cargarMapa(
                getClass().getResource("/com/mazmorras/resources/mapas/mapa1.txt").toString()
            );
            escenario.cargarEnemigos(
                getClass().getResource("/com/mazmorras/resources/enemigos/enemigos.csv").toString()
            );
        } catch (Exception e) {
            System.err.println("Error al cargar recursos: " + e.getMessage());
            // Fallback: crear escenario mínimo
            escenario.generarEscenarioBasico();
        }
        return escenario;
    }

    private void configurarVentanaPrincipal(Stage stage, Scene scene) {
        stage.setTitle("Juego de Mazmorras - Fase 1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        
        // Manejar cierre de la aplicación
        stage.setOnCloseRequest(e -> {
            System.out.println("Juego terminado");
            System.exit(0);
        });
        
        stage.show();
    }

    

    public static void main(String[] args) {
        launch();
    }
}