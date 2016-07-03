package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KuenstlerDetailActivity extends AppCompatActivity {

   private static LruCache<String, Bitmap> mMemoryCache;

   @Bind (R.id.toolbar)
   Toolbar toolbar;
   @Bind (R.id.toolbar_layout)
   CollapsingToolbarLayout toolbarLayout;
   @Bind (R.id.app_bar)
   AppBarLayout appBarLayout;
   @Bind (R.id.card_recycler_view)
   RecyclerView recyclerView;
   @Bind (R.id.image_name)
   ImageView imageName;
   @Bind (R.id.image_profile)
   ImageView imageProfile;
   @Bind (R.id.kuenstler_text_container)
   CardView kuenstlerTextContainer;
   @Bind (R.id.kuenstler_text)
   TextView kuenstlerText;
   @Bind (R.id.icon_collapse_expand)
   ImageView iconCollapseExpand;

   private int descriptionViewFullHeight;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_kuenstler_detail);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setTitle("");

      initMemoryCache();
      initViews();
   }

   private void initViews() {
      // TODO: set correct name
      imageName.setImageResource(getResId("kuenstler_name_" + "adler", R.drawable.class));
      imageProfile.setImageResource(getResId("kuenstler_profile_" + "adler", R.drawable.class));
      kuenstlerText.setText(
            getString(getResId("kuenstler_text_" + "annanatascha", R.string.class)));
      initRecyclerView();
      initKuenstlerTextView();
   }

   private void initKuenstlerTextView() {
      kuenstlerTextContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  kuenstlerText.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  descriptionViewFullHeight =
                        kuenstlerText.getHeight() +
                              ((FrameLayout.LayoutParams) kuenstlerText.getLayoutParams())
                                    .bottomMargin +
                              ((FrameLayout.LayoutParams) kuenstlerText.getLayoutParams())
                                    .topMargin;

                  // initially changing the height to min height
                  ViewGroup.LayoutParams layoutParams = kuenstlerTextContainer.getLayoutParams();
                  layoutParams.height =
                        (int) getResources().getDimension(R.dimen.kuenstler_description_min_height);
                  kuenstlerTextContainer.setLayoutParams(layoutParams);

                  return true;
               }
            });
      kuenstlerTextContainer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleProductDescriptionHeight();
         }
      });
      iconCollapseExpand.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleProductDescriptionHeight();
         }
      });
   }

   private void toggleProductDescriptionHeight() {
      int descriptionViewMinHeight =
            (int) getResources().getDimension(R.dimen.kuenstler_description_min_height);
      if (kuenstlerTextContainer.getHeight() == descriptionViewMinHeight) {
         // expand
         iconCollapseExpand.setImageResource(R.drawable.ic_expand_less);
         ValueAnimator anim =
               ValueAnimator.ofInt(kuenstlerTextContainer.getMeasuredHeightAndState(),
                     descriptionViewFullHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = kuenstlerTextContainer.getLayoutParams();
               layoutParams.height = val;
               kuenstlerTextContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      } else {
         // collapse
         iconCollapseExpand.setImageResource(R.drawable.ic_expand_more);
         ValueAnimator anim =
               ValueAnimator.ofInt(kuenstlerTextContainer.getMeasuredHeightAndState(),
                     descriptionViewMinHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = kuenstlerTextContainer.getLayoutParams();
               layoutParams.height = val;
               kuenstlerTextContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      }
   }

   private void initRecyclerView() {
      recyclerView.setHasFixedSize(true);
      GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
      recyclerView.setLayoutManager(layoutManager);

      List<Integer> kunstwerkeArray = prepareData();
      KuenstlerImagesAdapter adapter =
            new KuenstlerImagesAdapter(KuenstlerDetailActivity.this, kunstwerkeArray);
      recyclerView.setAdapter(adapter);

      appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
         boolean isShow = false;
         int scrollRange = -1;

         @Override
         public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
               scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (scrollRange + verticalOffset == 0) {
               // TODO: set correct name
               toolbarLayout.setTitle(
                     getString(getResId("kuenstler_name_" + "adler", R.string.class)));
               isShow = true;
            } else if (isShow) {
               toolbarLayout.setTitle("");
               isShow = false;
            }
         }
      });
   }

   private List<Integer> prepareData() {
      // TODO: instead of "adler" the correct artist
      String[] images = getResources().getStringArray(getResId("images_" + "adler", R.array.class));

      List<Integer> imagesList = new ArrayList<Integer>();
      for (String image : images) {
         imagesList.add(getResId(image, R.drawable.class));
      }
      return imagesList;
   }

   public int getResId(String resName, Class resType) {
      try {
         Field resourceField = resType.getDeclaredField(resName);
         return resourceField.getInt(resourceField);
      } catch (Exception e) {
         e.printStackTrace();
         return -1;
      }
   }

   // The following code is for memory

   private void initMemoryCache() {

      final int maxMemory = (int) (Runtime.getRuntime()
            .maxMemory() / 1024);

      // Use 1/8th of the available memory for this memory cache.
      final int cacheSize = maxMemory / 8;

      mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
         @Override
         protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
         }
      };
   }

   public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
      if (getBitmapFromMemCache(key) == null) {
         mMemoryCache.put(key, bitmap);
      }
   }

   public static Bitmap getBitmapFromMemCache(String key) {
      return mMemoryCache.get(key);
   }

   public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
         int reqHeight) {

      // First decode with inJustDecodeBounds=true to check dimensions
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;

      // Calculate inSampleSize
      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

      // Decode bitmap with inSampleSize set
      options.inJustDecodeBounds = false;
      return BitmapFactory.decodeResource(res, resId, options);
   }

   public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
         int reqHeight) {
      // Raw height and width of image
      final int height = options.outHeight;
      final int width = options.outWidth;
      int inSampleSize = 1;

      if (height > reqHeight || width > reqWidth) {

         final int halfHeight = height / 2;
         final int halfWidth = width / 2;

         // Calculate the largest inSampleSize value that is a power of 2 and keeps both
         // height and width larger than the requested height and width.
         while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
         }
      }

      return inSampleSize;
   }

   static class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
      private final WeakReference<ImageView> imageViewReference;
      private int data = 0;
      private Context context;

      public BitmapWorkerTask(Context context, ImageView imageView) {
         // Use a WeakReference to ensure the ImageView can be garbage collected
         imageViewReference = new WeakReference<ImageView>(imageView);
         this.context = context;
      }

      @Override
      protected Bitmap doInBackground(Integer... params) {
         final Bitmap bitmap =
               decodeSampledBitmapFromResource(context.getResources(), params[0], 100, 100);
         addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
         return bitmap;
      }

      @Override
      protected void onPostExecute(Bitmap bitmap) {
         if (isCancelled()) {
            bitmap = null;
         }

         if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
               imageView.setImageBitmap(bitmap);
            }
         }
      }
   }

   public void loadBitmap(int resId, ImageView imageView) {
      if (cancelPotentialWork(resId, imageView)) {
         final BitmapWorkerTask task = new BitmapWorkerTask(this, imageView);
         Bitmap placeholder =
               BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_demokratie);
         final AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), placeholder, task);
         imageView.setImageDrawable(asyncDrawable);
         task.execute(resId);
      }
   }

   static class AsyncDrawable extends BitmapDrawable {
      private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

      public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
         super(res, bitmap);
         bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
      }

      public BitmapWorkerTask getBitmapWorkerTask() {
         return bitmapWorkerTaskReference.get();
      }
   }

   public static boolean cancelPotentialWork(int data, ImageView imageView) {
      final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

      if (bitmapWorkerTask != null) {
         final int bitmapData = bitmapWorkerTask.data;
         if (bitmapData != data) {
            // Cancel previous task
            bitmapWorkerTask.cancel(true);
         } else {
            // The same work is already in progress
            return false;
         }
      }
      // No task associated with the ImageView, or an existing task was cancelled
      return true;
   }

   private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
      if (imageView != null) {
         final Drawable drawable = imageView.getDrawable();
         if (drawable instanceof AsyncDrawable) {
            final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            return asyncDrawable.getBitmapWorkerTask();
         }
      }
      return null;
   }
}
