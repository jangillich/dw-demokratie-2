package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.ArtistViewAdapter;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageCache;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageFetcher;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.Utils;

public class EventInfosActivity extends AppCompatActivity
      implements YouTubePlayer.OnInitializedListener {

   @Bind (R.id.toolbar)
   Toolbar toolbar;

   @Bind (R.id.demokratie_infos_container)
   CardView demokratieInfosContainer;
   @Bind (R.id.demokratie_infos)
   TextView demokratieInfos;
   @Bind (R.id.demokratie_infos_title)
   TextView demokratieInfosTitle;
   @Bind (R.id.icon_collapse_expand_demokratie)
   ImageView iconCollapseExpandDemokratie;

   @Bind (R.id.macht_infos_container)
   CardView machtInfosContainer;
   @Bind (R.id.macht_infos)
   TextView machtInfos;
   @Bind (R.id.macht_infos_title)
   TextView machtInfosTitle;
   @Bind (R.id.icon_collapse_expand_macht)
   ImageView iconCollapseExpandMacht;

   @Bind (R.id.partizipation_infos_container)
   CardView partizipationInfosContainer;
   @Bind (R.id.partizipation_infos)
   TextView partizipationInfos;
   @Bind (R.id.partizipation_infos_title)
   TextView partizipationInfosTitle;
   @Bind (R.id.icon_collapse_expand_partizipation)
   ImageView iconCollapseExpandPartizipation;

   @Bind (R.id.festival_text_container)
   CardView festivalTextContainer;
   @Bind (R.id.festival_text)
   TextView festivalText;
   @Bind (R.id.icon_collapse_expand)
   ImageView iconCollapseExpand;

   @Bind (R.id.recycler_view_gruender)
   RecyclerView recyclerViewGruender;

   private int festivalContainerFullHeight;
   private int demokratieContainerFullHeight;
   private int machtContainerFullHeight;
   private int partizipationContainerFullHeight;
   private YouTubePlayerSupportFragment mYoutubePlayerFragment;

   private static final String IMAGE_CACHE_DIR = "thumbs";

   private int mImageThumbSize;
   private int mImageThumbSpacing;
   private ImageFetcher mImageFetcher;
   private ArtistViewAdapter mAdapter;

   private List<Integer> gruenderProfileImagesList = new ArrayList<>();
   private List<String> kuenstlerList = new ArrayList<>();

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

      mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
      mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.spacing);

      ImageCache.ImageCacheParams cacheParams =
            new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);

      cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

      // The ImageFetcher takes care of loading images into our ImageView children asynchronously
      mImageFetcher = new ImageFetcher(this, mImageThumbSize);
      mImageFetcher.setLoadingImage(R.drawable.ic_launcher_demokratie);
      mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);

      gruenderProfileImagesList.add(
            ResHelper.getResId("kuenstler_profile_adler", R.drawable.class));
      gruenderProfileImagesList.add(ResHelper.getResId("kuenstler_profile_jpg", R.drawable.class));
      kuenstlerList.add("adler");
      kuenstlerList.add("jpg");

      mAdapter =
            new ArtistViewAdapter(this, mImageFetcher, gruenderProfileImagesList, kuenstlerList);

      initGruenderRecyclerView();
      initFestivalTextView();
      initDemokratieView();
      initMachtView();
      initPartizipationView();
      initializeYoutubePlayer();
   }

   private void initGruenderRecyclerView() {
      recyclerViewGruender.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
      recyclerViewGruender.setAdapter(mAdapter);
      recyclerViewGruender.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
   public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
         boolean wasRestored) {
      if (!wasRestored) {
         player.cueVideo("Jwa9r3yO_YE"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
      }
   }

   @Override
   public void onInitializationFailure(YouTubePlayer.Provider provider,
         YouTubeInitializationResult errorReason) {
      if (errorReason.isUserRecoverableError()) {
         errorReason.getErrorDialog(this, RECOVERY_REQUEST)
               .show();
      } else {
         String error = "error reason: " + errorReason.toString();
         Toast.makeText(this, error, Toast.LENGTH_LONG)
               .show();
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

   private void initFestivalTextView() {
      festivalText.setText(getString(R.string.festival_info_text));
      festivalTextContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  festivalText.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  festivalContainerFullHeight = festivalText.getHeight() +
                        ((FrameLayout.LayoutParams) festivalText.getLayoutParams()).bottomMargin +
                        ((FrameLayout.LayoutParams) festivalText.getLayoutParams()).topMargin;

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
         ValueAnimator anim = ValueAnimator.ofInt(festivalTextContainer.getMeasuredHeightAndState(),
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
         ValueAnimator anim = ValueAnimator.ofInt(festivalTextContainer.getMeasuredHeightAndState(),
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

   private void initDemokratieView() {
      demokratieInfosContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  demokratieInfos.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  demokratieContainerFullHeight = demokratieInfos.getHeight() +
                        ((FrameLayout.LayoutParams) demokratieInfos.getLayoutParams())
                              .bottomMargin +
                        ((FrameLayout.LayoutParams) demokratieInfos.getLayoutParams()).topMargin;

                  // initially changing the height to min height
                  ViewGroup.LayoutParams layoutParams = demokratieInfosContainer.getLayoutParams();
                  layoutParams.height = demokratieInfosTitle.getHeight() +
                        ((FrameLayout.LayoutParams) demokratieInfosTitle.getLayoutParams())
                              .bottomMargin +
                        ((FrameLayout.LayoutParams) demokratieInfosTitle.getLayoutParams())
                              .topMargin +
                        iconCollapseExpandDemokratie.getHeight();
                  demokratieInfosContainer.setLayoutParams(layoutParams);

                  return true;
               }
            });
      demokratieInfosContainer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleDemokratieTextHeight();
         }
      });
      iconCollapseExpandDemokratie.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleDemokratieTextHeight();
         }
      });
   }

   private void toggleDemokratieTextHeight() {
      int demokratieViewMinHeight = demokratieInfosTitle.getHeight() +
            ((FrameLayout.LayoutParams) demokratieInfosTitle.getLayoutParams()).bottomMargin +
            ((FrameLayout.LayoutParams) demokratieInfosTitle.getLayoutParams()).topMargin +
            iconCollapseExpandDemokratie.getHeight();
      if (demokratieInfosContainer.getHeight() == demokratieViewMinHeight) {
         // expand
         iconCollapseExpandDemokratie.setImageResource(R.drawable.ic_expand_less);
         ValueAnimator anim =
               ValueAnimator.ofInt(demokratieInfosContainer.getMeasuredHeightAndState(),
                     demokratieContainerFullHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = demokratieInfosContainer.getLayoutParams();
               layoutParams.height = val;
               demokratieInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      } else {
         // collapse
         iconCollapseExpandDemokratie.setImageResource(R.drawable.ic_expand_more);
         ValueAnimator anim =
               ValueAnimator.ofInt(demokratieInfosContainer.getMeasuredHeightAndState(),
                     demokratieViewMinHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = demokratieInfosContainer.getLayoutParams();
               layoutParams.height = val;
               demokratieInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      }
   }

   private void initMachtView() {
      machtInfosContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  machtInfos.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  machtContainerFullHeight = machtInfos.getHeight() +
                        ((FrameLayout.LayoutParams) machtInfos.getLayoutParams()).bottomMargin +
                        ((FrameLayout.LayoutParams) machtInfos.getLayoutParams()).topMargin;

                  // initially changing the height to min height
                  ViewGroup.LayoutParams layoutParams = machtInfosContainer.getLayoutParams();
                  layoutParams.height = machtInfosTitle.getHeight() +
                        ((FrameLayout.LayoutParams) machtInfosTitle.getLayoutParams())
                              .bottomMargin +
                        ((FrameLayout.LayoutParams) machtInfosTitle.getLayoutParams()).topMargin +
                        iconCollapseExpandMacht.getHeight();
                  machtInfosContainer.setLayoutParams(layoutParams);

                  return true;
               }
            });
      machtInfosContainer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleMachtTextHeight();
         }
      });
      iconCollapseExpandMacht.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            toggleMachtTextHeight();
         }
      });
   }

   private void toggleMachtTextHeight() {
      int machtViewMinHeight = machtInfosTitle.getHeight() +
            ((FrameLayout.LayoutParams) machtInfosTitle.getLayoutParams()).bottomMargin +
            ((FrameLayout.LayoutParams) machtInfosTitle.getLayoutParams()).topMargin +
            iconCollapseExpandMacht.getHeight();
      if (machtInfosContainer.getHeight() == machtViewMinHeight) {
         // expand
         iconCollapseExpandMacht.setImageResource(R.drawable.ic_expand_less);
         ValueAnimator anim = ValueAnimator.ofInt(machtInfosContainer.getMeasuredHeightAndState(),
               machtContainerFullHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = machtInfosContainer.getLayoutParams();
               layoutParams.height = val;
               machtInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      } else {
         // collapse
         iconCollapseExpandMacht.setImageResource(R.drawable.ic_expand_more);
         ValueAnimator anim = ValueAnimator.ofInt(machtInfosContainer.getMeasuredHeightAndState(),
               machtViewMinHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = machtInfosContainer.getLayoutParams();
               layoutParams.height = val;
               machtInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      }
   }

   private void initPartizipationView() {
      partizipationInfosContainer.getViewTreeObserver()
            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
               @Override
               public boolean onPreDraw() {
                  partizipationInfos.getViewTreeObserver()
                        .removeOnPreDrawListener(this);
                  // save the full height
                  partizipationContainerFullHeight = partizipationInfos.getHeight() +
                        ((FrameLayout.LayoutParams) partizipationInfos.getLayoutParams())
                              .bottomMargin +
                        ((FrameLayout.LayoutParams) partizipationInfos.getLayoutParams()).topMargin;

                  // initially changing the height to min height
                  ViewGroup.LayoutParams layoutParams =
                        partizipationInfosContainer.getLayoutParams();
                  layoutParams.height = partizipationInfosTitle.getHeight() +
                        ((FrameLayout.LayoutParams) partizipationInfosTitle.getLayoutParams())
                              .bottomMargin +
                        ((FrameLayout.LayoutParams) partizipationInfosTitle.getLayoutParams())
                              .topMargin +
                        iconCollapseExpandPartizipation.getHeight();
                  partizipationInfosContainer.setLayoutParams(layoutParams);

                  return true;
               }
            });
      partizipationInfosContainer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            togglePartizipationTextHeight();
         }
      });
      iconCollapseExpandPartizipation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            togglePartizipationTextHeight();
         }
      });
   }

   private void togglePartizipationTextHeight() {
      int partizipationViewMinHeight = partizipationInfosTitle.getHeight() +
            ((FrameLayout.LayoutParams) partizipationInfosTitle.getLayoutParams()).bottomMargin +
            ((FrameLayout.LayoutParams) partizipationInfosTitle.getLayoutParams()).topMargin +
            iconCollapseExpandPartizipation.getHeight();
      if (partizipationInfosContainer.getHeight() == partizipationViewMinHeight) {
         // expand
         iconCollapseExpandPartizipation.setImageResource(R.drawable.ic_expand_less);
         ValueAnimator anim =
               ValueAnimator.ofInt(partizipationInfosContainer.getMeasuredHeightAndState(),
                     partizipationContainerFullHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = partizipationInfosContainer.getLayoutParams();
               layoutParams.height = val;
               partizipationInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      } else {
         // collapse
         iconCollapseExpandPartizipation.setImageResource(R.drawable.ic_expand_more);
         ValueAnimator anim =
               ValueAnimator.ofInt(partizipationInfosContainer.getMeasuredHeightAndState(),
                     partizipationViewMinHeight);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int val = (Integer) valueAnimator.getAnimatedValue();
               ViewGroup.LayoutParams layoutParams = partizipationInfosContainer.getLayoutParams();
               layoutParams.height = val;
               partizipationInfosContainer.setLayoutParams(layoutParams);
            }
         });
         anim.start();
      }
   }
}
