package com.example.patrick.legps;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by patrick on 10/19/16.
 */
public class Localizador extends ContextWrapper implements LocationListener {


    String myLocation;
    LocationManager locationManager;

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        Log.e("LOCALIZAÇÃO ATUAL", myLocation);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    public String getMyLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);

        return myLocation;
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Localizador(Context base) {//Este construtor já registra o próprio onjeto como locationListener.
        super(base);
        myLocation = "O valor da localização não está sendo alterado";


    }
}
