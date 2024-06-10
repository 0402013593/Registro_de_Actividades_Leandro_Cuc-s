package com.example.myapplication;

public class DatosInvestigacion {

    String Fecha;
    String Crono;
    String Hora;
    String Latitud;
    String Longitud;
    String Titulo;
    String Area;
    String Nombre;
    String Avance;
    String Resultados;
    String Presupuesto;
    String Recordatorio;

    public DatosInvestigacion(String fecha, String crono,String hora, String latitud, String longitud, String titulo, String area, String nombre, String avance, String resultados, String presupuesto, String recordatorio) {
        Fecha = fecha;
        Crono =crono;
        Hora = hora;
        Latitud = latitud;
        Longitud = longitud;
        Titulo = titulo;
        Area = area;
        Nombre = nombre;
        Avance = avance;
        Resultados = resultados;
        Presupuesto = presupuesto;
        Recordatorio = recordatorio;
    }

    public String getFecha() {
        return Fecha;
    }
    public  String getCrono() {return Crono;}

    public String getHora() {
        return Hora;
    }

    public String getLatitud() {
        return Latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getArea() {
        return Area;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getAvance() {
        return Avance;
    }

    public String getResultados() {
        return Resultados;
    }

    public String getPresupuesto() {
        return Presupuesto;
    }

    public String getRecordatorio() {
        return Recordatorio;
    }


}
