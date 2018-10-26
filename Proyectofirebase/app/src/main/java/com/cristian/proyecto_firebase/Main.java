package com.cristian.proyecto_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cristian.proyecto_firebase.model.Adaptador;
import com.cristian.proyecto_firebase.model.Comentario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 100 ;
    private FirebaseAuth auth;
   private Button btnComentar;
   private TextView txtcomentario;
   private ListView listaComentarios;
   private Adaptador adaptador;
   private FirebaseDatabase db;


   Button btn_agregarfoto;
   ImageView img_foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser()==null){

            Intent i = new Intent(this,Login.class);
            startActivity(i);
            finish();
        }

        btn_agregarfoto = findViewById(R.id.btn_agregarfoto);
        img_foto = findViewById(R.id.img_foto);

        txtcomentario = findViewById(R.id.txtComentario);
        btnComentar = findViewById(R.id.btnComentario);
        listaComentarios = findViewById(R.id.listaComentarios);
        adaptador = new Adaptador(this);
        listaComentarios.setAdapter(adaptador);

        DatabaseReference comentarios_ref =db.getReference().child("comentarios");

        // lo vamos a usar cada vez que se agrega un comentario
        comentarios_ref.addChildEventListener(new ChildEventListener() {
            //Acá se va a cargar la informacion nueva
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Comentario c = dataSnapshot.getValue(Comentario.class);
            adaptador.agregarComentario(c);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Comentario c = dataSnapshot.getValue(Comentario.class);

                adaptador.refrescarComentario(c);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = txtcomentario.getText().toString();

               if(comentario.isEmpty())return;

                DatabaseReference reference = db.getReference().child("comentarios").push();
                //Identificador de comentario
                String id_comment = reference.getKey();
                //Con el identificador de comentario podemos agregar un comentario
                Comentario c = new Comentario();
                c.setId(id_comment);
                c.setTexto(comentario);
                reference.setValue(c);

            }
        });



        btn_agregarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                //Se queda vació para agregar acciones
                i.setType("image/*");
                //i.setType("video/*");
                //i.setType("*/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,REQUEST_GALLERY);
            }
        });





    }
//ESto se ejecuta después de abrir la galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_GALLERY && resultCode ==RESULT_OK)
        //ACCIONES QUE HAREMOS EN 15 DIAS
    }
}
