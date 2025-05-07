package com.mazmorras.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Mapa {
    private Celda[][] celdas;
    private int ancho;
    private int alto;
    private Random random;

    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = new Celda[alto][ancho];
        this.random = new Random();
        generarMapaAleatorio();
    }

    public Mapa(String rutaArchivo) throws IOException {
        cargarMapaDesdeArchivo(rutaArchivo);
    }

    private void generarMapaAleatorio() {
        // Generar bordes de paredes
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                boolean esPared = (x == 0 || y == 0 || x == ancho-1 || y == alto-1);
                celdas[y][x] = new Celda(esPared);
            }
        }

        // Generar paredes internas aleatorias (20% de probabilidad)
        for (int y = 1; y < alto-1; y++) {
            for (int x = 1; x < ancho-1; x++) {
                if (random.nextDouble() < 0.2) {
                    celdas[y][x] = new Celda(true);
                } else {
                    celdas[y][x] = new Celda(false);
                }
            }
        }

        // Asegurar que el punto de inicio (1,1) y opuesto (ancho-2, alto-2) sean accesibles
        celdas[1][1] = new Celda(false);
        celdas[alto-2][ancho-2] = new Celda(false);
    }

    private void cargarMapaDesdeArchivo(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int y = 0;
            
            // Leer dimensiones del mapa
            String[] dimensiones = br.readLine().split(",");
            ancho = Integer.parseInt(dimensiones[0]);
            alto = Integer.parseInt(dimensiones[1]);
            celdas = new Celda[alto][ancho];
            
            // Leer celdas
            while ((linea = br.readLine()) != null && y < alto) {
                String[] valores = linea.split(",");
                for (int x = 0; x < ancho && x < valores.length; x++) {
                    boolean esPared = valores[x].trim().equals("1");
                    celdas[y][x] = new Celda(esPared);
                }
                y++;
            }
        }
    }

    public Celda getCelda(int x, int y) {
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            return celdas[y][x];
        }
        return null;
    }

    public boolean esPared(int x, int y) {
        Celda celda = getCelda(x, y);
        return celda != null && celda.esPared();
    }

    public boolean esPosicionValida(int x, int y) {
        Celda celda = getCelda(x, y);
        return celda != null && !celda.esPared() && !celda.tienePersonaje();
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void colocarPersonaje(Personaje personaje, int x, int y) {
        Celda celda = getCelda(x, y);
        if (celda != null && !celda.esPared()) {
            celda.agregarPersonaje(personaje);
        }
    }

    public void removerPersonaje(int x, int y) {
        Celda celda = getCelda(x, y);
        if (celda != null) {
            celda.removerPersonaje();
        }
    }
}