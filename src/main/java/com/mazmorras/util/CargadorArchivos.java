package com.mazmorras.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mazmorras.modelo.Casilla;
import com.mazmorras.modelo.Enemigo;

public class CargadorArchivos {
    
    public static Casilla[][] cargarMapa(String nombreArchivo) {
        List<String> lineas = leerArchivo("resources/mazmorra/mapas/" + nombreArchivo);
        
        if (lineas == null || lineas.isEmpty()) {
            System.err.println("El archivo del mapa está vacío o no se pudo leer");
            return null;
        }
        
        int ancho = lineas.get(0).length();
        int alto = lineas.size();
        Casilla[][] mapa = new Casilla[ancho][alto];
        
        for (int y = 0; y < alto; y++) {
            String linea = lineas.get(y);
            // Validar que todas las líneas tengan el mismo ancho
            if (linea.length() != ancho) {
                System.err.println("Error: El mapa no es uniforme en la línea " + (y + 1));
                return null;
            }
            
            for (int x = 0; x < ancho; x++) {
                char caracter = linea.charAt(x);
                switch (caracter) {
                    case '#':
                        mapa[x][y] = Casilla.PARED;
                        break;
                    case '.':
                        mapa[x][y] = Casilla.SUELO;
                        break;
                    default:
                        System.err.println("Carácter desconocido en el mapa: '" + caracter + "'");
                        mapa[x][y] = Casilla.SUELO;
                }
            }
        }
        
        return mapa;
    }

    
    public static List<Enemigo> cargarEnemigos(String nombreArchivo) {
        List<String> lineas = leerArchivo("resources/mazmorra/enemigos/" + nombreArchivo);
        List<Enemigo> enemigos = new ArrayList<>();
        
        if (lineas == null) {
            return enemigos;
        }
        
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            // Saltar líneas vacías o comentarios (que empiezan con #)
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }
            
            String[] partes = linea.split(",");
            if (partes.length != 6) {
                System.err.println("Error en línea " + (i + 1) + ": formato incorrecto. Se esperaban 6 valores separados por comas.");
                continue;
            }
            
            try {
                String nombre = partes[0].trim();
                int salud = Integer.parseInt(partes[1].trim());
                int ataque = Integer.parseInt(partes[2].trim());
                int defensa = Integer.parseInt(partes[3].trim());
                int velocidad = Integer.parseInt(partes[4].trim());
                int percepcion = Integer.parseInt(partes[5].trim());
                
                enemigos.add(new Enemigo(nombre, salud, ataque, defensa, velocidad, percepcion));
            } catch (NumberFormatException e) {
                System.err.println("Error en línea " + (i + 1) + ": valor numérico inválido.");
            }
        }
        
        return enemigos;
    }

    private static List<String> leerArchivo(String rutaCompleta) {
        List<String> lineas = new ArrayList<>();
        
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + rutaCompleta);
            e.printStackTrace();
            return null;
        }
        
        return lineas;
    }

    public static boolean validarMapa(Casilla[][] mapa) {
        if (mapa == null || mapa.length == 0 || mapa[0].length == 0) {
            System.err.println("El mapa no puede estar vacío");
            return false;
        }
        
        for (Casilla[] columna : mapa) {
            if (columna == null || columna.length != mapa[0].length) {
                System.err.println("El mapa no es uniforme");
                return false;
            }
        }
        
        return true;
    }
}