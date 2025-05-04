package com.mazmorras.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mazmorras.util.CargadorArchivos;

public class ModeloJuego extends Observable {
    private Casilla[][] mapa;
    private Jugador jugador;
    private List<Enemigo> enemigos;
    private List<Personaje> ordenTurnos;
    private boolean juegoTerminado;
    private boolean victoria;
    
    public ModeloJuego(String archivoMapa, String archivoEnemigos, Jugador jugador) {
        this.mapa = CargadorArchivos.cargarMapa(archivoMapa);
        this.enemigos = CargadorArchivos.cargarEnemigos(archivoEnemigos);
        this.jugador = jugador;
        this.juegoTerminado = false;
        this.victoria = false;
        inicializarPersonajes();
        determinarOrdenTurnos();
    }
    
    private void inicializarPersonajes() {
        colocarPersonaje(jugador);
        for (Enemigo enemigo : enemigos) {
            colocarPersonaje(enemigo);
        }
    }
    
    private void colocarPersonaje(Personaje personaje) {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(mapa.length);
            y = rand.nextInt(mapa[0].length);
        } while (mapa[x][y] == Casilla.PARED || obtenerPersonajeEn(x, y) != null);
        
        personaje.setPosicion(x, y);
    }
    
    public void moverJugador(int dx, int dy) {
        if (juegoTerminado) return;
        
        Posicion actual = jugador.getPosicion();
        int nuevoX = actual.getX() + dx;
        int nuevoY = actual.getY() + dy;
        
        if (esMovimientoValido(nuevoX, nuevoY)) {
            Personaje objetivo = obtenerPersonajeEn(nuevoX, nuevoY);
            if (objetivo == null) {
                jugador.setPosicion(nuevoX, nuevoY);
            } else if (objetivo instanceof Enemigo) {
                atacar(jugador, (Enemigo) objetivo);
            }
            siguienteTurno();
        }
        notificarObservadores();
    }
    
    public void moverEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaVivo()) {
                Posicion posEnemigo = enemigo.getPosicion();
                Posicion posJugador = jugador.getPosicion();
                
                int dx = 0, dy = 0;
                
                if (enemigo.puedePercibir(posJugador, posEnemigo)) {
                    if (posJugador.getX() > posEnemigo.getX()) dx = 1;
                    else if (posJugador.getX() < posEnemigo.getX()) dx = -1;
                    
                    if (posJugador.getY() > posEnemigo.getY()) dy = 1;
                    else if (posJugador.getY() < posEnemigo.getY()) dy = -1;
                } else {
                    Random rand = new Random();
                    dx = rand.nextInt(3) - 1;
                    dy = rand.nextInt(3) - 1;
                }
                
                int nuevoX = posEnemigo.getX() + dx;
                int nuevoY = posEnemigo.getY() + dy;
                
                if (esMovimientoValido(nuevoX, nuevoY)) {
                    Personaje objetivo = obtenerPersonajeEn(nuevoX, nuevoY);
                    if (objetivo == null) {
                        enemigo.setPosicion(nuevoX, nuevoY);
                    } else if (objetivo == jugador) {
                        atacar(enemigo, jugador);
                    }
                }
            }
        }
        notificarObservadores();
    }
    
    private boolean esMovimientoValido(int x, int y) {
        return x >= 0 && x < mapa.length && 
               y >= 0 && y < mapa[0].length && 
               mapa[x][y] != Casilla.PARED;
    }
    
    private Personaje obtenerPersonajeEn(int x, int y) {
        if (jugador.getPosicion().equals(x, y)) return jugador;
        
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getPosicion().equals(x, y) && enemigo.estaVivo()) {
                return enemigo;
            }
        }
        return null;
    }
    
    private void atacar(Personaje atacante, Personaje defensor) {
        int danio = Math.max(0, atacante.getAtaque() - defensor.getDefensa());
        defensor.recibirDanio(danio);
        
        if (!defensor.estaVivo()) {
            if (defensor == jugador) {
                juegoTerminado = true;
            } else {
                enemigos.remove(defensor);
                if (enemigos.isEmpty()) {
                    victoria = true;
                }
            }
        }
    }
    
    private void determinarOrdenTurnos() {
        ordenTurnos = new ArrayList<>();
        ordenTurnos.add(jugador);
        ordenTurnos.addAll(enemigos);
        ordenTurnos.sort((p1, p2) -> p2.getVelocidad() - p1.getVelocidad());
    }
    
    public void siguienteTurno() {
        moverEnemigos();
        determinarOrdenTurnos();
    }
    
    // Getters
    public Casilla[][] getMapa() { return mapa; }
    public Jugador getJugador() { return jugador; }
    public List<Enemigo> getEnemigos() { return enemigos; }
    public List<Personaje> getOrdenTurnos() { return ordenTurnos; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isVictoria() { return victoria; }
    public int getAnchoMapa() { return mapa.length; }
    public int getAltoMapa() { return mapa[0].length; }
}