package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageCache;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageFetcher;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.Utils;

public class ImageDetailActivity extends AppCompatActivity implements OnClickListener {
   private static final String IMAGE_CACHE_DIR = "images";
   public static final String EXTRA_IMAGE = "extra_image";

   @Bind (R.id.toolbar)
   Toolbar toolbar;
   @Bind (R.id.pager)
   ViewPager mPager;
   @Bind (R.id.fullscreen_content_controls)
   View mControlsView;
   @Bind (R.id.kuenstler_button)
   Button kuenstlerButton;

   private ImagePagerAdapter mAdapter;
   private ImageFetcher mImageFetcher;

   private ArrayList<Integer> werkeArray = new ArrayList<>();
   private List<String> kuenstlerArray = new ArrayList<>();
   private String kuenstlerName;

   @TargetApi (VERSION_CODES.HONEYCOMB)
   @Override
   public void onCreate(Bundle savedInstanceState) {
      if (BuildConfig.DEBUG) {
         Utils.enableStrictMode();
      }
      super.onCreate(savedInstanceState);
      setContentView(R.layout.image_detail_pager);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      final ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setDisplayShowTitleEnabled(false);
      }

      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         kuenstlerArray = extras.getStringArrayList("KUENSTLER_ARRAY");
         kuenstlerName = extras.getString("KUENSTLER_NAME", "");
         werkeArray = extras.getIntegerArrayList("WERKE_LIST");
         kuenstlerButton.setText(kuenstlerName);
      }

      // Fetch screen height and width, to use as our max size when loading images as this
      // activity runs full screen
      final DisplayMetrics displayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay()
            .getMetrics(displayMetrics);
      final int height = displayMetrics.heightPixels;
      final int width = displayMetrics.widthPixels;

      // For this sample we'll use half of the longest width to resize our images. As the
      // image scaling ensures the image is larger than this, we should be left with a
      // resolution that is appropriate for both portrait and landscape. For best image quality
      // we shouldn't divide by 2, but this will use more memory and require a larger memory
      // cache.
      final int longest = (height > width ? height : width) / 2;

      ImageCache.ImageCacheParams cacheParams =
            new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
      cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

      // The ImageFetcher takes care of loading images into our ImageView children asynchronously
      mImageFetcher = new ImageFetcher(this, longest);
      mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
      mImageFetcher.setImageFadeIn(false);

      // Set up ViewPager and backing adapter
      mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), werkeArray.size());
      mPager.setAdapter(mAdapter);
      mPager.setPageMargin((int) getResources().getDimension(R.dimen.spacing));
      mPager.setOffscreenPageLimit(2);

      // Set up activity to go full screen
      getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);

      // Hide and show the ActionBar as the visibility changes
      mPager.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
         @Override
         public void onSystemUiVisibilityChange(int vis) {
            if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
               actionBar.hide();
               mControlsView.setVisibility(View.GONE);
            } else {
               actionBar.show();
               mControlsView.setVisibility(View.VISIBLE);
            }
         }
      });
      mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

         }

         @Override
         public void onPageSelected(int position) {
            kuenstlerButton.setText(
                  ResHelper.getResId("kuenstler_name_" + kuenstlerArray.get(position),
                        R.string.class));
         }

         @Override
         public void onPageScrollStateChanged(int state) {

         }
      });
      kuenstlerButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(ImageDetailActivity.this, KuenstlerDetailActivity.class);
            intent.putExtra("KUENSTLER_NAME", kuenstlerArray.get(mPager.getCurrentItem()));
            startActivity(intent);
         }
      });

      // Start low profile mode and hide ActionBar
      mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
      if (actionBar != null) {
         actionBar.hide();
      }
      mControlsView.setVisibility(View.GONE);

      // Set the current item based on the extra passed in to this activity
      final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, -1);
      if (extraCurrentItem != -1) {
         mPager.setCurrentItem(extraCurrentItem);
      }
   }

   @Override
   public void onResume() {
      super.onResume();
      mImageFetcher.setExitTasksEarly(false);
   }

   @Override
   protected void onPause() {
      super.onPause();
      mImageFetcher.setExitTasksEarly(true);
      mImageFetcher.flushCache();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      mImageFetcher.closeCache();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
         onBackPressed();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   /**
    * Called by the ViewPager child fragments to load images via the one ImageFetcher
    */
   public ImageFetcher getImageFetcher() {
      return mImageFetcher;
   }

   /**
    * The main adapter that backs the ViewPager. A subclass of FragmentStatePagerAdapter as there
    * could be a large number of items in the ViewPager and we don't want to retain them all in
    * memory at once but create/destroy them on the fly.
    */
   private class ImagePagerAdapter extends FragmentStatePagerAdapter {
      private final int mSize;

      public ImagePagerAdapter(FragmentManager fm, int size) {
         super(fm);
         mSize = size;
      }

      @Override
      public int getCount() {
         return mSize;
      }

      @Override
      public Fragment getItem(int position) {
         return ImageDetailFragment.newInstance(
               getResources().getResourceEntryName(werkeArray.get(position)));
      }
   }

   /**
    * Set on the ImageView in the ViewPager children fragments, to enable/disable low profile mode
    * when the ImageView is touched.
    */
   @TargetApi (VERSION_CODES.HONEYCOMB)
   @Override
   public void onClick(View v) {
      final int vis = mPager.getSystemUiVisibility();
      if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
         mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
      } else {
         mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
      }
   }
}
