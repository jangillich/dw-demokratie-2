package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventInfosActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

   @Bind (R.id.toolbar)
   Toolbar toolbar;
   @Bind (R.id.festival_text_container)
   CardView festivalTextContainer;
   @Bind (R.id.festival_text)
   TextView festivalText;
   @Bind (R.id.icon_collapse_expand)
   ImageView iconCollapseExpand;


   private int festivalContainerFullHeight;
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
      initKuenstlerTextView();
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


   private void initKuenstlerTextView() {
      festivalText.setText(getString(R.string.festival_info_text));
      festivalTextContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  festivalText.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  festivalContainerFullHeight =
                        festivalText.getHeight() +
                              ((FrameLayout.LayoutParams) festivalText.getLayoutParams())
                                    .bottomMargin +
                              ((FrameLayout.LayoutParams) festivalText.getLayoutParams())
                                    .topMargin;

                  // initially changing the height to min height
                  ViewGroup.LayoutParams layoutParams = festivalTextContainer.getLayoutParams();
                  layoutParams.height =
                        (int) getResources().getDimension(R.dimen.kuenstler_description_min_height);
                  festivalTextContainer.setLayoutParams(layoutParams);

                  return true;
               }
            });
      festivalTextContainer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleFestivalTextHeight();
         }
      });
      iconCollapseExpand.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleFestivalTextHeight();
         }
      });
   }



   private void toggleFestivalTextHeight() {
      int descriptionViewMinHeight =
            (int) getResources().getDimension(R.dimen.kuenstler_description_min_height);
      if (festivalTextContainer.getHeight() == descriptionViewMinHeight) {
         // expand
         iconCollapseExpand.setImageResource(R.drawable.ic_expand_less);
         ValueAnimator anim =
               ValueAnimator.ofInt(festivalTextContainer.getMeasuredHeightAndState(),
                     festivalContainerFullHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = festivalTextContainer.getLayoutParams();
               layoutParams.height = val;
               festivalTextContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      } else {
         // collapse
         iconCollapseExpand.setImageResource(R.drawable.ic_expand_more);
         ValueAnimator anim =
               ValueAnimator.ofInt(festivalTextContainer.getMeasuredHeightAndState(),
                     descriptionViewMinHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = festivalTextContainer.getLayoutParams();
               layoutParams.height = val;
               festivalTextContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      }
   }

}
