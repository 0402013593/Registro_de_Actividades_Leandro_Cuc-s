package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registrar extends AppCompatActivity {




    private FirebaseAuth mAuth;

    private EditText correo;
    private EditText contrasena;
    private EditText contrasenaconfimacion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);


        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        contrasenaconfimacion = findViewById(R.id.contrasenaconfimacion);


    }


    public void iriniciar(View view){

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }

    public boolean validar (){

        boolean retorno = true;
        String c1 = correo.getText().toString();
        String c2 = contrasena.getText().toString();
        String c3 = contrasenaconfimacion.getText().toString();

        if (c1.isEmpty()){
            correo.setError("Campo 1 sin llenar");
            retorno = false;
        }
        if (c2.isEmpty()){
            contrasena.setError("Campo 2 sin llenar");
            retorno = false;
        }
        if (c3.isEmpty()){
            contrasenaconfimacion.setError("Campo 3 sin llenar");
            retorno = false;
        }
        return retorno;
    }


    public void registrarUsuarios(View view) {

        if (validar()) {
            if(ContextCompat.checkSelfPermission(Registrar.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

                if (contrasena.getText().toString().trim().equals(contrasenaconfimacion.getText().toString().trim())) {

                    mAuth.createUserWithEmailAndPassword(correo.getText().toString().trim(), contrasena.getText().toString().trim())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);

                                        Intent i = new Intent(getApplicationContext(), UsuarioIniciado.class);
                                        startActivity(i);

                                        Toast.makeText(getApplicationContext(), "El registro fue exitoso, Bienvenido.", Toast.LENGTH_SHORT).show();


                                    } else {

                                        Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();


                                    }

                                }
                            });


                } else {

                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

                }
            }else {
                ActivityCompat.requestPermissions(Registrar.this,new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},1);

            }
        }



    }




}