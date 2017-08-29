package com.senamoviles.alcayata.alcayata.LogicaLista;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senamoviles.alcayata.alcayata.InfoActivity;
import com.senamoviles.alcayata.alcayata.R;
import com.senamoviles.alcayata.alcayata.SwipeView.FragmentoHuerto;
import com.senamoviles.alcayata.alcayata.SwipeView.SwipePasos;

import java.util.List;

/**
 * Created by macbook on 8/25/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PasoViewHolder> {


    List<Paso> pasos;

    public RVAdapter(List<Paso> pasos ){
        this.pasos = pasos;
    }

    public static class PasoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        Intent intent;
        CardView cv;
        TextView paso_nombre;
        TextView paso_desc;
        TextView paso_sabias;
        ImageView paso_foto;
        Context context;




        PasoViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            paso_nombre = (TextView)itemView.findViewById(R.id.paso_nombre);
            paso_foto = (ImageView)itemView.findViewById(R.id.paso_foto);

            itemView.setOnClickListener(this);
            context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (getAdapterPosition()){
                        case 0:
                            //Toast.makeText(itemView.getContext(),"Has clickeado en la posición " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            intent = new Intent(context, SwipePasos.class);
                            intent.putExtra("paso","huerto");

                            break;
                        case 1:
                            //Toast.makeText(itemView.getContext(),"Has clickeado en la posición " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            intent = new Intent(context, SwipePasos.class);
                            intent.putExtra("paso","crucifijo");
                            break;
                        case 2:
                            //Toast.makeText(itemView.getContext(),"Has clickeado en la posición " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            intent = new Intent(context, SwipePasos.class);
                            intent.putExtra("paso","evangelista");
                            break;
                        case 3:
                            //Toast.makeText(itemView.getContext(),"Has clickeado en la posición " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            intent = new Intent(context, SwipePasos.class);
                            intent.putExtra("paso","dolores");
                            break;
                    }
                    context.startActivity(intent);


                }

            });
        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public RVAdapter.PasoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent, false);
        PasoViewHolder ivh = new PasoViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.PasoViewHolder holder, int position) {
        holder.paso_nombre.setText(pasos.get(position).nombre);
        holder.paso_foto.setImageResource(pasos.get(position).foto);

    }

    @Override
    public int getItemCount() {
        return pasos.size();
    }

}
