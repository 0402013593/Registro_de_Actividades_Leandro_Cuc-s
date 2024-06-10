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

public class MainActivity extends AppCompatActivity {


    private EditText correo;
    private EditText contrasena;

    private FirebaseAuth mAuth;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);

        mAuth = FirebaseAuth.getInstance();

    }

    public void onBackPressed() {
        // Si el usuario ha iniciado sesión, no permitas volver a la actividad de inicio de sesión.

        if (mAuth.getCurrentUser()==null) {
            moveTaskToBack(true);  // Esto minimiza la aplicación en lugar de volver a la actividad anterior.
        } else {
            super.onBackPressed(); // Permite retroceder en otras circunstancias.
            Intent i = new Intent(this, UsuarioIniciado.class);
            startActivity(i);

        }
    }

    public boolean validar (){

        boolean retorno = true;
        String c1 = correo.getText().toString();
        String c2 = contrasena.getText().toString();

        if (c1.isEmpty()){
            correo.setError("Campo 1 sin llenar");
            retorno = false;
        }
        if (c2.isEmpty()){
            contrasena.setError("Campo 2 sin llenar");
            retorno = false;
        }
        return retorno;
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void iniciarSecion (View view){


        if (validar()){
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

                mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(), contrasena.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //updateUI(user);


                                    Intent i = new Intent(getApplicationContext(), UsuarioIniciado.class);
                                    startActivity(i);

                                    Toast.makeText(getApplicationContext(), "Bienvenido.",
                                            Toast.LENGTH_SHORT).show();


                                } else {


                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "El usuario o la contraseña es incorrecta.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);

                                }
                            }
                        });
            }else{

                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},1);
            }


        }






    }




    public void irRegistro(View view){

        Intent i = new Intent(this,Registrar.class);
        startActivity(i);


    }






}