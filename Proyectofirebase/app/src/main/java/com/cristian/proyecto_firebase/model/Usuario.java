package com.cristian.proyecto_firebase.model;

public class Usuario {



    private String uid;
    private String nombre;
    private String email;
    private String edad;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }


    public Usuario(String uid, String nombre, String email, String edad) {
        this.uid = uid;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    public Usuario() {
    }
}
