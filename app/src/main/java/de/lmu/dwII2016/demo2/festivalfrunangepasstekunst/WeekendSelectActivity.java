package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeekendSelectActivity extends AppCompatActivity {

    @Bind(R.id.demokratie_images)
    ImageSwitcher demokratieSwitcher;
    @Bind(R.id.macht_images)
    ImageSwitcher machtSwitcher;
    @Bind(R.id.partizipation_images)
    ImageSwitcher partizipationSwitcher;
    @Bind(R.id.demokratie_view)
    View demokratieView;
    @Bind(R.id.macht_view)
    View machtView;
    @Bind(R.id.partizipation_view)
    View partizipationView;

    @Bind(R.id.notification_container)
    CardView notificationContainer;
    @Bind(R.id.notification_button_ok)
    Button notificationButtonOk;
    @Bind(R.id.notification_button_more)
    Button notificationButtonMore;

    private String last_demokratie_image;
    private String last_macht_image;
    private String last_partizipation_image;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String NotificationClickedAway = "notAway";
    SharedPreferences sharedpreferences;
    private static LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekend_select);
        ButterKnife.bind(this);

        initMemoryCache();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        initNotification();
        initImageSwitcher(demokratieSwitcher, 1, R.array.demokratie_kunstwerke);
        // TODO: change arrays
        initImageSwitcher(machtSwitcher, 2, R.array.demokratie_kunstwerke);
        initImageSwitcher(partizipationSwitcher, 3, R.array.demokratie_kunstwerke);
        initOnClickListener();
    }


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

    private void initOnClickListener() {
        demokratieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeekendSelectActivity.this,  DemokratieOverviewActivity.class);
                startActivity(intent);
            }
        });
        machtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        partizipationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initNotification() {
        boolean showNotification = !sharedpreferences.getBoolean(NotificationClickedAway, false);
        if(showNotification) {
            notificationContainer.setVisibility(View.VISIBLE);
            notificationButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationContainer.animate()
                          .translationX(getResources().getDisplayMetrics().widthPixels)
                          .alpha(1.0f)
                          .setDuration(500)
                          .withEndAction(new Runnable() {
                              @Override
                              public void run() {
                                  notificationContainer.setVisibility(View.GONE);
                                  saveHideNotificationButton();
                              }
                          }).start();
                }
            });
            notificationButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: go to festival page
                    saveHideNotificationButton();
                }
            });
        }
    }

    private void saveHideNotificationButton() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(NotificationClickedAway, true);
        editor.apply();
    }

    private void initImageSwitcher(final ImageSwitcher switcher, final int festival, final int arrayRes) {

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                      LinearLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Animation animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        switcher.setInAnimation(animIn);
        switcher.setOutAnimation(animOut);
        switcher.postDelayed(new Runnable() {
            public void run() {
                loadBitmap(getRandomImageRes(festival, arrayRes), switcher);
//                switcher.setImageDrawable(ImageHelper.decodeSampledBitmapFromResource(getResources(), getRandomImageRes(festival, arrayRes), 300, 300));
//                switcher.setImageResource(getRandomImageRes(festival, arrayRes));
                switcher.postDelayed(this, 5000);
            }
        }, 0);
    }

    private int getRandomImageRes(int festival, int arrayRes) {
        String[] array = getResources().getStringArray(arrayRes);
        String new_image = array[new Random().nextInt(array.length)];
        switch (festival) {
            case 1:
                while (new_image.equals(last_demokratie_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_demokratie_image = new_image;
                break;
            case 2:
                while (new_image.equals(last_macht_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_macht_image = new_image;
                break;
            case 3:
                while (new_image.equals(last_partizipation_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_partizipation_image = new_image;
                break;
            default:
        }
        String kunstwerkName = "kunstwerke_" + new_image;

        final int resourceId = getResources().getIdentifier(kunstwerkName, "drawable", getPackageName());
        return resourceId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weekend_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_events) {
            return true;
        } else if (id == R.id.action_anfahrt) {
            return true;
        } else if (id == R.id.action_festival) {
            return true;
        } else if (id == R.id.action_gruender) {
            return true;
        } else if (id == R.id.action_impressum) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        private final WeakReference<ImageSwitcher> imageViewReference;
        private int data = 0;
        private Context context;

        public BitmapWorkerTask(Context context, ImageSwitcher imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageSwitcher>(imageView);
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
                final ImageSwitcher imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                      getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageDrawable(new BitmapDrawable(context.getResources(), bitmap));
                }
            }
        }
    }



    public void loadBitmap(int resId, ImageSwitcher imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(this, imageView);
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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

    public static boolean cancelPotentialWork(int data, ImageSwitcher imageView) {
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

    private static BitmapWorkerTask getBitmapWorkerTask(ImageSwitcher imageView) {
        if (imageView != null) {
            final Drawable drawable = ((ImageView)imageView.getCurrentView()).getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
