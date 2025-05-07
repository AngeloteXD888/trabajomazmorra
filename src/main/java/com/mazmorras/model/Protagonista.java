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

    public int getX() {
        return fuerza;
    }

    @Override
protected void setPosicion(int nuevoX, int nuevoY) {
    // 1. Validar que las nuevas coordenadas sean diferentes
    if (this.x == nuevoX && this.y == nuevoY) {
        return;
    }
    // 2. Actualizar las coordenadas del personaje
    this.x = nuevoX;
    this.y = nuevoY;
}

public String getSalud() {
    Object saludMaxima = null;
    return String.format("Salud: %d/%d", salud, saludMaxima);
}

@Override
public String getNombre() {
    return nombre != null ? nombre : "Sin nombre";
}
}