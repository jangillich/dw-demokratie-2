package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by bianka.roppelt on 29/06/16.
 */
public class TabWallFragment  extends Fragment {

   private static LruCache<String, Bitmap> mMemoryCache;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_wall_images, container, false);
      ButterKnife.bind(this, rootView);
      initMemoryCache();
      return rootView;
   }
   public int getResId(String resName, Class resClass) {
      try {
         Field resourceField = resClass.getDeclaredField(resName);
         return resourceField.getInt(resourceField);
      } catch (Exception e) {
         return -1;
      }
   }
   // The following code is for memory

   private void initMemoryCache() {

      final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

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


   public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
         int reqWidth, int reqHeight) {

      // First decode with inJustDecodeBounds=true to check dimensions
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;

      // Calculate inSampleSize
      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

      // Decode bitmap with inSampleSize set
      options.inJustDecodeBounds = false;
      return BitmapFactory.decodeResource(res, resId, options);
   }

   public static int calculateInSampleSize(
         BitmapFactory.Options options, int reqWidth, int reqHeight) {
      // Raw height and width of image
      final int height = options.outHeight;
      final int width = options.outWidth;
      int inSampleSize = 1;

      if (height > reqHeight || width > reqWidth) {

         final int halfHeight = height / 2;
         final int halfWidth = width / 2;

         // Calculate the largest inSampleSize value that is a power of 2 and keeps both
         // height and width larger than the requested height and width.
         while ((halfHeight / inSampleSize) > reqHeight
               && (halfWidth / inSampleSize) > reqWidth) {
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
         final Bitmap bitmap = decodeSampledBitmapFromResource(
               context.getResources(), params[0], 100, 100);
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
            final BitmapWorkerTask bitmapWorkerTask =
                  getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
               imageView.setImageBitmap(bitmap);
            }
         }
      }
   }



   public void loadBitmap(int resId, ImageView imageView) {
      if (cancelPotentialWork(resId, imageView)) {
         final BitmapWorkerTask task = new BitmapWorkerTask(getContext(), imageView);
         Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_demokratie);
         final AsyncDrawable asyncDrawable =
               new AsyncDrawable(getResources(), placeholder, task);
         imageView.setImageDrawable(asyncDrawable);
         task.execute(resId);
      }
   }

   static class AsyncDrawable extends BitmapDrawable {
      private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

      public AsyncDrawable(Resources res, Bitmap bitmap,
            BitmapWorkerTask bitmapWorkerTask) {
         super(res, bitmap);
         bitmapWorkerTaskReference =
               new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
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
