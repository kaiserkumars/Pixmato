
package com.sample.pixel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.sample.pixel.R;

import org.w3c.dom.Text;


public class ActivityTwo extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    String[] locArray = {"Gurgaon", "New Delhi", "Bombay", "Noida", "Kolkata", "Hyderabad", "Chennai"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        alertDialogbox();

        //MAP LOCATION CHANGE TXT
        TextView locChange = (TextView)findViewById(R.id.map_current_loc);
        locChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogbox();
            }
        });


        //MAP BOTTOM LIST
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.map_listview_txt, locArray);
        ListView listView = (ListView) findViewById(R.id.map_list);
        listView.setAdapter(adapter);

//        TextView title = (TextView) findViewById(R.id.activityTitle2);
//        title.setText("This is ActivityTwo");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        final SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(ActivityTwo.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(ActivityTwo.this, ActivityOne.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:

                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(ActivityTwo.this, ActivityThree.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(ActivityTwo.this, ActivityFour.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }


    private ClusterManager<MarkerClusterItem> mClusterManager;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mClusterManager = new ClusterManager<MarkerClusterItem>(this, mMap);

        setUpClusterer();
        mMap.setOnCameraChangeListener(mClusterManager);


    }

    private void setUpClusterer() {
        // Position the map.

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addPlaces();

//        setRenderer();
        mClusterManager.cluster();


    }


    private void addPlaces()
    {
        for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(28.4 + i, 77.0 + i);
            mClusterManager.addItem(new MarkerClusterItem("Marker #" + (i + 0.001), latLng));

        }

        for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(28.5 + i, 77.5 + i);
            mClusterManager.addItem(new MarkerClusterItem("Marker #" + (i + 0.001), latLng));

        }

    }




    private void setRenderer() {
        MarkerClusterRenderer<MarkerClusterItem> clusterRenderer = new MarkerClusterRenderer<>(this, mMap, mClusterManager);
        mClusterManager.setRenderer(clusterRenderer);
    }

    static class MarkerClusterItem implements ClusterItem {

        final String title;
        final LatLng latLng;

        public MarkerClusterItem(String title, LatLng latLng) {
            this.title = title;
            this.latLng = latLng;
        }


        @Override public LatLng getPosition() {
            return latLng;
        }
    }

    public void alertDialogbox()
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        /*LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.map_bottom_sheet, null);
        builder.setView(dialogView);*/
        builder.setItems(locArray, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                TextView tv  = (TextView)findViewById(R.id.map_current_loc);
                tv.setText(locArray[item]);
                tv.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    }


