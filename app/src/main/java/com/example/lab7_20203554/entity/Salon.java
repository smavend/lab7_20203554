package com.example.lab7_20203554.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Salon implements Serializable {
    private String nombre;
    private ArrayList<String> servicios;

    public ArrayList<String> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<String> servicios) {
        this.servicios = servicios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
