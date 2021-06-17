package com.proyecto.parcial3;

public class Datos {

    String Nombre;
    String Puntaje;

    private Datos(){}

    private Datos(String Nombre, String Puntaje){
        this.Nombre = Nombre;
        this.Puntaje = Puntaje;

    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPuntaje() {
        return Puntaje;
    }

    public void setPuntaje(String puntaje) {
        Puntaje = puntaje;
    }


}
