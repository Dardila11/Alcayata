package com.senamoviles.alcayata.alcayata.CustomCards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senamoviles.alcayata.alcayata.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by macbook on 8/27/17.
 */

public class CustomCardSabias extends Card {

    TextView sabias;
    String texto_sabias;

    public CustomCardSabias(Context context) {
        super(context, R.layout.custom_card_sabias);
        init();
    }

    public CustomCardSabias(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }
    private void init() {

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        sabias = (TextView) parent.findViewById(R.id.sabias);
        //sabias.setText("descripcion de la descripcion lol");
        sabias.setText(getSabias());


    }

    public void setSabias(String texto_sabias){
         this.texto_sabias = texto_sabias;
    }

    public String getSabias(){
        return texto_sabias;
    }




}
