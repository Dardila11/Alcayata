package com.senamoviles.alcayata.alcayata.SwipeView;

import android.net.Uri;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.senamoviles.alcayata.alcayata.InfoActivity;
import com.senamoviles.alcayata.alcayata.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class SwipePasos extends AppCompatActivity implements FragmentoHuerto.OnFragmentInteractionListener,FragmentoCrucifijo.OnFragmentInteractionListener, FragmentoEvangelista.OnFragmentInteractionListener, FragmentoDolores.OnFragmentInteractionListener, BeaconConsumer {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private BeaconManager beaconManager;
    FragmentManager fr = getSupportFragmentManager();
    FragmentTransaction transaction;
    public static final String TAG = "Semana Santa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_pasos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        beaconManager=BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.icons_toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_descarga:
                //Descarga el PDF de la información del paso
                //Snackbar.make(getWindow().getDecorView(), "En unos momentos se descargará el PDF", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                //descargarArchivo();
                return true;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
                                    Toast.makeText(SwipePasos.this, "Huerto", Toast.LENGTH_SHORT).show();
                                    FragmentoHuerto huerto = new FragmentoHuerto();
                                    transaction =fr.beginTransaction();
                                    transaction.replace(R.id.main_content, huerto);
                                    transaction.commit();
                                    break;
                                case "10903":
                                    //El crucifijo
                                    //card_desc.setDesc("Esta es la descripcion del paso El Crucifijo");
                                    Toast.makeText(SwipePasos.this, "Crucifijo", Toast.LENGTH_SHORT).show();
                                    FragmentoCrucifijo crucifijo = new FragmentoCrucifijo();
                                    transaction =fr.beginTransaction();
                                    transaction.replace(R.id.main_content, crucifijo);
                                    transaction.commit();
                                    break;
                                case "51626":
                                    // San Juan Evangelista
                                    Toast.makeText(SwipePasos.this, "Evangelista", Toast.LENGTH_SHORT).show();
                                    FragmentoEvangelista evangelista = new FragmentoEvangelista();
                                    transaction =fr.beginTransaction();
                                    transaction.replace(R.id.main_content, evangelista);
                                    transaction.commit();
                                    break;
                                case "43984":
                                    // Virgen de los Dolores
                                    Toast.makeText(SwipePasos.this, "Dolores", Toast.LENGTH_SHORT).show();
                                    FragmentoDolores dolores = new FragmentoDolores();
                                    transaction =fr.beginTransaction();
                                    transaction.replace(R.id.main_content, dolores);
                                    transaction.commit();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            /*PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);*/
            Fragment fragment = null;
            switch (sectionNumber) {
                case 1:
                    fragment = new FragmentoHuerto();
                    break;
                case 2:
                    fragment = new FragmentoCrucifijo();
                    break;
                case 3:
                    fragment = new FragmentoEvangelista();
                    break;
                case 4:
                    fragment = new FragmentoDolores();
                    break;
            }
            return fragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_swipe_pasos, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }
}
