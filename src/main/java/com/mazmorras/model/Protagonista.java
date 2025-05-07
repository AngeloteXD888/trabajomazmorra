package com.mazmorras.model;

public class Protagonista extends Personaje {
    private String nombre;
    private int fuerza;
    private int saludMaxima;

    public Protagonista(String nombre, int salud, int velocidad, int fuerza) {
        this.nombre = nombre;
        this.salud = salud;
        this.saludMaxima = salud;
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
    public int getY() {
        return y; // Devuelve la coordenada Y, no la fuerza
    }

    @Override
    public int getX() {
        return x; // Devuelve la coordenada X, no la fuerza
    }

    @Override
    protected void setPosicion(int nuevoX, int nuevoY) {
        this.x = nuevoX;
        this.y = nuevoY;
    }

    public String getSalud() {
        return String.format("Salud: %d/%d", salud, saludMaxima);
    }

    @Override
    public String getNombre() {
        return nombre != null ? nombre : "Sin nombre";
    }
}