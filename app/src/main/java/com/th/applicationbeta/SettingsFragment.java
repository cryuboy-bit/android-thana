package com.th.applicationbeta;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;


public class SettingsFragment extends Fragment {

    private int locationRequestCode = 1000;
    private Button button,button2;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private double[] clat = {0};
    private double[] clng = {0};

    EditText editTextname;

    private GoogleMap mMap;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        button = (Button) view.findViewById(R.id.button);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        editTextname = view.findViewById(R.id.editTextName);
        button2 = view.findViewById(R.id.button2);

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        }
        if (mapFragment != null) {
            mapFragment.getMapAsync(callbackactive);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapFragment != null) {
                    Get_Current_location();
                    mapFragment.getMapAsync(callbackactive);
                }
                Toast.makeText(getActivity(), "นี้คือที่อยู่ของคุณ", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapFragment != null) {
                    String location = editTextname.getText().toString();
                    List<Address> addressList = null;
                    // checking if the entered location is null or not.
                    if (location != null || location.equals("")) {
                        if(mMap != null) {
                            mMap.clear();
                        }
                        // on below line we are creating and initializing a geo coder.
                        Geocoder geocoder = new Geocoder(getActivity());
                        try {
                            // on below line we are getting location from the
                            // location name and adding that location to address list.
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // on below line we are getting the location
                        // from our list a first position.
                        Address address = addressList.get(0);

                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        clat[0] = address.getLatitude();
                        clng[0] = address.getLongitude();
                        String strsnippet =  location + "Here at " + clat[0] + ", " + clng[0];

                        MarkerOptions options1 = new MarkerOptions()
                                .position(latLng)
                                .title(location)
                                .snippet(strsnippet)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                        // on below line we are adding marker to that position.
                        mMap.addMarker(options1);

                        // below line is to animate camera to that position.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                    }
                }
                Toast.makeText(getActivity(), "นี้คือสถานที่ที่คุณค้นหา", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    private void Get_Current_location() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            clat[0] = location.getLatitude();
                            clng[0] = location.getLongitude();
                        }
                    }
                });
    }

    private OnMapReadyCallback callbackactive = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Get_Current_location();
            googleMap.clear();
            String strtitle = "Thanachot Location";
            String strsnippet = "I am Here at " + clat[0] + ", " + clng[0];
            LatLng cposition = new LatLng(clat[0], clng[0]);
            MarkerOptions options = new MarkerOptions()
                    .position(cposition)
                    .title(strtitle)
                    .snippet(strsnippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            LatLng cposition2 = new LatLng(15.246228413380893, 104.84522771608624);
            MarkerOptions options2 = new MarkerOptions()
                    .position(cposition2)
                    .title("Maker 1")
                    .snippet("คอมพิวเตอร์อยู่นี้")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            LatLng cposition3 = new LatLng(15.241842858374662, 104.84605580258949);
            googleMap.addMarker(new MarkerOptions()
                    .position(cposition3)
                    .title("Marker 2")
                    .snippet("วิทยาลัยเทคนิคอุบลราชธานี")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            LatLng cposition4 = new LatLng(15.244304706530786, 104.84135192684292);
            googleMap.addMarker(new MarkerOptions()
                    .position(cposition4)
                    .title("Marker 3")
                    .snippet("คณะเทคโนโลยีอุตสาหกรรม")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            googleMap.addMarker(options);
            googleMap.addMarker(options2);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cposition2, 15));
            mMap = googleMap;
        }
    };
}