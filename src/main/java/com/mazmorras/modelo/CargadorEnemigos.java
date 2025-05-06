package com.mazmorras.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargadorEnemigos {
    private static final String ARCHIVO_ENEMIGOS = "/com/mazmorras/enemigos/enemigos.txt";

    public static List<Enemigo> cargarTodosEnemigos() throws IOException {
        List<Enemigo> enemigos = new ArrayList<>();
        InputStream is = CargadorEnemigos.class.getResourceAsStream(ARCHIVO_ENEMIGOS);
        
        if (is == null) {
            throw new IOException("Archivo de enemigos no encontrado: " + ARCHIVO_ENEMIGOS);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String linea;
            Map<String, String> propiedadesEnemigo = null;
            
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                
                // Ignorar líneas vacías y comentarios
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                
                // Nueva sección de enemigo
                if (linea.startsWith("[") && linea.endsWith("]")) {
                    // Guardar el enemigo anterior si existe
                    if (propiedadesEnemigo != null) {
                        enemigos.add(crearEnemigoDesdePropiedades(propiedadesEnemigo));
                    }
                    // Iniciar nuevo enemigo
                    propiedadesEnemigo = new HashMap<>();
                    continue;
                }
                
                // Procesar propiedad
                if (propiedadesEnemigo != null) {
                    String[] partes = linea.split("=", 2);
                    if (partes.length == 2) {
                        propiedadesEnemigo.put(partes[0].trim().toLowerCase(), partes[1].trim());
                    }
                }
            }
            
            // Añadir el último enemigo procesado
            if (propiedadesEnemigo != null && !propiedadesEnemigo.isEmpty()) {
                enemigos.add(crearEnemigoDesdePropiedades(propiedadesEnemigo));
            }
        }
        
        return enemigos;
    }

    private static Enemigo crearEnemigoDesdePropiedades(Map<String, String> propiedades) {
        Enemigo enemigo = new Enemigo();
        enemigo.setNombre(propiedades.getOrDefault("nombre", "Enemigo Desconocido"));
        enemigo.setSaludMaxima(Integer.parseInt(propiedades.getOrDefault("salud", "20")));
        enemigo.setAtaque(Integer.parseInt(propiedades.getOrDefault("ataque", "5")));
        enemigo.setDefensa(Integer.parseInt(propiedades.getOrDefault("defensa", "3")));
        enemigo.setVelocidad(Integer.parseInt(propiedades.getOrDefault("velocidad", "3")));
        enemigo.setExperiencia(Integer.parseInt(propiedades.getOrDefault("experiencia", "10")));
        enemigo.setImagen(propiedades.getOrDefault("imagen", "enemigo_generico.png"));
        enemigo.setDescripcion(propiedades.getOrDefault("descripcion", ""));
        
        // Procesar habilidades si existen
        if (propiedades.containsKey("habilidades")) {
            String[] habilidades = propiedades.get("habilidades").split(",");
            for (String habilidad : habilidades) {
                enemigo.agregarHabilidad(habilidad.trim());
            }
        }
        
        return enemigo;
    }
}