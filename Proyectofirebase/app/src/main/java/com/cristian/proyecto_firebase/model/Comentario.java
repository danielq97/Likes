package com.cristian.proyecto_firebase.model;

import java.util.HashMap;

public class Comentario {


    private String id;
    private String texto;
    private HashMap<String, String> likes;

    public Comentario (){

    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Comentario){
          Comentario c=   (Comentario) obj;
          return  this.getId().equals(c.getId());
        }

        return false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setLikes(HashMap<String, String> likes) {
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public HashMap<String, String> getLikes() {
        return likes;
    }
}
