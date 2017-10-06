package com.Help.Helping_1.click_help_1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;

    Button b;

    String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

      //  b=(Button)findViewById(R.id.mp);

        Intent j=getIntent();

       s=j.getStringExtra("Place");

        Toast.makeText(MapsActivity.this,"Place= "+s,Toast.LENGTH_SHORT).show();



       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<Address> addressList=null;


                Geocoder geocoder=new Geocoder(MapsActivity.this);
                try {
                    addressList=geocoder.getFromLocationName(s,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Address myaddress = addressList.get(0);

                String locality=myaddress.getLocality();


                LatLng pp = new LatLng(myaddress.getLatitude(), myaddress.getLongitude());

                CameraUpdate cameraUpdateFactory=CameraUpdateFactory.newLatLngZoom(pp,15);


                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(pp);
                markerOptions.title(s);
                mMap.addMarker(markerOptions);

                mMap.moveCamera(cameraUpdateFactory);

            }
        });*/








        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();

           /* Intent intent=new Intent(Donate.this,Navigation.class);
            intent.putExtra("For_single_frag","From_Donate");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
        }

        return super.onOptionsItemSelected(item);
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
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if(!TextUtils.isEmpty(s)) {

            Toast.makeText(MapsActivity.this,"Place= "+s,Toast.LENGTH_SHORT).show();


            List<Address> addressList = null;


            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(s, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Address myaddress = addressList.get(0);

            String locality = myaddress.getLocality();


            LatLng pp = new LatLng(myaddress.getLatitude(), myaddress.getLongitude());

            CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(pp, 15);


            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(pp);
            markerOptions.title(s);
            mMap.addMarker(markerOptions);

            mMap.moveCamera(cameraUpdateFactory);
        }



    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
