package com.example.oaxacacomercio.Modelos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static java.lang.String.valueOf;

public class User {
    public int id;
    public String nombre, apellido_paterno, apellido_materno,correoelectronico,cargo,municipio,image;
    public Integer adminsecre;
    SharedPreferences sharedPreferences;
    Context context;

    public String getImage() {
    //    image=sharedPreferences.getString("userfotop","");
        return image;
    }

    public void setImage(String image) {
      //  sharedPreferences.edit().putString("userfotop",image).commit();
        this.image = image;
    }

    public User(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }
    public void removeuser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getCorreoelectronico() {
        correoelectronico=sharedPreferences.getString("userdata","");
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
        sharedPreferences.edit().putString("userdata",correoelectronico).commit();
    }

    public String getMunicipio() {
        municipio=sharedPreferences.getString("usermun","");
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
        sharedPreferences.edit().putString("usermun",municipio).commit();
    }

    public String getCargo() {
        cargo=sharedPreferences.getString("usercar","");
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
        sharedPreferences.edit().putString("usercar",cargo).commit();
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
        nombre=sharedPreferences.getString("usernom","");
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        sharedPreferences.edit().putString("usernom",nombre).commit();
    }

    public String getApellido_paterno() {
        apellido_paterno=sharedPreferences.getString("userap","");
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
        sharedPreferences.edit().putString("userap",apellido_paterno).commit();
    }

    public String getApellido_materno() {
        apellido_materno=sharedPreferences.getString("useram","");
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
        sharedPreferences.edit().putString("useram",apellido_materno).commit();
    }

    public Integer getAdminsecre() {
        adminsecre=sharedPreferences.getInt("useradmsecre",0);
        return adminsecre;
    }

    public void setAdminsecre(Integer adminsecre) {
        this.adminsecre = adminsecre;
        sharedPreferences.edit().putInt("useradmsecre",adminsecre).commit();
    }
}
