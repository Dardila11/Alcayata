package com.senamoviles.alcayata.alcayata.LogicaLista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.R;

public class CardViewActivity extends AppCompatActivity {

    TextView paso_nombre;
    ImageView paso_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        paso_nombre = (TextView) findViewById(R.id.paso_nombre);
        paso_foto = (ImageView) findViewById(R.id.paso_foto);
    }
}
