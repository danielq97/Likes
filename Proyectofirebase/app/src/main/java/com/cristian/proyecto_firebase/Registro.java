package com.cristian.proyecto_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristian.proyecto_firebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private FirebaseDatabase db;
    FirebaseAuth auth;

    private EditText txtnombre;
    private EditText txtedad;
    private EditText txtcorreo;
    private EditText txtcontraseña;
    private Button registrarse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtcontraseña = findViewById(R.id.contraseña);
        txtnombre = findViewById(R.id.usuario);
        txtcorreo = findViewById(R.id.correo);
        txtedad = findViewById(R.id.edad);
        registrarse = findViewById(R.id.registrarse);

        db= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            Intent i = new Intent(this,Main.class);

            startActivity(i);

            finish();

            return;
        }



        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = new Usuario("",txtnombre.getText().toString(),txtcorreo.getText().toString(),txtedad.getText().toString());

                registrarUsuario(usuario);
            }
        });



        //DatabaseReference reference= db.getReference().child("estudiantes");
        //reference.setValue("Cristian");




        }
    public void registrarUsuario(final Usuario usuario){
        if(txtcontraseña.getText().toString().length()<=5){

            Toast.makeText(this,"la contraseña esta mala pirtobo",Toast.LENGTH_SHORT).show();
        }


        auth.createUserWithEmailAndPassword(usuario.getEmail(), txtcontraseña.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Registro.this,"registro Exitoso",Toast.LENGTH_SHORT).show();
                    usuario.setUid( auth.getCurrentUser().getUid());

                    DatabaseReference reference = db.getReference().child("usuarios").child(usuario.getUid());

                    reference.setValue(usuario);
                    //aqui me voy para la otra actividad

                    Intent i = new Intent(Registro.this,Main.class);

                    startActivity(i);

                    finish();
                }
            }
        });

    }
}
