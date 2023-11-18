package com.example.lab7_20203554.entity;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String rol;
    private String estado;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
