package com.mazmorras.controller;

import java.io.IOException;

import com.mazmorras.model.Protagonista;
import com.mazmorras.model.Escenario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreacionProtagonistaController {

    @FXML
    private TextField nombreField;
    
    @FXML
    private TextField saludField;
    
    @FXML
    private TextField fuerzaField;
    
    @FXML
    private TextField velocidadField;
    
    @FXML
    private void crearProtagonista() {
        try {
            // Validar campos
            if (nombreField.getText().isEmpty() || 
                saludField.getText().isEmpty() ||
                fuerzaField.getText().isEmpty() ||
                velocidadField.getText().isEmpty()) {
                
                mostrarError("Todos los campos son obligatorios");
                return;
            }
            
            // Parsear valores
            String nombre = nombreField.getText();
            int salud = Integer.parseInt(saludField.getText());
            int fuerza = Integer.parseInt(fuerzaField.getText());
            int velocidad = Integer.parseInt(velocidadField.getText());
            
            // Validar rangos
            if (salud < 50 || salud > 100) {
                mostrarError("La salud debe estar entre 50 y 100");
                return;
            }
            
            if (fuerza < 5 || fuerza > 20) {
                mostrarError("La fuerza debe estar entre 5 y 20");
                return;
            }
            
            if (velocidad < 1 || velocidad > 10) {
                mostrarError("La velocidad debe estar entre 1 y 10");
                return;
            }
            
            // Crear protagonista
            Protagonista protagonista = new Protagonista(nombre, salud, velocidad, fuerza);
            protagonista.setPosicion(1, 1); // Posición inicial
            
            // Crear escenario y cargar mapa
            Escenario escenario = new Escenario();
            try {
                escenario.cargarMapa("src/main/resources/com/mazmorras/mapa1.txt");
            } catch (IOException e) {
                System.out.println("Error al cargar el mapa, generando escenario básico");
                escenario.generarEscenarioBasico();
            }
            
            // Asignar protagonista al escenario
            escenario.setProtagonista(protagonista);
            
            // Cargar la vista de juego
            cargarVistaJuego(escenario);
            
        } catch (NumberFormatException e) {
            mostrarError("Los valores numéricos no son válidos");
        } catch (Exception e) {
            mostrarError("Error al crear personaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void cargarVistaJuego(Escenario escenario) {
        try {
            // Cargar la vista de juego
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mazmorras/view/JuegoView.fxml")
            );
            
            Parent root = loader.load();
            
            // Acceder al controlador y configurar el modelo
            JuegoControlador controlador = loader.getController();
            controlador.setModelo(escenario);
            
            // Crear nueva escena
            Scene juegoScene = new Scene(root, 800, 600);
            
            // Obtener la ventana actual
            Stage stage = (Stage) nombreField.getScene().getWindow();
            
            // Cambiar a la escena de juego
            stage.setScene(juegoScene);
            stage.setTitle("Mazmorras - Aventura");
            
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de juego");
            e.printStackTrace();
        }
    }
}