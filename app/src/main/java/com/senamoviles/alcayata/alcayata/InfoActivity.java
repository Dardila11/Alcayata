package com.senamoviles.alcayata.alcayata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.senamoviles.alcayata.alcayata.CustomCards.CustomCardDesc;
import com.senamoviles.alcayata.alcayata.CustomCards.CustomCardSabias;
import com.senamoviles.alcayata.alcayata.LogicaLista.Paso;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class InfoActivity extends AppCompatActivity implements BeaconConsumer {

    private BeaconManager beaconManager;
    public static final String TAG = "Semana Santa";

    List<Paso> pasos;
    TextView paso_nombre;
    TextView paso_descripcion;
    TextView paso_sabias;
    ImageView paso_imagen;

    String texto;

    CustomCardDesc card_desc;
    CardHeader header_desc;
    CardViewNative cardView_desc;

    CustomCardSabias card_sabias;
    CardHeader header_sabias;
    CardExpand expand_sabias;
    CardViewNative cardView_sabias;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String nombre = getIntent().getStringExtra("nombre_paso");
        getSupportActionBar().setTitle(nombre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        beaconManager=BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        /*for (int i = 0; i < pasos.size() ; i++) {
            if(nombre == pasos.get(i).getNombre()){
            }

        }*/

        card_desc = new CustomCardDesc(getApplicationContext());
        header_desc = new CardHeader(getApplicationContext());
        header_desc.setTitle("Descripción");
        header_desc.setButtonExpandVisible(true);
        card_desc.addCardHeader(header_desc);

        //card_desc.setDesc("Esta es la descripcion del paso");
        card_desc.setDesc(texto);

        cardView_desc = (CardViewNative) findViewById(R.id.card_desc);
        cardView_desc.setCard(card_desc);





        card_sabias = new CustomCardSabias(getApplicationContext());
        header_sabias = new CardHeader(getApplicationContext());
        header_sabias.setTitle("Sabias Que");
        header_sabias.setButtonExpandVisible(true);

        card_sabias.addCardHeader(header_sabias);
        expand_sabias = new CardExpand(getApplicationContext());
        card_sabias.addCardExpand(expand_sabias);

        card_sabias.setSabias("Sabias que la prosesión mas 1 ...");

        cardView_sabias = (CardViewNative) findViewById(R.id.card_sabias);
        cardView_sabias.setCard(card_sabias);
    }
    private void iniciarlizarDatos(){
        pasos = new ArrayList<>();

        pasos.add(new Paso("San Juan Evangelista", R.drawable.juan,"Juan fue conocido como el apóstol " +
                "amado, estuvo en todos los momentos\n" +
                "importantes al lado de Jesús, por eso vemos su imagen en cada una de las\n" +
                "procesiones de Popayán",
                "¿Sabías que los cargueros de Popayán pueden terminar con el hombro\n" +
                        "reventado y prefieren morir antes que retirarse del paso durante el recorrido\n" +
                        "procesional?",""));

        pasos.add(new Paso("Señor del huerto", R.drawable.huerto,"Este paso representa el momento en que" +
                " Jesús realiza una oración antes de\n" +
                "ser capturado por los soldados romanos",
                "¿Sabías que ni los terremotos ni las guerras han causado la suspensión de\n" +
                        "las procesiones de Popayán?",""));

        pasos.add(new Paso("El Crucifijo", R.drawable.crucifijo,"Jesús agoniza, sufre y finalmente muere en" +
                " la Cruz",
                "¿Sabías que en la Cruz de un paso de la Semana Santa de Popayán está\n" +
                        "incrustado un trozo de la verdadera Cruz de Cristo?",""));

        pasos.add(new Paso("Virgen de los Dolores", R.drawable.virgen,"María, la madre de Jesús, sufre un" +
                " dolor tan grande por la muerte de su hijo,\n" +
                "que es representado con una espada atravesándole el corazón",
                "¿Sabías que La Virgen de los Dolores lleva en el pecho uno de los mayores\n" +
                        "secretos religiosos de Popayán?",""));

    }
    private void descargarArchivo(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://alcayata-174f1.appspot.com/");

        final File localFile;
        try {
            localFile = File.createTempFile("application","pdf");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.e("firebase ",";local tem file created  created " +localFile.toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("firebase ",";local tem file not created  created " +e.toString());

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.icons_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {;
        switch (item.getItemId()){
            case R.id.icon_descarga:
                //Descarga el PDF de la información del paso
                //Snackbar.make(getWindow().getDecorView(), "En unos momentos se descargará el PDF", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                descargarArchivo();
                return true;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        final Region region = new Region("myBeaons", Identifier.parse("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                try {
                    Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(final Beacon oneBeacon : beacons) {
                    Log.d(TAG, "distancia: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (String.valueOf(oneBeacon.getId2())){
                                case "6133":
                                    //El señor del Huerto
                                    card_desc.setDesc("Esta es la descripcion del paso El señor Del huerto");
                                    break;
                                case "10903":
                                    //El crucifijo
                                    //card_desc.setDesc("Esta es la descripcion del paso El Crucifijo");
                                    texto = "Esta es la descripcion del paso El Crucifijo";
                                    Toast.makeText(InfoActivity.this, "Crucifijo", Toast.LENGTH_SHORT).show();
                                    break;
                                case "51626":
                                    // San Juan Evangelista
                                    card_desc.setDesc("Esta es la descripcion del paso San Juan Evangelista");
                                    break;
                                case "43984":
                                    // Virgen de los Dolores
                                    card_desc.setDesc("Esta es la descripcion del paso Virgen de los Dolores");
                                    break;
                            }

                        }
                    });

                }

            }

        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
