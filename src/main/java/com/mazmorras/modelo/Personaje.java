package com.mazmorras.modelo;

public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected int saludMaxima;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int percepcion;
    protected Posicion posicion;
    
    public Personaje(String nombre, int salud, int ataque, int defensa, int velocidad, int percepcion) {
        this.nombre = nombre;
        this.saludMaxima = salud;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.posicion = new Posicion(0, 0);
    }
    
    public Personaje() {
    }

    public boolean estaVivo() {
        return salud > 0;
    }
    
    public void recibirDanio(int danio) {
        salud = Math.max(0, salud - danio);
    }
    
    public void setPosicion(int x, int y) {
        posicion.setX(x);
        posicion.setY(y);
    }
    
    public Posicion getPosicion() {
        return posicion;
    }
    
    public boolean puedePercibir(Posicion posicionObjetivo, Posicion posicionActual) {
        int dx = Math.abs(posicionObjetivo.getX() - posicionActual.getX());
        int dy = Math.abs(posicionObjetivo.getY() - posicionActual.getY());
        return dx <= percepcion && dy <= percepcion;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public int getSalud() { return salud; }
    public int getSaludMaxima() { return saludMaxima; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public int getPercepcion() { return percepcion; }
}
