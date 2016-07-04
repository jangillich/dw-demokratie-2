package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnfahrtActivity extends AppCompatActivity {

   @Bind (R.id.toolbar)
   Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_anfahrt_activity);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }
      SupportMapFragment mapFragmentDemokratie = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_festival1);
      mapFragmentDemokratie.getMapAsync(new OnMapReadyCallback() {
         @Override
         public void onMapReady(GoogleMap googleMap) {
            LatLng demokratie = new LatLng(48.129423, 11.611146);
            googleMap.addMarker(new MarkerOptions().position(demokratie)
                  .title(getResources().getString(R.string.map_marker_festival_1)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(demokratie, 12.0f));

         }
      });
      SupportMapFragment mapFragmentMacht = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_festival2);
      mapFragmentMacht.getMapAsync(new OnMapReadyCallback() {
         @Override
         public void onMapReady(GoogleMap googleMap) {
            LatLng macht = new LatLng(48.160838, 11.500710);
            googleMap.addMarker(new MarkerOptions().position(macht)
                  .title(getResources().getString(R.string.map_marker_festival_2)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(macht, 12.0f));
         }
      });
      SupportMapFragment mapFragmentPartizipation = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_festival3);
      mapFragmentPartizipation.getMapAsync(new OnMapReadyCallback() {
         @Override
         public void onMapReady(GoogleMap googleMap) {
            LatLng partizipation = new LatLng(48.159847, 11.549667);
            googleMap.addMarker(new MarkerOptions().position(partizipation)
                  .title(getResources().getString(R.string.map_marker_festival_3)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(partizipation, 12.0f));
         }
      });
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            onBackPressed();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
}
