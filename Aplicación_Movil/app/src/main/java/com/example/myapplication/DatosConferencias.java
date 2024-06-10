package com.example.myapplication;

public class DatosConferencias {

    String Fecha, Crono, Hora, Latitud, Longitud, NombreDelEvento, NombreDelPonente, Tematica, DuracionPonencia, Notas;

    public DatosConferencias(String fecha, String crono, String hora, String latitud, String longitud, String nombreDelEvento, String nombreDelPonente, String tematica, String duracionPonencia, String notas) {
        Fecha = fecha;
        Crono = crono;
        Hora = hora;
        Latitud = latitud;
        Longitud = longitud;
        NombreDelEvento = nombreDelEvento;
        NombreDelPonente = nombreDelPonente;
        Tematica = tematica;
        DuracionPonencia = duracionPonencia;
        Notas = notas;
    }

    public String getFecha() {
        return Fecha;
    }

    public  String getCrono(){return  Crono;}

    public String getHora() {
        return Hora;
    }

    public String getLatitud() {
        return Latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public String getNombreDelEvento() {
        return NombreDelEvento;
    }

    public String getNombreDelPonente() {
        return NombreDelPonente;
    }

    public String getTematica() {
        return Tematica;
    }

    public String getDuracionPonencia() {
        return DuracionPonencia;
    }

    public String getNotas() {
        return Notas;
    }
}


