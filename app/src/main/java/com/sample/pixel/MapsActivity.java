//@Deepak  - WARNING  - Obsolete - used only for testing Google Maps SDK


package com.sample.pixel;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        final SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    // Declare a variable for the cluster manager.
    private ClusterManager<MarkerClusterItem> mClusterManager;



    private static final double LAT =  -8.101379;
    private static final double LNG =  112.147751;
    @Override public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpClusterer();

//        mClusterManager = new ClusterManager<>(this, mMap);

//        mMap.setOnCameraChangeListener(mClusterManager);

//        mClusterManager.cluster();

    }

    private void setUpClusterer() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LAT,LNG), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MarkerClusterItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addPlaces();

        setRenderer();

    }


    private void addPlaces()
    {
        for (int i = 0; i < 10; i++) {
            final LatLng latLng = new LatLng(-34 + i, 151 + i);
            mClusterManager.addItem(new MarkerClusterItem("Marker #" + (i + 1), latLng));

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

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}