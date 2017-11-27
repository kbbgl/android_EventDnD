package com.kobbigal.savedlocations;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.ManagerFactoryParameters;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {


    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    public void returnAddressToActivityMain(String address) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        //TODO
//        startActivityForResult(intent, )

        Log.i("addressRe", address);
        intent.putExtra("address", address);

//        startActivity(intent);

    }

    public void setInitialLocation(LatLng userLoc) {

        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 15));
        locationManager.removeUpdates(locationListener);

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

            if (mMap != null){

                locationManager.removeUpdates(locationListener);
                mMap.clear();
                Log.i("Click", "User clicked map");

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses != null && addresses.size() > 0) {

                    String address = "";

                    if ((addresses.get(0).getThoroughfare() != null))
                        address += addresses.get(0).getThoroughfare() + " " + addresses.get(0).getSubThoroughfare();
                    if (addresses.get(0).getLocality() != null)
                        address += " " + addresses.get(0).getLocality();
                    if (addresses.get(0).getCountryName() != null)
                        address += " " + addresses.get(0).getCountryName();

                    mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getThoroughfare() + " " + addresses.get(0).getSubThoroughfare()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).showInfoWindow();

                    Log.i("address", address);
                        Toast.makeText(this, "Address added", Toast.LENGTH_LONG).show();
                    returnAddressToActivityMain(address);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    setInitialLocation(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()));

                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //32.057732, 34.767439

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

        mMap.setOnMapLongClickListener(this);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT > 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                setInitialLocation(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()));

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

                Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                setInitialLocation(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()));

                if (lastLoc.getAccuracy() < 10.0 && lastLoc.getAccuracy() != 0.0){
                    setInitialLocation(new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude()));
                    locationManager.removeUpdates(locationListener);
                    mMap.setOnMapLongClickListener(this);
                }


            }
        }

    }
}
