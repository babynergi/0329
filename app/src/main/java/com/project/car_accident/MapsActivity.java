package com.project.car_accident;

import androidx.fragment.app.FragmentActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import com.google.android.maps.GeoPoint;


import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private EditText editText;
Double lat,lng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button maps=(Button)findViewById(R.id.maps);
        maps.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
Button weather=(Button)findViewById(R.id.how);
weather.setOnClickListener(new Button.OnClickListener() {
   @Override
    public void onClick(View view) {
        Intent intent = new Intent(MapsActivity.this, weatherActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
    }
});

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.66,127.04)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.57,127.04)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.51,126.93)).title("??????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.60,126.92)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.63,127.02)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger1))).position(new LatLng(37.53,127.12)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger1))).position(new LatLng(37.51,127.04)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.55,126.84)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.45,126.90)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.49,126.88)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.47,126.95)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.53,127.08)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.57,126.97)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.56,126.99)).title("??????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.60,127.09)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.56,12.90)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger1))).position(new LatLng(37.65,127.05)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.48,127.03)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.57,126.93)).title("????????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger1))).position(new LatLng(37.58,127.01)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.56,127.03)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger2))).position(new LatLng(37.51,127.10)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger1))).position(new LatLng(37.51,126.86)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.53,126.96)).title("?????????"));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.danger3))).position(new LatLng(37.52,126.89)).title("????????????"));

        mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).title("Marker in Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION,12));
        lat=37.56;
        lng=126.97;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // ?????? ?????????
               // mOptions.title("?????? ??????");
                Double latitude = point.latitude; // ??????
                Double longitude = point.longitude; // ??????
                // ????????? ?????????(????????? ?????????) ??????
                //mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: ?????? ?????? ?????? ?????????
                //mOptions.position(new LatLng(latitude, longitude));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));
                lat=latitude;
                lng=longitude;
               // googleMap.clear();
                // ??????(???) ??????
               //googleMap.addMarker(mOptions);

            }
        });

        // ?????? ?????????
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str=editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText??? ????????? ?????????(??????, ??????, ?????? ???)??? ?????? ????????? ????????? ??????
                    addressList = geocoder.getFromLocationName(
                            str, // ??????
                            10); // ?????? ?????? ?????? ??????
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // ????????? ???????????? split
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // ??????
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // ??????
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // ??????
                System.out.println(latitude);
                System.out.println(longitude);
                lat=Double.parseDouble(latitude);
                lng=Double.parseDouble(longitude);

                // ??????(??????, ??????) ??????
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//                // ?????? ??????
//                MarkerOptions mOptions2 = new MarkerOptions();
//                mOptions2.title("search result");
//                mOptions2.snippet(point.toString());
//                mOptions2.position(point);
//                // ?????? ??????
//                mMap.addMarker(mOptions2);
                // ?????? ????????? ?????? ???
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

            }
        });
        ////////////////////



    }
}

