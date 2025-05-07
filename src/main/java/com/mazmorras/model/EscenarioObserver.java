package com.mazmorras.model;

public interface EscenarioObserver {
    void onEscenarioCambiado(Escenario escenario);
    default void onPersonajeEliminado(Personaje personaje) {}
}