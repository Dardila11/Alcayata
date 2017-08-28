package com.senamoviles.alcayata.alcayata.CustomCards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senamoviles.alcayata.alcayata.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by macbook on 8/27/17.
 */

public class CustomCardDesc extends Card {

    String texto_desc;
    TextView descripcion;

    public CustomCardDesc(Context context) {
        super(context, R.layout.custom_card_desc_sabias);
        init();
    }

    public CustomCardDesc(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init(){


    }
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        descripcion = (TextView) parent.findViewById(R.id.desc_sabias);
        descripcion.setText(getDesc());


    }

    public void setDesc(String texto_desc){
        this.texto_desc = texto_desc;
    }

    public String getDesc(){
        return texto_desc;
    }

}
