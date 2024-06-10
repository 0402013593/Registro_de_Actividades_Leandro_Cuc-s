package com.example.myapplication;

public class DatosEntrevistas {
    String Fecha;
    String Crono;
    String Hora;
    String Latitud;
    String Longitud;
    String AreaDeTrabajo;
    String Nombre;
    String Edad;
    String Telefono;
    String Correo;
    String Tiempo;
    String Situacion;
    String Nivel;
    String Horas;
    String Idioma;
    String Habilidades;
    String Notas;

    public DatosEntrevistas(String Fecha, String Crono, String Hora, String Latitud, String Longitud, String AreaDeTrabajo, String Nombre, String Edad, String Telefono, String Correo, String Tiempo, String Situacion, String Nivel, String Horas, String Idioma, String Habilidades, String Notas) {
        this.Fecha = Fecha;
        this.Crono = Crono;
        this.Hora = Hora;
        this.Latitud = Latitud;
        this.Longitud = Longitud;
        this.AreaDeTrabajo = AreaDeTrabajo;
        this.Nombre = Nombre;
        this.Edad = Edad;
        this.Telefono = Telefono;
        this.Correo = Correo;
        this.Tiempo = Tiempo;
        this.Situacion = Situacion;
        this.Nivel = Nivel;
        this.Horas = Horas;
        this.Idioma = Idioma;
        this.Habilidades = Habilidades;
        this.Notas = Notas;
    }

    public String getFecha() {
        return Fecha;
    }
    public  String getCrono() {return  Crono;}

    public String getHora() {
        return Hora;
    }

    public String getLatitud() {
        return Latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public String getAreaDeTrabajo() {
        return AreaDeTrabajo;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getEdad() {
        return Edad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public String getSituacion() {
        return Situacion;
    }

    public String getNivel() {
        return Nivel;
    }

    public String getHoras() {
        return Horas;
    }

    public String getIdioma() {
        return Idioma;
    }

    public String getHabilidades() {
        return Habilidades;
    }

    public String getNotas() {
        return Notas;
    }
}