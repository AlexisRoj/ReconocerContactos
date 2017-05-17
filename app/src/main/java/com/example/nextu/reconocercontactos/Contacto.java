package com.example.nextu.reconocercontactos;

/**
 * Clase encargada de extraer los elementos del dispositivo
 */
public class Contacto {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String  email;
    private String  nombre;
    private String  numero;

    public Contacto( String nombre, String numero,String email) {
        this.email = email;
        this.nombre = nombre;
        this.numero = numero;
    }
}
