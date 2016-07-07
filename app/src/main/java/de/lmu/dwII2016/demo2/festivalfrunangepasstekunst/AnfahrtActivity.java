package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.DWMapInfoViewAdapter;

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
            googleMap.setInfoWindowAdapter(new DWMapInfoViewAdapter(AnfahrtActivity.this));
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
               @Override
               public void onInfoWindowClick(Marker marker) {
                  double lat = marker.getPosition().latitude;
                  double lng = marker.getPosition().longitude;
                  Uri gmmIntentUri = Uri.parse("geo:"+ lat + "," + lng + "?q=" + lat + "," + lng + "?z=12(" + Uri.encode(marker.getTitle()) + ")");
                  Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                  mapIntent.setPackage("com.google.android.apps.maps");
                  startActivity(mapIntent);
               }
            });
            LatLng demokratie = new LatLng(48.129423, 11.611146);
            googleMap.addMarker(new MarkerOptions().position(demokratie)
                  .title(getResources().getString(R.string.map_marker_festival_1_title))
                  .snippet(getResources().getString(R.string.map_marker_festival_1_desc))
                    .icon (BitmapDescriptorFactory.fromResource(R.drawable.map_marker_demokratie)));
            LatLng macht = new LatLng(48.160838, 11.500710);
            googleMap.addMarker(new MarkerOptions().position(macht)
                    .title(getResources().getString(R.string.map_marker_festival_2_title))
                    .snippet(getResources().getString(R.string.map_marker_festival_2_desc))
                    .icon (BitmapDescriptorFactory.fromResource(R.drawable.map_marker_macht)));
            LatLng partizipation = new LatLng(48.159847, 11.549667);
            googleMap.addMarker(new MarkerOptions().position(partizipation)
                    .title(getResources().getString(R.string.map_marker_festival_3_title))
                    .snippet(getResources().getString(R.string.map_marker_festival_3_desc))
                    .icon (BitmapDescriptorFactory.fromResource(R.drawable.map_marker_partizipation)));

            double lat = (demokratie.latitude + macht.latitude + partizipation.latitude) / 3.0;
            double lng = (demokratie.longitude + macht.longitude + partizipation.longitude) / 3.0;
            LatLng center = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 8.0f));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 11.5f));
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
