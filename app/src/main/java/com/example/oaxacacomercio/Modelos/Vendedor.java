package com.example.oaxacacomercio.Modelos;

public class Vendedor {
    public Integer id;
    public String nombre, apellido_paterno, apellido_materno,nomorganizacion,actividad,giro,nomzona;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomorganizacion() {
        return nomorganizacion;
    }

    public void setNomorganizacion(String nomorganizacion) {
        this.nomorganizacion = nomorganizacion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getNomzona() {
        return nomzona;
    }

    public void setNomzona(String nomzona) {
        this.nomzona = nomzona;
    }

    public String getNombrev() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }
}
