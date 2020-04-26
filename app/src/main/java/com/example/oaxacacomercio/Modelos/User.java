package com.example.oaxacacomercio.Modelos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static java.lang.String.valueOf;

public class User {
    public int id;
    public String nombre, apellido_paterno, apellido_materno,correoelectronico,cargo,municipio;
    SharedPreferences sharedPreferences;
    Context context;

    public User(Context context) {
        this.context = context;
        //sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }
   // public void remove(){
     //   sharedPreferences.edit().clear().commit();
   // }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getId() {
     //   String name = sharedPreferences.getString("userdata","");
      //  id=Integer.parseInt(name);
        return id;
    }

    public void setId(int id) {

        this.id = id;
        //sharedPreferences.edit().putString("userdata", valueOf(id)).commit();
    }

    public String getNombre() {
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
