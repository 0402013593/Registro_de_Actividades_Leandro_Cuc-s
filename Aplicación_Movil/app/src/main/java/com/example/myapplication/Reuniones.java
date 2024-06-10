package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Reuniones extends AppCompatActivity {

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
    private TextView vFecha, vHora, vlatitud, vlongitud, vintegrantes, vPpresente, vpAusentes, vTematica, vResumen, vNota;


    Button btn_guardar, btn_cancelar;

    private DatabaseReference Asuntos;
    FirebaseAuth mAuth;

    Date date = new Date();

    private FusedLocationProviderClient Ubicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniones);


        // Buscar el TextView del cronómetro por su ID
        textViewChronometer = findViewById(R.id.cronometro);
        startTime = System.currentTimeMillis(); // Iniciar el cronómetro automáticamente al abrir el Activity
        handler.postDelayed(updateTimeThread, 0); // Iniciar el hilo para actualizar el cronómetro



        Asuntos = FirebaseDatabase.getInstance().getReference("Reuniones");

        mAuth = FirebaseAuth.getInstance();

        vFecha = (TextView) findViewById(R.id.txt_fecha);
        SimpleDateFormat fechaC = new SimpleDateFormat("d,MMMM 'del', yyyy");
        String sFecha = fechaC.format(date);
        vFecha.setText(sFecha);

        vHora = (TextView) findViewById(R.id.txt_hora);
        SimpleDateFormat hor = new SimpleDateFormat("h:mm a");
        String shora = hor.format(date);
        vHora.setText(shora);

        vlatitud = (TextView) findViewById(R.id.txt_latitud);
        vlongitud = (TextView) findViewById(R.id.txt_longitud);

        Ubicacion = LocationServices.getFusedLocationProviderClient(Reuniones.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Ubicacion.getLastLocation().addOnSuccessListener(Reuniones.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Double latitud = location.getLatitude();
                    Double longitud = location.getLongitude();
                    vlongitud.setText(String.valueOf(longitud));
                    vlatitud.setText(String.valueOf(latitud));

                }


            }
        });

        vintegrantes = (EditText) findViewById(R.id.txt_Nintegrantes);
        vPpresente = (EditText) findViewById(R.id.etm_Presentes);
        vpAusentes = (EditText) findViewById(R.id.etm_ausentes);
        vTematica = (EditText) findViewById(R.id.txt_tema);
        vResumen = (EditText) findViewById(R.id.etm_resumen);
        vNota = (EditText) findViewById(R.id.etm_NotasComen);


        btn_guardar = (Button) findViewById(R.id.btn_guardar);


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



    }




    public void registrar(){


        String Fecha = vFecha.getText().toString();
        String Crono = textViewChronometer.getText().toString();
        String Hora = vHora.getText().toString();
        String Latitud = vlatitud.getText().toString();
        String Longitud = vlongitud.getText().toString();
        String Integrantes = vintegrantes.getText().toString();
        String PersonasPresentes= vPpresente.getText().toString();
        String PersonasAusentes = vpAusentes.getText().toString();
        String Tematica = vTematica.getText().toString();
        String Resumen = vResumen.getText().toString();
        String Notas = vNota.getText().toString();

        String id = mAuth.getCurrentUser().getUid();

        String nuevoId = Asuntos.push().getKey(); // Genera un ID único para el nuevo registro

        DatosReuniones Datos = new DatosReuniones(Fecha, Crono ,Hora,Latitud,Longitud,Integrantes,PersonasPresentes,PersonasAusentes,Tematica,Resumen,Notas);
        Asuntos.child(id).child(nuevoId).setValue(Datos);



    }


}