package trinhhoainam.tttn.com.monngonduongpho.maps;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import trinhhoainam.tttn.com.monngonduongpho.R;

/**
 * Created by HoaiNam on 13/03/2017.
 */

public class TimDuongActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ProgressDialog myProgress;
    private Dialog dialog;
    private Location location;
    LatLng sydney;
    private String tieuDe = "", diaChi = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_maps);
        getSupportActionBar().setTitle("Chỉ đường");


        addControl();
        addEvent();
    }

    private void addEvent() {

    }

    private void addControl() {


        //create progress dialog
        myProgress = new ProgressDialog(this);
        myProgress.setMessage("Xin chờ...");
        myProgress.setTitle("Maps is Loading...");
        myProgress.setCancelable(false);
        myProgress.show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
                mMap.clear();
                Intent getLatLog = getIntent();
                double latitude = Double.parseDouble(getLatLog.getStringExtra("LATITUDE"));
                double longitude = Double.parseDouble(getLatLog.getStringExtra("LONGITUDE"));
                if (latitude == 0 || longitude == 0) {

                    location = mMap.getMyLocation();

                    if (location == null) {
                        double lature1 = 21.053232;
                        double longatu1 = 105.735242;
                        sydney = new LatLng(lature1, longatu1);
                        tieuDe = " Vị trí hiện tại !";
                    } else {
                        double lature = location.getLatitude();
                        double longatu = location.getLongitude();
                        sydney = new LatLng(lature, longatu);
                        tieuDe = " Vị trí hiện tại !";
                    }


                    //21.053232
                    //105.735242 nhon


                } else {
                    sydney = new LatLng(latitude, longitude);
                    tieuDe = getLatLog.getStringExtra("TITLE_FOOD");
                    diaChi = getLatLog.getStringExtra("ADDRESS_FOOD");
                }

                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(tieuDe).snippet(diaChi));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));


            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_maps_types, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_maps_type) {
            showMapsType();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMapsType() {
        dialog = new Dialog(this, R.style.CustomAnimationMaps);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.maps_type_show);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);
        LinearLayout layout_normal, layout_satelite, layout_terrain, layout_hybrid;
        layout_normal = (LinearLayout) dialog.findViewById(R.id.show_maps_normal);
        layout_satelite = (LinearLayout) dialog.findViewById(R.id.show_maps_satelite);
        layout_terrain = (LinearLayout) dialog.findViewById(R.id.show_maps_terrain);
        layout_hybrid = (LinearLayout) dialog.findViewById(R.id.show_maps_hybrid);

        layout_hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                dialog.dismiss();
            }
        });
        layout_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                dialog.dismiss();
            }
        });
        layout_satelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                dialog.dismiss();
            }
        });
        layout_terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                dialog.dismiss();
            }
        });


        dialog.setCancelable(false);
        dialog.show();
    }


}
