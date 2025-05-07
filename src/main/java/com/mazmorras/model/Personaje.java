package com.mazmorras.model;

public abstract class Personaje implements Cloneable {
    protected int x, y;
    protected int salud;
    protected int velocidad;
    protected int percepcion;

    // Método getter para la velocidad
    public int getVelocidad() {
        return velocidad;
    }

    public abstract void mover(int dx, int dy, Escenario escenario);
    public abstract void atacar(Personaje objetivo);
    protected abstract int getY();

    @Override
    public Personaje clone() {
        try {
            return (Personaje) super.clone(); // Clone superficial suficiente para Personaje
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La clase Personaje debería ser Cloneable", e);
        }
    }
}
