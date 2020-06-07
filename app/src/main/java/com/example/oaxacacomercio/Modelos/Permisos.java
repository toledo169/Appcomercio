package com.example.oaxacacomercio.Modelos;

import java.sql.Time;

public class Permisos {
public String giro;
public Time horainicio,horafin;
public Double latitud,longitud;
public Double latitudfinal, longitudfinal;

    public Double getLatitudfinal() {
        return latitudfinal;
    }

    public void setLatitudfinal(Double latitudfinal) {
        this.latitudfinal = latitudfinal;
    }

    public Double getLongitudfinal() {
        return longitudfinal;
    }

    public void setLongitudfinal(Double longitudfinal) {
        this.longitudfinal = longitudfinal;
    }
//latitudfin,longitudfin;

    public Time getHorafin() {
        return horafin;
    }

    public void setHorafin(Time horafin) {
        this.horafin = horafin;
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

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public Time getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Time horainicio) {
        this.horainicio = horainicio;
    }
}
