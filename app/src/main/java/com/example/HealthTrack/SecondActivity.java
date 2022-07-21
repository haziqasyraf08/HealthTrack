package com.example.HealthTrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    String name, email;
    Button button;
    TextView tvCoordinate, tvAddress;
    private LocationCallback locationCallback;
    LocationRequest locationRequest;

    String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE"};

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = (Button) findViewById(R.id.button);
        tvCoordinate = (TextView) findViewById(R.id.tvCoordinate);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MapsActivity.class);
                startActivity(intent)
                ;
            }
        });

        TextView tvName = (TextView) findViewById(R.id.tvname);
        TextView tvEmail = (TextView) findViewById(R.id.tvemail);

        name = getIntent().getStringExtra("Name");
        email = getIntent().getStringExtra("Email");

        tvName.setText(name);
        tvEmail.setText(email);

        Button signout = findViewById(R.id.signout);
        signout.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ActivityCompat.requestPermissions(this, perms, 200);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }

        locationRequest = LocationRequest.create();

        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 + 100);
        locationRequest.setFastestInterval(2000);


        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(getApplicationContext(), "Unable to detect location", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    tvCoordinate.setText("" + lat + "," + lon);
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    List<Address> addressList = null;
                    try {
                        addressList = geocoder.getFromLocation(lat, lon, 1);
                        Address address = addressList.get(0);
                        String line = address.getAddressLine(0);
                        String area = address.getAdminArea();
                        String country = address.getCountryName();
                        String postcode = address.getPostalCode();

                        tvAddress.setText(line+"\n"+area+"\n"+country+"\n"+postcode);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                tvCoordinate.setText("" + lat + "," + lon);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signout:
                signOut();
                break;
        }

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), email + "Succesfull signed out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){

            case R.id.aboutus:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }

    return super.onOptionsItemSelected(item);
    }
}