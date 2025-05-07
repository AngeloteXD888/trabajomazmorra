package com.mazmorras.modelo;


import java.util.ArrayList;
import java.util.List;

public class Enemigo extends Personaje {
    private int experiencia;
    private String imagen;
    private String descripcion;
    private List<String> habilidades;

    public Enemigo() {
        super(); 
        this.habilidades = new ArrayList<>();
    }

    // Constructor con parámetros básicos (debe incluir los de Personaje)
    public Enemigo(String nombre, int saludMaxima, int ataque, int defensa, int velocidad) {
        super(); 
        this.habilidades = new ArrayList<>();
    }

    // Constructor completo
    public Enemigo(String nombre, int saludMaxima, int ataque, int defensa, int velocidad,
                   int experiencia, String imagen, String descripcion, List<String> habilidades) {
        super(nombre, saludMaxima, ataque, defensa, velocidad, experiencia); // Atributos de Personaje
        this.experiencia = experiencia;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.habilidades = (habilidades != null) ? new ArrayList<>(habilidades) : new ArrayList<>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSaludMaxima() {
        return saludMaxima;
    }

    public void setSaludMaxima(int saludMaxima) {
        this.saludMaxima = saludMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    // Métodos útiles para habilidades
    public void agregarHabilidad(String habilidad) {
        this.habilidades.add(habilidad);
    }

    public boolean tieneHabilidad(String habilidad) {
        return habilidades.contains(habilidad);
    }
}