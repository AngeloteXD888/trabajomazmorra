package com.mazmorras.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Escenario {
    private Celda[][] celdas;
    private List<Personaje> personajes;
    private Protagonista protagonista;
    private Random random;
    private int ancho;
    private int alto;

    public Escenario() {
        this.personajes = new ArrayList<>();
        this.random = new Random();
    }

    public void cargarMapa(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            List<String> lineas = new ArrayList<>();
            String linea;

            // Leer todas las líneas
            while ((linea = br.readLine()) != null) {
                lineas.add(linea.trim());
            }

            // Inicializar matriz de celdas
            this.alto = lineas.size();
            this.ancho = lineas.get(0).split(",").length;
            this.celdas = new Celda[alto][ancho];

            // Procesar cada celda
            for (int y = 0; y < alto; y++) {
                String[] valores = lineas.get(y).split(",");
                for (int x = 0; x < ancho; x++) {
                    boolean esPared = valores[x].trim().equals("1");
                    celdas[y][x] = new Celda(esPared);
                }
            }
        }
    }

    public void cargarEnemigos(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primeraLinea = true; // Saltar cabecera

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    int x = Integer.parseInt(datos[0].trim());
                    int y = Integer.parseInt(datos[1].trim());
                    int salud = Integer.parseInt(datos[2].trim());
                    int velocidad = Integer.parseInt(datos[3].trim());
                    int percepcion = Integer.parseInt(datos[4].trim());

                    if (esPosicionValida(x, y)) {
                        Enemigo enemigo = new Enemigo(x, y, salud, velocidad, percepcion);
                        celdas[y][x].agregarPersonaje(enemigo);
                        personajes.add(enemigo);
                    }
                }
            }
        }
    }

    /**
     * Genera un mapa básico si falla la carga de archivos
     */
    public void generarEscenarioBasico() {
        this.ancho = 10;
        this.alto = 8;
        this.celdas = new Celda[alto][ancho];

        // Bordes como paredes, interior libre
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                boolean esPared = (x == 0 || y == 0 || x == ancho - 1 || y == alto - 1);
                celdas[y][x] = new Celda(esPared);
            }
        }

        // Asegurar posición inicial accesible
        celdas[1][1] = new Celda(false);

        // Añadir un enemigo básico
        Enemigo enemigo = new Enemigo(5, 4, 20, 3, 5);
        celdas[4][5].agregarPersonaje(enemigo);
        personajes.add(enemigo);
    }

    public boolean moverPersonaje(Personaje personaje, int dx, int dy) {
        int nuevoX = personaje.getX() + dx;
        int nuevoY = personaje.getY() + dy;

        if (!esPosicionValida(nuevoX, nuevoY)) {
            return false;
        }

        Celda celdaDestino = celdas[nuevoY][nuevoX];
        if (celdaDestino.tienePersonaje()) {
            personaje.atacar(celdaDestino.getPersonaje());
            return true;
        }

        // Mover personaje
        celdas[personaje.getY()][personaje.getX()].removerPersonaje();
        personaje.setPosicion(nuevoX, nuevoY);
        celdaDestino.agregarPersonaje(personaje);

        return true;
    }

    /**
     * Verifica si una posición es válida para movimiento
     */
    public boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < ancho && y >= 0 && y < alto && !celdas[y][x].esPared();
    }

    /**
     * Obtiene el personaje en una posición específica
     */
    public Personaje getPersonajeEn(int x, int y) {
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            return celdas[y][x].getPersonaje();
        }
        return null;
    }

    // Getters y setters
    public Celda[][] getMapa() {
        return celdas;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public List<Personaje> getTodosPersonajes() {
        List<Personaje> todos = new ArrayList<>(personajes);
        if (protagonista != null) {
            todos.add(protagonista);
        }
        return todos;
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        if (esPosicionValida(protagonista.getX(), protagonista.getY())) {
            celdas[protagonista.getY()][protagonista.getX()].agregarPersonaje(protagonista);
        }
    }

    /**
     * Elimina un personaje del escenario
     */
    public void eliminarPersonaje(Personaje personaje) {
        personajes.remove(personaje);
        celdas[personaje.getY()][personaje.getX()].removerPersonaje();
    }

    public boolean esPared(int i, int j) {
        // Verificar que las coordenadas estén dentro de los límites del mapa
        if (i < 0 || i >= ancho || j < 0 || j >= alto) {
            return true; // Considerar posiciones fuera del mapa como paredes
        }

        // Verificar si la celda existe y es una pared
        Celda celda = celdas[j][i]; // Nota: [fila][columna] -> [y][x]
        return celda != null && celda.esPared();
    }

    public void removerPersonaje(Enemigo enemigo) {
        // 1. Verificar que el enemigo existe y está en el escenario
        if (enemigo == null || !personajes.contains(enemigo)) {
            return;
        }

        // 2. Remover de la celda actual
        int x = enemigo.getX();
        int y = enemigo.getY();

        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            celdas[y][x].removerPersonaje();
        }

        personajes.remove(enemigo);

        notificarObservadores();
    }

    private final List<EscenarioObserver> observadores = new CopyOnWriteArrayList<>();

    private void notificarObservadores() {
        if (observadores.isEmpty()) {
            return;
        }

        for (EscenarioObserver observador : observadores) {
            try {
                if (observador != null) {
                    observador.onEscenarioCambiado(this);
                }
            } catch (Exception e) {
                System.err.println(
                        "Error notificando a " + observador.getClass().getSimpleName() + ": " + e.getMessage());

                e.printStackTrace();
            }
        }
    }
}