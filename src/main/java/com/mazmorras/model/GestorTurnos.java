package com.mazmorras.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorTurnos {
    private List<Personaje> personajes;
    private int turnoActual;
    private boolean juegoTerminado;

    public GestorTurnos() {
        this.personajes = new ArrayList<>();
        this.turnoActual = 0;
        this.juegoTerminado = false;
    }

    /**
     * Añade personajes al gestor de turnos y los ordena por velocidad
     * @param personajes Lista de personajes (protagonista y enemigos)
     */
    public void agregarPersonajes(List<Personaje> personajes) {
        this.personajes.addAll(personajes);
        ordenarPorVelocidad();
    }

    private void ordenarPorVelocidad() {
        personajes.sort(Comparator.comparingInt(Personaje::getVelocidad).reversed());
    }

    public Personaje siguienteTurno() {
        if (juegoTerminado) {
            return null;
        }

        Personaje actual = personajes.get(turnoActual);
        turnoActual = (turnoActual + 1) % personajes.size();
        
        return actual;
    }

    /**
     * Notifica que un personaje ha muerto y lo elimina del sistema de turnos
     * @param personaje Personaje eliminado
     */
    public void personajeMuerto(Personaje personaje) {
        personajes.remove(personaje);
        
        if (turnoActual >= personajes.size()) {
            turnoActual = 0;
        }
        verificarFinJuego();
    }

    /**
     * Verifica si se cumplen condiciones para terminar el juego
     */
    private void verificarFinJuego() {
        // Si no quedan enemigos
        boolean quedanEnemigos = personajes.stream()
            .anyMatch(p -> p instanceof Enemigo);
        
        // Si el protagonista ha muerto
        boolean protagonistaVivo = personajes.stream()
            .anyMatch(p -> p instanceof Protagonista);
        
        juegoTerminado = !quedanEnemigos || !protagonistaVivo;
    }


    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public Personaje getTurnoActual() {
        return personajes.get(turnoActual);
    }

    public List<Personaje> getOrdenTurnos() {
        return new ArrayList<>(personajes);
    }
}