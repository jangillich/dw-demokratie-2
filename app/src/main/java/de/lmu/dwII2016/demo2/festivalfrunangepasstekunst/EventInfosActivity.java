package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventInfosActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

   @Bind (R.id.toolbar)
   Toolbar toolbar;

   private  YouTubePlayerSupportFragment mYoutubePlayerFragment;

   private static final int RECOVERY_REQUEST = 1;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_event_infos);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }
      initializeYoutubePlayer();

   }

   private void initializeYoutubePlayer() {
      mYoutubePlayerFragment = new YouTubePlayerSupportFragment();
      mYoutubePlayerFragment.initialize(getString(R.string.google_maps_key), this);
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.fragment_youtube_player, mYoutubePlayerFragment);
      fragmentTransaction.commit();
   }

   @Override
   public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
      if (!wasRestored) {
         player.cueVideo("Jwa9r3yO_YE"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
      }
   }

   @Override
   public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
      if (errorReason.isUserRecoverableError()) {
         errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
      } else {
         String error = "error reason: " + errorReason.toString();
         Toast.makeText(this, error, Toast.LENGTH_LONG).show();
      }
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == RECOVERY_REQUEST) {
         // Retry initialization if user performed a recovery action
         getYouTubePlayerProvider().initialize(getString(R.string.google_maps_key), this);
      }
   }

   protected YouTubePlayer.Provider getYouTubePlayerProvider() {
      return mYoutubePlayerFragment;
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
