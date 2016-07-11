package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.GridSpacingItemDecoration;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageCache;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageFetcher;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.Utils;

public class KuenstlerDetailActivity extends AppCompatActivity {

   private static final String TAG = "FOWerkeGridFragment";
   private static final String IMAGE_CACHE_DIR = "thumbs";

   private int mImageThumbSize;
   private int mImageThumbSpacing;
   private ImageAdapter mAdapter;
   private ImageFetcher mImageFetcher;

   @Bind (R.id.toolbar)
   Toolbar toolbar;
   @Bind (R.id.toolbar_layout)
   CollapsingToolbarLayout toolbarLayout;
   @Bind (R.id.app_bar)
   AppBarLayout appBarLayout;
   @Bind (R.id.kuenstler_images)
   RecyclerView kuenstlerImagesView;
   @Bind (R.id.image_name)
   ImageView imageName;
   @Bind (R.id.image_name_text)
   TextView imageNameText;
   @Bind (R.id.image_profile)
   ImageView imageProfile;
   @Bind (R.id.kuenstler_text_container)
   CardView kuenstlerTextContainer;
   @Bind (R.id.kuenstler_text)
   TextView kuenstlerText;
   @Bind (R.id.icon_collapse_expand)
   ImageView iconCollapseExpand;

   private int descriptionViewFullHeight;
   private String kuenstlerName;
   private List<Integer> werkeArray = new ArrayList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_kuenstler_detail);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }

      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         kuenstlerName = extras.getString("KUENSTLER_NAME");
      }
      setTitle("");

      initializeWerkeArray();

      mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
      mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.spacing);

      mAdapter = new ImageAdapter(this, werkeArray);

      ImageCache.ImageCacheParams cacheParams =
            new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);

      cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

      // The ImageFetcher takes care of loading images into our ImageView children asynchronously
      mImageFetcher = new ImageFetcher(this, mImageThumbSize);
      mImageFetcher.setLoadingImage(R.drawable.loader);
      mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);

      initViews();
   }

   private void initViews() {
      int kuenstlerNameImageId =
            ResHelper.getResId("kuenstler_name_" + kuenstlerName, R.drawable.class);
      if (kuenstlerNameImageId < 0) {
         imageName.setVisibility(View.GONE);
         imageNameText.setVisibility(View.VISIBLE);
         imageNameText.setText(
               getString(ResHelper.getResId("kuenstler_name_" + kuenstlerName, R.string.class)));
      } else {
         imageName.setImageResource(kuenstlerNameImageId);
      }
      imageProfile.setImageResource(
            ResHelper.getResId("kuenstler_profile_" + kuenstlerName, R.drawable.class));

      int kuenstlerTextId = ResHelper.getResId("kuenstler_text_" + kuenstlerName, R.string.class);
      if (kuenstlerTextId < 0) {
         kuenstlerTextContainer.setVisibility(View.GONE);
      } else {
         kuenstlerText.setText(getString(kuenstlerTextId));
         initKuenstlerTextView();
      }
      initRecyclerView();
   }

   private void initKuenstlerTextView() {
      kuenstlerTextContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  kuenstlerText.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  descriptionViewFullHeight = kuenstlerText.getHeight() +
                        ((FrameLayout.LayoutParams) kuenstlerText.getLayoutParams()).bottomMargin +
                        ((FrameLayout.LayoutParams) kuenstlerText.getLayoutParams()).topMargin;

                  if (descriptionViewFullHeight > 500) {
                     // initially changing the height to min height
                     ViewGroup.LayoutParams layoutParams = kuenstlerTextContainer.getLayoutParams();
                     layoutParams.height = (int) getResources().getDimension(
                           R.dimen.kuenstler_description_min_height);
                     kuenstlerTextContainer.setLayoutParams(layoutParams);

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
                  } else {
                     iconCollapseExpand.setVisibility(View.GONE);
                  }

                  return true;
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
      kuenstlerImagesView.setNestedScrollingEnabled(false);
      kuenstlerImagesView.setHasFixedSize(false);
      GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
      layoutManager.offsetChildrenVertical(R.dimen.spacing);

      kuenstlerImagesView.setLayoutManager(layoutManager);
      kuenstlerImagesView.setAdapter(mAdapter);
      int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
      kuenstlerImagesView.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, false, 0));
      kuenstlerImagesView.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            // Pause fetcher to ensure smoother scrolling when flinging
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
               // Before Honeycomb pause image loading on scroll to help with performance
               if (!Utils.hasHoneycomb()) {
                  mImageFetcher.setPauseWork(true);
               }
            } else {
               mImageFetcher.setPauseWork(false);
            }
         }
      });

      kuenstlerImagesView.getViewTreeObserver()
            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
               @TargetApi (Build.VERSION_CODES.JELLY_BEAN)
               @Override
               public void onGlobalLayout() {
                  if (mAdapter.getNumColumns() == 0) {
                     final int numColumns = (int) Math.floor(
                           kuenstlerImagesView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                     if (numColumns > 0) {
                        final int columnWidth =
                              (kuenstlerImagesView.getWidth() / numColumns) - mImageThumbSpacing;
                        mAdapter.setNumColumns(numColumns);
                        mAdapter.setItemHeight(columnWidth);
                        if (BuildConfig.DEBUG) {
                           Log.d(TAG, "numColumns:" + numColumns);
                        }
                        if (Utils.hasJellyBean()) {
                           kuenstlerImagesView.getViewTreeObserver()
                                 .removeOnGlobalLayoutListener(this);
                        } else {
                           kuenstlerImagesView.getViewTreeObserver()
                                 .removeGlobalOnLayoutListener(this);
                        }
                     }
                  }
               }
            });

      appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
         boolean isShow = false;
         int scrollRange = -1;

         @Override
         public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
               scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (scrollRange + verticalOffset == 0) {
               toolbarLayout.setTitle(getString(
                     ResHelper.getResId("kuenstler_name_" + kuenstlerName, R.string.class)));
               isShow = true;
            } else if (isShow) {
               toolbarLayout.setTitle("");
               isShow = false;
            }
         }
      });
   }

   private void initializeWerkeArray() {
      Field[] fields = R.drawable.class.getFields();
      for (Field field : fields) {
         if (field.getName()
               .startsWith(kuenstlerName + "_")) {
            try {
               werkeArray.add(field.getInt(null));
            } catch (IllegalAccessException e) {
               e.printStackTrace();
            }
         }
      }
   }

   @Override
   public void onResume() {
      super.onResume();
      mImageFetcher.setExitTasksEarly(false);
      mAdapter.notifyDataSetChanged();
   }

   @Override
   public void onPause() {
      super.onPause();
      mImageFetcher.setPauseWork(false);
      mImageFetcher.setExitTasksEarly(true);
      mImageFetcher.flushCache();
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      mImageFetcher.closeCache();
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

   /**
    * The main adapter that backs the GridView. This is fairly standard except the number of columns
    * in the GridView is used to create a fake top row of empty views as we use a transparent
    * ActionBar and don't want the real top row of images to start off covered by it.
    */
   private class ImageAdapter extends RecyclerView.Adapter {

      private final Context mContext;
      private int mItemHeight = 0;
      private int mNumColumns = 0;
      private GridView.LayoutParams mImageViewLayoutParams;

      private List<Integer> images;

      public class ItemViewHolder extends RecyclerView.ViewHolder {
         public ItemViewHolder(ImageView view) {
            super(view);
            ButterKnife.bind(this, view);
         }
      }

      public ImageAdapter(Context context, List<Integer> werkeArray) {
         super();
         mContext = context;
         this.images = werkeArray;
         mImageViewLayoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.MATCH_PARENT);
      }

      @Override
      public int getItemCount() {
         // If columns have yet to be determined, return no items
         if (getNumColumns() == 0) {
            return 0;
         }

         // Size + number of columns for top empty row
         return images.size();
      }

      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         ImageView imageView = new RecyclingImageView(mContext);
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         imageView.setLayoutParams(mImageViewLayoutParams);
         return new ItemViewHolder(imageView);
      }

      @Override
      public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

         // Now handle the main ImageView thumbnails
         ImageView imageView = (ImageView) holder.itemView;

         // Check the height matches our calculated column width
         if (imageView.getLayoutParams().height != mItemHeight) {
            imageView.setLayoutParams(mImageViewLayoutParams);
         }

         mImageFetcher.loadImage(getResources().getResourceEntryName(images.get(position)),
               imageView);
         imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final Intent i = new Intent(KuenstlerDetailActivity.this, ImageDetailActivity.class);
               i.putExtra(ImageDetailActivity.EXTRA_IMAGE,
                     (int) getItemId(holder.getAdapterPosition()));
               i.putExtra("KUENSTLER_NAME", kuenstlerName);
               if (Utils.hasJellyBean()) {
                  // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading
                  // spinner may
                  // show plus the thumbnail image in GridView is cropped. so using
                  // makeScaleUpAnimation() instead.
                  ActivityOptions options =
                        ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
                  startActivity(i, options.toBundle());
               } else {
                  startActivity(i);
               }
            }
         });
      }

      @Override
      public long getItemId(int position) {
         return position;
      }

      /**
       * Sets the item height. Useful for when we know the column width so the height can be set to
       * match.
       */
      public void setItemHeight(int height) {
         if (height == mItemHeight) {
            return;
         }
         mItemHeight = height;
         mImageViewLayoutParams =
               new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
         mImageFetcher.setImageSize(height);
         notifyDataSetChanged();
      }

      public void setNumColumns(int numColumns) {
         mNumColumns = numColumns;
      }

      public int getNumColumns() {
         return mNumColumns;
      }
   }
}
