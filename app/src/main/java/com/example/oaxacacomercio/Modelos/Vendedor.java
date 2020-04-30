package com.example.oaxacacomercio.Modelos;

import android.content.Context;
import android.content.SharedPreferences;

public class Vendedor {
    public Integer id;
    public String nombre, apellido_paterno, apellido_materno,nomorganizacion,actividad,giro,nomzona;
    public Integer numcuenta,numexpediente;
    public Double latitud,longitud;
    SharedPreferences sharedPreferences;
    Context context;
    public Vendedor(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("uservendedor", Context.MODE_PRIVATE);
    }
    public void removeuser(){
        sharedPreferences.edit().clear().commit();
    }

    public Integer getNumcuenta() {
           String name = sharedPreferences.getString("usernumc","");
          numcuenta=Integer.parseInt(name);
        return numcuenta;
    }

    public void setNumcuenta(Integer numcuenta) {
        this.numcuenta = numcuenta;
        sharedPreferences.edit().putString("usernumc",String.valueOf(numcuenta)).commit();
    }

    public Integer getNumexpediente() {
        String name = sharedPreferences.getString("usernumex","");
        numexpediente=Integer.parseInt(name);
        return numexpediente;
    }

    public void setNumexpediente(Integer numexpediente) {
        this.numexpediente = numexpediente;
        sharedPreferences.edit().putString("usernumex",String.valueOf(numexpediente)).commit();
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

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
        nombre=sharedPreferences.getString("usernomv","");
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
        sharedPreferences.edit().putString("usernomv",nombre).commit();
    }

    public String getApellido_paterno() {
        apellido_paterno=sharedPreferences.getString("userapv","");
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
        sharedPreferences.edit().putString("userapv",apellido_paterno).commit();
    }

    public String getApellido_materno() {
        apellido_materno=sharedPreferences.getString("useramv","");
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
        sharedPreferences.edit().putString("useramv",apellido_paterno).commit();
    }
}
