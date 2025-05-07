package com.mazmorras.model;

public class Celda implements Cloneable{
    private boolean pared;
    private Personaje personaje;
    
    public Celda(boolean esPared) {
        this.pared = esPared;
        this.personaje = null;
    }
    
    // Métodos básicos
    public boolean esPared() {
        return pared;
    }
    
    public void setPared(boolean pared) {
        this.pared = pared;
    }
    
    public Personaje getPersonaje() {
        return personaje;
    }
    
    public boolean tienePersonaje() {
        return personaje != null;
    }
    
    // Métodos para manejo de personajes
    public boolean agregarPersonaje(Personaje personaje) {
        if (this.pared || this.personaje != null) {
            return false; // No se puede agregar a una pared o celda ocupada
        }
        this.personaje = personaje;
        return true;
    }
    
    public Personaje removerPersonaje() {
        Personaje temp = this.personaje;
        this.personaje = null;
        return temp;
    }
    
    // Método para clonación (útil para getMapa())
    @Override
    public Celda clone() {
        try {
            Celda cloned = (Celda) super.clone();
            // Clonación profunda del personaje si existe
            if (this.personaje != null) {
                cloned.personaje = this.personaje.clone(); // Asegúrate que Personaje también implemente Cloneable
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La clase Celda debería ser Cloneable", e);
        }
    }
}