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

public class Gproyectos extends AppCompatActivity {

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
    private EditText areap, jefep,nombrep, actividadp, fechainp, costop, notasp;
    private TextView pFecha, pHora, plongitud, platitud;
    private FusedLocationProviderClient Ubicacion;

    Button btn_guardarpro, btn_cancelar;

    private DatabaseReference Proyectos;
    FirebaseAuth mAuth;
    Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gproyectos);

        // Buscar el TextView del cronómetro por su ID
        textViewChronometer = findViewById(R.id.cronometro);
        startTime = System.currentTimeMillis(); // Iniciar el cronómetro automáticamente al abrir el Activity
        handler.postDelayed(updateTimeThread, 0); // Iniciar el hilo para actualizar el cronómetro


        Proyectos = FirebaseDatabase.getInstance().getReference("Gestion de proyectos");

        mAuth = FirebaseAuth.getInstance();

        pFecha = (TextView) findViewById(R.id.txt_fecha);
        SimpleDateFormat fechaC = new SimpleDateFormat("d,MMMM 'del', yyyy");
        String sFecha = fechaC.format(date);
        pFecha.setText(sFecha);

        pHora = (TextView) findViewById(R.id.txt_hora);
        SimpleDateFormat hor = new SimpleDateFormat("h:mm a");
        String shora = hor.format(date);
        pHora.setText(shora);

        platitud = (TextView) findViewById(R.id.txt_latitud);
        plongitud = (TextView) findViewById(R.id.txt_longitud);

        Ubicacion = LocationServices.getFusedLocationProviderClient(Gproyectos.this);

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
        Ubicacion.getLastLocation().addOnSuccessListener(Gproyectos.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Double latitud = location.getLatitude();
                    Double longitud = location.getLongitude();
                    plongitud.setText(String.valueOf(longitud));
                    platitud.setText(String.valueOf(latitud));
                }
            }
        });


        areap =(EditText) findViewById(R.id.txt_Area);
        jefep =(EditText) findViewById(R.id.txt_Jefe);
        nombrep =(EditText) findViewById(R.id.txt_Nombrepro);
        actividadp = (EditText) findViewById(R.id.txt_Actividad);
        fechainp =(EditText) findViewById(R.id.txt_Fechain);
        costop =(EditText) findViewById(R.id.txt_Costo);
        notasp =(EditText) findViewById(R.id.edt_notarec);



        btn_guardarpro =(Button) findViewById(R.id.btn_guardarpro);


        btn_guardarpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {registrarpro();

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

    public void registrarpro(){

        String Fecha = pFecha.getText().toString();
        String Crono = textViewChronometer.getText().toString();
        String Hora = pHora.getText().toString();
        String Latitud = platitud.getText().toString();
        String Longitud = plongitud.getText().toString();
        String Area = areap.getText().toString();
        String Jefe= jefep.getText().toString();
        String NombreProyecto = nombrep.getText().toString();
        String Actividad = actividadp.getText().toString();
        String FechaIn = fechainp.getText().toString();
        String Costo = costop.getText().toString();
        String Notas = notasp.getText().toString();

        String id = mAuth.getCurrentUser().getUid();

        String nuevoId = Proyectos.push().getKey(); // Genera un ID único para el nuevo registro

        DatosProyectos Datos = new DatosProyectos(Fecha,Crono,Hora,Latitud,Longitud,Area,Jefe,NombreProyecto,Actividad,
                FechaIn,Costo,Notas);
        Proyectos.child(id).child(nuevoId).setValue(Datos);

    }


}