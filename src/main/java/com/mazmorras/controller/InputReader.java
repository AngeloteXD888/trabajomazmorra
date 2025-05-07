package com.mazmorras.controller;

import java.util.Scanner;

public class InputReader {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Lee un entero desde la entrada del usuario.
     * Si el usuario ingresa un valor no numérico, seguirá pidiendo un entero.
     * 
     * @return El número entero ingresado.
     */
    public static int leerEntero() {
        while (true) {
            try {
                System.out.print("Ingresa un número: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingresa un número entero.");
            }
        }
    }

    /**
     * Cierra el scanner al finalizar el programa.
     */
    public static void cerrar() {
        scanner.close();
    }
}
