package com.mazmorras.model;

import java.util.Random;

public class Enemigo extends Personaje {
    private static final Random rand = new Random();

    public Enemigo(int x, int y, int salud, int velocidad, int percepcion) {
        this.x = x;
        this.y = y;
        this.salud = salud;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
    }

    @Override
    public void mover(int dx, int dy, Escenario escenario) {
        // 1. Verificar si el movimiento es válido (no es pared y está dentro del escenario)
        if (!escenario.esPosicionValida(x + dx, y + dy)) {
            return;
        }
    
        // 2. Verificar si hay otro personaje en la posición destino
        Personaje objetivo = escenario.getPersonajeEn(x + dx, y + dy);
        if (objetivo != null) {
            if (objetivo instanceof Protagonista) {
                this.atacar(objetivo); // Atacar al protagonista
            }
            return; // No moverse si hay otro personaje
        }
    
        // 3. Movimiento válido, actualizar posición
        escenario.removerPersonaje(this); // Quitar de la posición actual
        this.x += dx;
        this.y += dy;
        escenario.eliminarPersonaje(this); // Colocar en nueva posición
    }

    @Override
    public void atacar(Personaje objetivo) {
        objetivo.salud -= 5; // Daño base
    }

    @Override
    protected int getY() {
        return percepcion;
    }

    public void ejecutarTurno(Escenario modelo) {
        // 1. Obtener posición del protagonista
        Protagonista protagonista = modelo.getProtagonista();
        if (protagonista == null) return;
    
        // 2. Calcular distancia al protagonista
        int distanciaX = Math.abs(protagonista.getX() - this.x);
        int distanciaY = Math.abs(protagonista.getY() - this.y);
        double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
    
        // 3. Comportamiento basado en la distancia de percepción
        if (distancia <= this.percepcion) {
            // Perseguir al jugador
            moverHaciaProtagonista(protagonista, modelo);
        } else {
            // Movimiento aleatorio
            moverAleatorio(modelo);
        }
    }
    
    private void moverHaciaProtagonista(Protagonista protagonista, Escenario modelo) {
        // Calcular dirección óptima hacia el jugador
        int dx = Integer.compare(protagonista.getX(), this.x);
        int dy = Integer.compare(protagonista.getY(), this.y);
    
        // Intentar mover en la dirección X primero
        if (dx != 0 && modelo.esPosicionValida(this.x + dx, this.y)) {
            modelo.moverPersonaje(this, dx, 0);
        } 
        // Si no puede mover en X, intentar en Y
        else if (dy != 0 && modelo.esPosicionValida(this.x, this.y + dy)) {
            modelo.moverPersonaje(this, 0, dy);
        }
    }
    
    private void moverAleatorio(Escenario modelo) {
        // Lista de posibles direcciones (arriba, abajo, izquierda, derecha)
        int[][] direcciones = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        
        // Barajar direcciones para movimiento aleatorio
        java.util.Collections.shuffle(java.util.Arrays.asList(direcciones));
        
        // Intentar moverse en alguna dirección válida
        for (int[] dir : direcciones) {
            int nuevoX = this.x + dir[0];
            int nuevoY = this.y + dir[1];
            
            if (modelo.esPosicionValida(nuevoX, nuevoY)) {
                modelo.moverPersonaje(this, dir[0], dir[1]);
                break;
            }
        }
    }

    @Override
    protected int getX() {
        return percepcion;
    }

    @Override
    protected void setPosicion(int nuevoX, int nuevoY) {
    }

    public String getSalud() {
        Object saludMaxima = null;
        return String.format("Salud: %d/%d", salud, saludMaxima);
    }
    
    @Override
    public String getNombre() {
        Object nombre = null;
        return nombre != null ? (String) nombre : "Sin nombre";
    }
}