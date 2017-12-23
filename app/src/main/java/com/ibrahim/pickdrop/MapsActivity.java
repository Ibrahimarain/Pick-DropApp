package com.ibrahim.pickdrop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibrahim.pickdrop.CustomClasses.Constants;
import com.ibrahim.pickdrop.CustomClasses.LocalStoragePreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocalStoragePreferences localStoragePreferences;
    LovelyProgressDialog lovelyProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lovelyProgressDialog = new LovelyProgressDialog(this);
        lovelyProgressDialog.setCancelable(false);
        lovelyProgressDialog.setTopColorRes(R.color.colorPrimary);
        lovelyProgressDialog.setIcon(R.drawable.ic_home_white_24dp);
        lovelyProgressDialog.setTopTitleColor(getResources().getColor(R.color.color_white));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        localStoragePreferences = LocalStoragePreferences.getInstance(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (localStoragePreferences.getUserType() == Constants.USER_TYPE_DRIVER){
            try {
                loadDriverPjp();
                lovelyProgressDialog.setTopTitle("Loading Data");
                lovelyProgressDialog.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (localStoragePreferences.getUserType() == Constants.USER_TYPE_EMPLOYEE){


        }

    }


    void loadDriverPjp() throws JSONException {

        AsyncHttpClient client = new AsyncHttpClient();
        String companyCode = localStoragePreferences.getCompanyCode();
        JSONObject userData = new JSONObject(localStoragePreferences.getUserData());
        Log.i("pjp", "loadDriverPjp: " + userData.toString());
        String driverToken = userData.getString("Token");
        String  driverId = userData.getString("DriverId") ;

        String pjpUrl = "http://"+ companyCode +".trackdrives.com/Mobile/Type/DriversPlan/?token="+driverToken+"&did="+driverId;

        Log.i("pjpUrl", "loadDriverPjp: "+pjpUrl);
        client.get(pjpUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (responseBody!=null) {
                   String response = new String(responseBody);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int a=0; a< jsonArray.length(); a++){
                            JSONObject jsonObject = jsonArray.getJSONObject(a);
                            String  str_latitude = jsonObject.getString("PickUpLatitude");
                            String  str_longtitude = jsonObject.getString("PickUpLongitude");

                            if (str_latitude !=null && !str_latitude.equals("null")
                                    && str_longtitude !=null && !str_longtitude.equals("null")){

                                Double latitude = Double.parseDouble(str_latitude);
                                Double longitude = Double.parseDouble(str_longtitude);

                                Log.i("latLng", "onSuccess: "+ latitude +"\t" + longitude);

                                 LatLng latLng = new LatLng(latitude, longitude);
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker at Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

                            }

                        }

                        lovelyProgressDialog.dismiss();


                    } catch (JSONException e) {
                        lovelyProgressDialog.dismiss();
                        e.printStackTrace();
                    }

                }
                }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                lovelyProgressDialog.dismiss();
            }
        });

    }



    void loadCurrentLocation(){

        SmartLocation.with(getApplicationContext())
                .location()
                .oneFix()
                .config(LocationParams.NAVIGATION)
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {

//                                pd.dismiss();
                        if (location != null) {
                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(sydney));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                            if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(MapsActivity.this,
                                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            mMap.setMyLocationEnabled(true);
                            mMap.setPadding(1, 50, 1, 1);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));
                        }
                    }
                });

    }


}
