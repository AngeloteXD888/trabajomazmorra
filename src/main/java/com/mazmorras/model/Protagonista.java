package com.mazmorras.model;

public class Protagonista extends Personaje {
    private String nombre;
    private int fuerza;

    public Protagonista(String nombre, int salud, int velocidad, int fuerza) {
        this.nombre = nombre;
        this.salud = salud;
        this.velocidad = velocidad;
        this.fuerza = fuerza;
    }

    @Override
    public void mover(int dx, int dy, Escenario escenario) {
        if (!escenario.esPared(x + dx, y + dy)) {
            x += dx;
            y += dy;
        }
    }

    @Override
    public void atacar(Personaje objetivo) {
        objetivo.salud -= this.fuerza;
    }

    @Override
    protected int getY() {
        return fuerza;
    }
}