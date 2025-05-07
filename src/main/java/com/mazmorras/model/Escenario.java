package com.mazmorras.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Escenario {
    private int[][] matriz;
    private List<Personaje> personajes;

    public Escenario(String rutaArchivo) {
        cargarEscenarioDesdeArchivo(rutaArchivo);
        personajes = new ArrayList<>();
    }
    private void cargarEscenarioDesdeArchivo(String ruta) {
        List<String> lineas = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Ignorar líneas vacías y comentarios
                if (!linea.trim().isEmpty() && !linea.trim().startsWith("#")) {
                    lineas.add(linea);
                }
            }
            
            if (lineas.isEmpty()) {
                throw new IllegalArgumentException("El archivo está vacío o no contiene datos válidos");
            }
            
            int filas = lineas.size();
            int columnas = lineas.get(0).length();
            
            // Validar que todas las líneas tengan el mismo ancho
            for (String l : lineas) {
                if (l.length() != columnas) {
                    throw new IllegalArgumentException("El mapa no es uniforme - todas las filas deben tener el mismo ancho");
                }
            }
            
            // Crear la matriz
            matriz = new int[filas][columnas];
            
            // Procesar cada carácter
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    char c = lineas.get(i).charAt(j);
                    matriz[i][j] = parsearCaracter(c);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            // Cargar mapa por defecto en caso de error
            cargarMapaPorDefecto();
        }
    }
    
    private int parsearCaracter(char c) {
        switch (c) {
            case '#': return 1;  // Pared
            case '.': return 0;  // Suelo
            case 'P': return 2;  // Punto de inicio del jugador
            case 'E': return 3;  // Enemigo
            case 'S': return 4;  // Salida
            case 'T': return 5;  // Tesoro
            default: 
                System.err.println("Carácter desconocido en el mapa: '" + c + "'. Usando suelo como predeterminado.");
                return 0;
        }
    }
    
    private void cargarMapaPorDefecto() {
        System.out.println("Cargando mapa por defecto...");
        // Mapa simple 10x10 con bordes de paredes
        matriz = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    matriz[i][j] = 1; // Paredes en los bordes
                } else {
                    matriz[i][j] = 0; // Suelo en el centro
                }
            }
        }
        matriz[1][1] = 2; // Posición inicial del jugador
    }

    public boolean esPared(int x, int y) {
        return matriz[y][x] == 1; // 1 = pared, 0 = suelo
    }

    public void agregarPersonaje(Personaje p) {
        personajes.add(p);
    }

   public Map<String, Object> getMapa() {
    Map<String, Object> mapaCompleto = new HashMap<>();
    
    // 1. Matriz básica del escenario
    mapaCompleto.put("matriz", this.matriz);
    
    // 2. Posiciones de personajes
    Map<String, int[]> posiciones = new HashMap<>();
    for (Personaje p : personajes) {
        String clave = (p instanceof Protagonista) ? "protagonista" : "enemigo_" + personajes.indexOf(p);
        posiciones.put(clave, new int[]{p.getY(), p.getY()});
    }
    mapaCompleto.put("personajes", posiciones);
    
    // 3. Metadatos adicionales
    mapaCompleto.put("ancho", matriz[0].length);
    mapaCompleto.put("alto", matriz.length);
    
    return mapaCompleto;
}
   
public boolean esPosicionValida(int i, int j) {
    return i >= 0 && i < matriz.length && j >= 0 && j < matriz[0].length && matriz[i][j] != 1;
}

public Personaje getPersonajeEn(int i, int j) {
    for (Personaje p : personajes) {
        if (p.getY() == i && p.getY() == j) {
            return p;
        }
    }
    return null;
}

public void removerPersonaje(Enemigo enemigo) {
    personajes.remove(enemigo);
}


}