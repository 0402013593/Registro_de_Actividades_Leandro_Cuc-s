package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entrevistas extends AppCompatActivity {


    private TextView textViewChronometer;
    private long startTime = 0L;
    private Handler handler = new Handler();
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private Runnable updateTimeThread = new Runnable() {
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updatedTime % 1000);
            textViewChronometer.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };


    private FusedLocationProviderClient fusedLocationClient;
    private TextView eFecha, eHora, elatitud, elongitud;
    private EditText Areavacan,nombre, edad, telefono, correo, situacion, nivel, horas, idioma, habilidades, notas;
    private Spinner spn_tiempo;
    Date date = new Date();
    private FusedLocationProviderClient Ubicacion;

    Button btn_guardar, btn_cancelar;
    private DatabaseReference Asuntos;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrevistas);


        // Buscar el TextView del cronómetro por su ID
        textViewChronometer = findViewById(R.id.cronometro);
        startTime = System.currentTimeMillis(); // Iniciar el cronómetro automáticamente al abrir el Activity
        handler.postDelayed(updateTimeThread, 0); // Iniciar el hilo para actualizar el cronómetro


        Asuntos = FirebaseDatabase.getInstance().getReference("Entrevistas");

        mAuth = FirebaseAuth.getInstance();

        eFecha = (TextView) findViewById(R.id.txt_fecha);
        SimpleDateFormat fechaC = new SimpleDateFormat("d,MMMM 'del', yyyy");
        String sFecha = fechaC.format(date);
        eFecha.setText(sFecha);

        eHora = (TextView) findViewById(R.id.txt_hora);
        SimpleDateFormat hor = new SimpleDateFormat("h:mm a");
        String shora = hor.format(date);
        eHora.setText(shora);

        elatitud = (TextView) findViewById(R.id.txt_latitud);
        elongitud = (TextView) findViewById(R.id.txt_longitud);

        Ubicacion = LocationServices.getFusedLocationProviderClient(Entrevistas.this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Ubicacion.getLastLocation().addOnSuccessListener(Entrevistas.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Double latitud = location.getLatitude();
                    Double longitud = location.getLongitude();
                    elongitud.setText(String.valueOf(longitud));
                    elatitud.setText(String.valueOf(latitud));

                }


            }
        });

        Areavacan = (EditText) findViewById(R.id.txt_vacante);
        nombre = (EditText) findViewById(R.id.txt_nombre);
        edad = (EditText) findViewById(R.id.txt_edad);
        telefono = (EditText) findViewById(R.id.txt_telefono);
        correo = (EditText) findViewById(R.id.txt_correo);
        spn_tiempo = (Spinner) findViewById(R.id.spinnertiempo);

        String[] opciones = {"Tiempo completo", "Medio tiempo", "Turnos especiales"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spn_tiempo.setAdapter(adapter);

        btn_guardar = (Button) findViewById(R.id.btn_guardarentre);


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();

                Intent i = new Intent(getApplicationContext(), Agradecimiento.class);
                startActivity(i);

            }

        });

        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), UsuarioIniciado.class);
                startActivity(i);
            }
        });



        situacion = (EditText) findViewById(R.id.txt_situacion);
        nivel = (EditText) findViewById(R.id.txt_nivel);
        horas = (EditText) findViewById(R.id.txt_horas);
        idioma = (EditText) findViewById(R.id.txt_idioma);
        habilidades = (EditText) findViewById(R.id.txt_otras);
        notas = (EditText) findViewById(R.id.txt_notas);



    }


    public void registrar(){


        String Fecha = eFecha.getText().toString();
        String Crono = textViewChronometer.getText().toString();
        String Hora = eHora.getText().toString();
        String Latitud = elatitud.getText().toString();
        String Longitud = elongitud.getText().toString();
        String AreaDeTrabajo = Areavacan.getText().toString();
        String Nombre = nombre.getText().toString();
        String Edad = edad.getText().toString();
        String Telefono = telefono.getText().toString();
        String Correo = correo.getText().toString();
        String Tiempo = spn_tiempo.getSelectedItem().toString();
        String Situacion = situacion.getText().toString();
        String Nivel = nivel.getText().toString();
        String Horas = horas.getText().toString();
        String Idioma = idioma.getText().toString();
        String Habilidades = habilidades.getText().toString();
        String Notas = notas.getText().toString();



        String id = mAuth.getCurrentUser().getUid();

        String nuevoId = Asuntos.push().getKey(); // Genera un ID único para el nuevo registro

        DatosEntrevistas Datos = new DatosEntrevistas(Fecha, Crono,Hora,Latitud,Longitud,AreaDeTrabajo,Nombre,Edad,Telefono,Correo,
                Tiempo,Situacion,Nivel,Horas,Idioma,Habilidades,Notas);
        Asuntos.child(id).child(nuevoId).setValue(Datos);



    }
}