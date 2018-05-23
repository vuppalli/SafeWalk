package i.com.uberforwalking;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerDragListener{
    private double myLat;
    private double myLong;
    private double destLat;
    private double destLong;
    private Button findBuddy;
    private EditText userName;
    private String name;
    private Marker here;
    private GoogleMap myMap;
    private Intent matchedIntent;

    private String url = "https://us-central1-nameless-199018.cloudfunctions.net/findBuddy";
    private RequestQueue queue;
    private StringRequest postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        matchedIntent = new Intent(this, MatchedActivity.class);
        // Enable Location
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            //ENABLE LOCATION SERVICESSSSS
        }
        LocationManager locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                2000,
                10, this);
        destLat = myLat;
        destLong = myLong;
        //Button and username stuff
        userName = (EditText) findViewById(R.id.enterName);

        findBuddy = (Button) findViewById(R.id.button);
        findBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //LatLng temp = here.getPosition();
                //destLat = temp.latitude;
                //destLong = temp.longitude;
                sendAndRequestResponse();
                loadFragment(new WaitFragment());
                findBuddy.setVisibility(View.GONE);
                getMatch(view);
            }
        });
    }

    public void getMatch(View view)
    {
        //Intent intent = new Intent(this, MatchedActivity.class);
        //startActivity(intent);
    }
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    public void onMarkerDragEnd(Marker marker){
        Log.d("GOES INSIDE", "Listener successful");
        findBuddy.setEnabled(false);
        LatLng pos = marker.getPosition();
        destLat = pos.latitude;
        int t = (int) (destLat*100.0);
        destLat = ((double)t) / 100.0;
        destLong = pos.longitude;
        t = (int) (destLong * 100.0);
        destLong = ((double)t) / 100.0;
        Log.d("New Locations", "Lat: "+destLat+" Long: "+destLong);
        findBuddy.setEnabled(true);
    }
    //Google Cloud Services Stuff
    private void sendAndRequestResponse(){
        /*try{Thread.sleep(2000);}
        catch(InterruptedException e){Log.d("exc", "interrupted exception");}*/
        queue = Volley.newRequestQueue(this);
        postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                matchedIntent.putExtra("RESPONSE", response);
                startActivity(matchedIntent);
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", userName.getText().toString());
                params.put("locLat", ""+myLat);
                params.put("locLong", ""+myLong);
                params.put("destLat", ""+destLat);
                params.put("destLong", ""+destLong);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onMapReady (GoogleMap gm){
        myMap = gm;
        LatLng riceHall = new LatLng(myLat, myLong);
        here = gm.addMarker(new MarkerOptions().position(riceHall).title("Drag to your destination"));
        //.draggable(true)
        here.setDraggable(true);
        myMap.setOnMarkerDragListener(this); ;
        gm.moveCamera(CameraUpdateFactory.newLatLng(riceHall));
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            myLat = location.getLatitude();
            int t = (int)(myLat*100.0);
            myLat = ((double) t) / 100.0;
            myLong = location.getLongitude();
            t = (int)(myLong*100.0);
            myLong = ((double) t) / 100.0;
            here.setPosition(new LatLng(myLat, myLong));
            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLat, myLong)));
            //myMap.setZoom();
        }
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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
}
