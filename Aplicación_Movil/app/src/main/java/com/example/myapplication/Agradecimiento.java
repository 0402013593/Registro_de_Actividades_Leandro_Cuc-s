package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Agradecimiento extends AppCompatActivity {





    private DatabaseReference Asuntos;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agradecimiento);



        mAuth = FirebaseAuth.getInstance();


    }

    public void onBackPressed() {
        // Si el usuario ha iniciado sesión, no permitas volver a la actividad de inicio de sesión.

        if (mAuth.getCurrentUser()!=null) {

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


    public void irUsuIni(View view){

        if(mAuth.getCurrentUser()!=null) {

            Intent i = new Intent(this, UsuarioIniciado.class);
            startActivity(i);


        }else{

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }

}