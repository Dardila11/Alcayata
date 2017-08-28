package com.senamoviles.alcayata.alcayata;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.senamoviles.alcayata.alcayata.LogicaLista.Paso;
import com.senamoviles.alcayata.alcayata.LogicaLista.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Paso> pasos;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Virgen de los Dolores");

        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        iniciarlizarDatos();
        inicializarAdaptador();

    }

    private void iniciarlizarDatos(){
        pasos = new ArrayList<>();
        pasos.add(new Paso("San Juan Evangelista", R.drawable.juan,"descripcion","sabias que",""));
        pasos.add(new Paso("El Señor del huerto", R.drawable.huerto,"descripcion", "sabias que",""));
        pasos.add(new Paso("El Crucifijo", R.drawable.crucifijo,"descripcion","sabias que",""));
        pasos.add(new Paso("Virgen de los Dolores", R.drawable.virgen,"descripcion","sabias que",""));

    }
    private void inicializarAdaptador(){
        RVAdapter adapter = new RVAdapter(pasos);
        rv.setAdapter(adapter);
    }
}
