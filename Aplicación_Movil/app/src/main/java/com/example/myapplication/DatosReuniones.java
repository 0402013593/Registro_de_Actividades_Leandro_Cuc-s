package com.example.myapplication;

public class DatosReuniones {



    String Fecha;
    String Crono;
    String Hora;
    String Latitud;
    String Longitud;
    String Integrantes;
    String PersonasPresentes;
    String PersonasAusentes;
    String Tematica;
    String Resumen;
    String Notas;


    public DatosReuniones(String Fecha, String Crono,String Hora,String Latitud,String Longitud,String Integrantes,String PersonasPresentes,String PersonasAusentes,String Tematica,String Resumen,String Notas) {
        this.Fecha = Fecha;
        this.Crono = Crono;
        this.Hora = Hora;
        this.Latitud = Latitud;
        this.Longitud = Longitud;
        this.Integrantes = Integrantes;
        this.PersonasPresentes = PersonasPresentes;
        this.PersonasAusentes = PersonasAusentes;
        this.Tematica = Tematica;
        this.Resumen = Resumen;
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
    public String getIntegrantes() {
        return Integrantes;
    }

    public String getPersonasPresentes() {
        return PersonasPresentes;
    }

    public String getPersonasAusentes() {
        return PersonasAusentes;
    }

    public String getTematica() {
        return Tematica;
    }

    public String getResumen() {
        return Resumen;
    }
    public String getNotas() {
        return Notas;
    }


}
