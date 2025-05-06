package com.mazmorras.vista;

public class TipoJugador {
    private final String nombre;
    private final int saludMaxima;
    private final int ataque;
    private final int defensa;
    private final int poderMagico;
    private final int velocidad;
    private final double modificadorSalud;
    private final double modificadorPercepcion;

    // Tipos predefinidos como constantes
    public static final TipoJugador GUERRERO = new TipoJugador("Guerrero", 150, 20, 10, 5, 3, 1.0, 0.7);
    public static final TipoJugador MAGO = new TipoJugador("Mago", 100, 10, 5, 20, 5, 0.8, 1.2);
    public static final TipoJugador ARQUERO = new TipoJugador("Arquero", 120, 15, 8, 10, 8, 0.9, 1.1);
    public static final TipoJugador LADRON = new TipoJugador("Ladrón", 110, 12, 6, 8, 10, 0.85, 1.5);
    public static final TipoJugador PALADIN = new TipoJugador("Paladín", 160, 18, 15, 12, 4, 1.1, 0.8);

    /**
     * Constructor privado para crear tipos de jugador.
     * 
     * @param nombre Nombre del tipo de jugador
     * @param saludMaxima Salud máxima inicial
     * @param ataque Poder de ataque físico
     * @param defensa Capacidad de defensa
     * @param poderMagico Poder de ataque mágico
     * @param velocidad Velocidad de movimiento y ataque
     * @param modificadorSalud Modificador para cálculos de salud
     * @param modificadorPercepcion Modificador para percepción de objetos/trampas
     */
    private TipoJugador(String nombre, int saludMaxima, int ataque, int defensa, int poderMagico, 
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

    /**
     * Calcula la salud actual basada en el modificador de salud
     * @return Valor de salud ajustado
     */
    public double getSalud() {
        return saludMaxima * modificadorSalud;
    }

    /**
     * Calcula el valor de percepción del jugador
     * @return Valor de percepción para detectar objetos/trampas
     */
    public double getPercepcion() {
        return velocidad * modificadorPercepcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Método para obtener todos los tipos disponibles
     * @return Array con todos los tipos de jugador
     */
    public static TipoJugador[] valores() {
        return new TipoJugador[]{GUERRERO, MAGO, ARQUERO, LADRON, PALADIN};
    }
}