package com.cristian.proyecto_firebase.model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cristian.proyecto_firebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

/**
 * Created by Domiciano on 01/05/2018.
 */

/*
    PARA USAR ESTE ADAPTADOR DEBE TENER EL ARCHIVO renglon.xml EN LA CARPETA LAYOUT
    DEBE TAMBIÉN USAR
*/
public class Adaptador extends BaseAdapter{
    ArrayList<Comentario> publicaciones;
    Context context;

    public Adaptador(Context context){
        this.context = context;
        publicaciones = new ArrayList<>();

    }

    @Override
    public int getCount() {
        return publicaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return publicaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View renglon = inflater.inflate(R.layout.renglon,null);
        TextView tv_comentario = renglon.findViewById(R.id.tv_comentario);
        Button like_btn = renglon.findViewById(R.id.like_btn);
        tv_comentario.setText(publicaciones.get(position).getTexto());


        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                db.getReference().child("comentarios").child(publicaciones.get(position).getId()).child("likes").push().setValue("L");
            }
        });


        return renglon;
    }

    public void clear() {
        publicaciones.clear();
    }


    public void addItem(Comentario p) {
        publicaciones.add(p);
    }

    public void agregarComentario(Comentario c) {

        publicaciones.add(c);
        notifyDataSetChanged();
    }

    public void refrescarComentario(Comentario c) {

        int index = publicaciones.indexOf(c);
        Comentario viejo = publicaciones.get(index);
        viejo.setLikes(c.getLikes());
        notifyDataSetChanged();

    }
}
