package com.mazmorras.vista;

public enum TipoJugador {
    GUERRERO("Guerrero", 150, 20, 10, 5, 3, 1.0, 0.7),
    MAGO("Mago", 100, 10, 5, 20, 5, 0.8, 1.2),
    ARQUERO("Arquero", 120, 15, 8, 10, 8, 0.9, 1.1),
    LADRON("Ladrón", 110, 12, 6, 8, 10, 0.85, 1.5),
    PALADIN("Paladín", 160, 18, 15, 12, 4, 1.1, 0.8);

    private final String nombre;
    private final int saludMaxima;
    private final int ataque;
    private final int defensa;
    private final int poderMagico;
    private final int velocidad;
    private final double modificadorSalud;
    private final double modificadorPercepcion;

    TipoJugador(String nombre, int saludMaxima, int ataque, int defensa, int poderMagico, 
               int velocidad, double modificadorSalud, double modificadorPercepcion) {
        this.nombre = nombre;
        this.saludMaxima = saludMaxima;
        this.ataque = ataque;
        this.defensa = defensa;
        this.poderMagico = poderMagico;
        this.velocidad = velocidad;
        this.modificadorSalud = modificadorSalud;
        this.modificadorPercepcion = modificadorPercepcion;
    }

    // Getters básicos
    public String getNombre() {
        return nombre;
    }

    public int getSaludMaxima() {
        return saludMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public int getVelocidad() {
        return velocidad;
    }

    
    public double getSalud() {
        return saludMaxima * modificadorSalud;
    }

    
    public double getPercepcion() {
        return velocidad * modificadorPercepcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}