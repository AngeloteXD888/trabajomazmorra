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
        escenario.agregarPersonaje(this); // Colocar en nueva posición
    }

    @Override
    public void atacar(Personaje objetivo) {
        objetivo.salud -= 5; // Daño base
    }

    @Override
    protected int getY() {
        return percepcion;
    }
}