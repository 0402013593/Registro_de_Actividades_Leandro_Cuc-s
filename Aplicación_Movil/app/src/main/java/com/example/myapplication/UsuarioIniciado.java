package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UsuarioIniciado extends AppCompatActivity {



    FirebaseAuth mAuth;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_iniciado);

        mAuth = FirebaseAuth.getInstance();


    }


    public void onBackPressed() {
        // Si el usuario ha iniciado sesión, no permitas volver a la actividad de inicio de sesión.

        if (mAuth.getCurrentUser()!=null) {
            moveTaskToBack(true);  // Esto minimiza la aplicación en lugar de volver a la actividad anterior.
        } else {
            super.onBackPressed(); // Permite retroceder en otras circunstancias.
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    public void FinSecion(View view){

        mAuth.signOut();

        if(mAuth.getCurrentUser()==null) {


            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);


        }

    }





    public void irReuniones(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, Reuniones.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }





    public void irproyectos(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, Gproyectos.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }


    public void irEntrevistas(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this,Entrevistas.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }

    public void irConferencias(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, Conferencias.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }


    public void irInvestigacion(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, Investigacion.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }


}