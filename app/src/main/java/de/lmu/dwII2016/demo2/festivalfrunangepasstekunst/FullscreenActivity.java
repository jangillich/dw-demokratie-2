package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullscreenActivity extends AppCompatActivity {

   private static final boolean AUTO_HIDE = true;
   private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
   private static final int UI_ANIMATION_DELAY = 300;
   private final Handler mHideHandler = new Handler();
   private final Runnable mHidePart2Runnable = new Runnable() {
      @SuppressLint ("InlinedApi")
      @Override
      public void run() {
         mImageView.setSystemUiVisibility(
               View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN |
                     View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                     View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                     View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
      }
   };

   @Bind(R.id.fullscreen_content)
   ImageView mImageView;
   @Bind(R.id.fullscreen_content_controls)
   View mControlsView;

   private int imageRes;

   private final Runnable mShowPart2Runnable = new Runnable() {
      @Override
      public void run() {
         // Delayed display of UI elements
         ActionBar actionBar = getSupportActionBar();
         if (actionBar != null) {
            actionBar.show();
         }
         mControlsView.setVisibility(View.VISIBLE);
      }
   };
   private boolean mVisible;
   private final Runnable mHideRunnable = new Runnable() {
      @Override
      public void run() {
         hide();
      }
   };
   /**
    * Touch listener to use for in-layout UI controls to delay hiding the system UI. This is to
    * prevent the jarring behavior of controls going away while interacting with activity UI.
    */
   private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS);
         }
         return false;
      }
   };

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
         onBackPressed();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_fullscreen);
      ButterKnife.bind(this);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         imageRes = extras.getInt("IMAGE");
      }

      setTitle(imageRes);
      mVisible = true;
      mImageView.setImageResource(imageRes);
      mImageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            toggle();
         }
      });

      findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
   }

   @Override
   protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      delayedHide(100);
   }

   private void delayedHide(int delayMillis) {
      mHideHandler.removeCallbacks(mHideRunnable);
      mHideHandler.postDelayed(mHideRunnable, delayMillis);
   }

   private void hide() {
      // Hide UI first
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.hide();
      }
      mControlsView.setVisibility(View.GONE);
      mVisible = false;

      // Schedule a runnable to remove the status and navigation bar after a delay
      mHideHandler.removeCallbacks(mShowPart2Runnable);
      mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
   }

   @SuppressLint ("InlinedApi")
   private void show() {
      // Show the system bar
      mImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
      mVisible = true;

      // Schedule a runnable to display UI elements after a delay
      mHideHandler.removeCallbacks(mHidePart2Runnable);
      mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
   }

   private void toggle() {
      if (mVisible) {
         hide();
      } else {
         show();
      }
   }
}
