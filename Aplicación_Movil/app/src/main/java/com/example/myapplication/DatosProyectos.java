package com.example.myapplication;

public class DatosProyectos {
    String Fecha,Crono,Hora,Latitud,Longitud,Area,Jefe,NombreProyecto,Actividad,FechaIn,Costo,Notas;

    public DatosProyectos(String fecha,String crono, String hora, String latitud, String longitud, String area, String jefe, String nombreProyecto, String actividad, String fechaIn, String costo, String notas) {
        Fecha = fecha;
        Crono = crono;
        Hora = hora;
        Latitud = latitud;
        Longitud = longitud;
        Area = area;
        Jefe = jefe;
        NombreProyecto = nombreProyecto;
        Actividad = actividad;
        FechaIn = fechaIn;
        Costo = costo;
        Notas = notas;
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

    public String getArea() {
        return Area;
    }

    public String getJefe() {
        return Jefe;
    }

    public String getNombreProyecto() {
        return NombreProyecto;
    }

    public String getActividad() {
        return Actividad;
    }

    public String getFechaIn() {
        return FechaIn;
    }

    public String getCosto() {
        return Costo;
    }

    public String getNotas() {
        return Notas;
    }



    }
