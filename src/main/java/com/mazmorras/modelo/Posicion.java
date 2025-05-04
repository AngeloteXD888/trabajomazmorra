package com.mazmorras.modelo;


public class Posicion {
    private int x;
    private int y;
    
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }
}