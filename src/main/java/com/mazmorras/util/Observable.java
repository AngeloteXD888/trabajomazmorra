package com.mazmorras.util;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observador> observadores = new ArrayList<>();
    
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }
    
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }
    
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.update(this);
        }
    }
}