package com.mazmorras.vista;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;

public class VistaCreacionJugador {

    private VBox raiz;
    private TextField campoNombre;
    private ComboBox<TipoJugador> comboTipo;
    private Slider sliderSalud;
    private Slider sliderAtaque;
    private Slider sliderDefensa;
    private Slider sliderVelocidad;
    private Slider sliderPercepcion;
    private Label labelValorSalud;
    private Label labelValorAtaque;
    private Label labelValorDefensa;
    private Label labelValorVelocidad;
    private Label labelValorPercepcion;
    private TextArea areaDescripcion;
    private Button botonIniciar;
    private Text textoError;

    private Scene escena;

    public VistaCreacionJugador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mazmorra/fxml/vista-creacion-jugador.fxml"));
            loader.setController(this);
            raiz = loader.load();
            escena = new Scene(raiz, 600, 500);
            configurarComponentes();
            aplicarEstilos();
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la vista de creación de jugador", e);
        }
    }

    private void configurarComponentes() {
        // Configurar ComboBox de tipos de jugador
        comboTipo.getItems().setAll(TipoJugador.values());
        comboTipo.getSelectionModel().selectFirst();
        comboTipo.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarEstadisticasPorTipo(newVal);
            actualizarDescripcion(newVal);
        });

        // Configurar sliders
        configurarSlider(sliderSalud, labelValorSalud, 50, 200, 100);
        configurarSlider(sliderAtaque, labelValorAtaque, 1, 30, 10);
        configurarSlider(sliderDefensa, labelValorDefensa, 1, 20, 5);
        configurarSlider(sliderVelocidad, labelValorVelocidad, 1, 10, 5);
        configurarSlider(sliderPercepcion, labelValorPercepcion, 1, 10, 3);

        // Mostrar valores iniciales
        actualizarEstadisticasPorTipo(comboTipo.getValue());
    }

    private void configurarSlider(Slider slider, Label label, double min, double max, double valorInicial) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(valorInicial);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int valor = newVal.intValue();
            label.setText(String.valueOf(valor));
        });
    }

    private void actualizarEstadisticasPorTipo(TipoJugador tipo) {
        sliderSalud.setValue(tipo.getSalud());
        sliderAtaque.setValue(tipo.getAtaque());
        sliderDefensa.setValue(tipo.getDefensa());
        sliderVelocidad.setValue(tipo.getVelocidad());
        sliderPercepcion.setValue(tipo.getPercepcion());
    }

    private void actualizarDescripcion(TipoJugador tipo) {
        String descripcion = "";
        switch (tipo) {
            case GUERRERO:
                descripcion = "Guerrero: Combate cuerpo a cuerpo especializado. Alta salud y ataque.";
                break;
            case MAGO:
                descripcion = "Mago: Poderosos hechizos pero frágil. Alto ataque mágico.";
                break;
            case ARQUERO:
                descripcion = "Arquero: Ataques a distancia precisos. Buena percepción.";
                break;
            case LADRON:
                descripcion = "Ladrón: Movimiento rápido y ataques sigilosos.";
                break;
            case PALADIN:
                descripcion = "Paladín: Defensa sólida y habilidades de apoyo.";
                break;
        }
        areaDescripcion.setText(descripcion);
    }

    private void aplicarEstilos() {
        // Estilos pueden ser aplicados aquí o mediante CSS
        raiz.getStyleClass().add("panel-creacion");
        botonIniciar.getStyleClass().add("boton-importante");
    }

    public void mostrarError(String mensaje) {
        textoError.setText(mensaje);
        textoError.setVisible(true);
    }

    public void limpiarError() {
        textoError.setText("");
        textoError.setVisible(false);
    }

    // Getters para los componentes
    public Scene getEscena() {
        return escena;
    }

    public TextField getCampoNombre() {
        return campoNombre;
    }

    public ComboBox<TipoJugador> getComboTipo() {
        return comboTipo;
    }

    public Slider getSliderSalud() {
        return sliderSalud;
    }

    public Slider getSliderAtaque() {
        return sliderAtaque;
    }

    public Slider getSliderDefensa() {
        return sliderDefensa;
    }

    public Slider getSliderVelocidad() {
        return sliderVelocidad;
    }

    public Slider getSliderPercepcion() {
        return sliderPercepcion;
    }

    public Button getBotonIniciar() {
        return botonIniciar;
    }

    public Scene getScene() {
        if (escena == null) {
        }
        return escena;
    }

}