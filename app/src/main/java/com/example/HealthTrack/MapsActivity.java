package com.example.HealthTrack;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project.databinding.ActivityMapsBinding;

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    MarkerOptions marker;
    LatLng centerlocation;

    Vector<MarkerOptions> markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerlocation = new LatLng(3.0, 101);

        markerOptions = new Vector<>();

        markerOptions.add(marker = new MarkerOptions().title("Hospital Sultanah Bahiyah")
                .position(new LatLng(6.149091, 100.407221))
                .snippet("Alor Setar, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Yan")
                .position(new LatLng(5.820435, 100.385131))
                .snippet("Yan, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Jitra")
                .position(new LatLng(6.277471, 100.417497))
                .snippet("Jitra, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Sultan Abdul Halim")
                .position(new LatLng(5.669614, 100.517415))
                .snippet("Sungai Petani, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Pantai Hospital Sungai Petani")
                .position(new LatLng(5.6726 , 100.5132))
                .snippet("Sungai Petani, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Kulim")
                .position(new LatLng(5.392154, 100.574463))
                .snippet("Kulim, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Baling")
                .position(new LatLng(5.678075, 100.925610))
                .snippet("Baling, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Kedah Medical Center")
                .position(new LatLng(6.1488, 100.3694))
                .snippet("Alor Setar, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Sik")
                .position(new LatLng(5.811987, 100.727504))
                .snippet("Sik, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Hospital Langkawi")
                .position(new LatLng(6.325532, 99.797282))
                .snippet("Langkawi, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("INS Specialist Centre Sdn Bhd")
                .position(new LatLng(6.1255, 100.3646))
                .snippet("Alor Setar, Kedah")
        );

        markerOptions.add(marker = new MarkerOptions().title("Metro Specialist Hospital")
                .position(new LatLng(5.6291, 100.5100))
                .snippet("Sungai Petani, Kedah")
        );
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation, 8));

        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this, perms, 200);
        }
    }
}