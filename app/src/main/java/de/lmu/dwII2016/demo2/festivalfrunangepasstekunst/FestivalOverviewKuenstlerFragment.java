package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.ArtistViewAdapter;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageCache;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageFetcher;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.Utils;

public class FestivalOverviewKuenstlerFragment extends Fragment {

   private static final String TAG = "FOWerkeFragment";
   private static final String IMAGE_CACHE_DIR = "thumbs";

   private int mImageThumbSize;
   private int mImageThumbSpacing;
   private ImageFetcher mImageFetcher;
   private ArtistViewAdapter mAdapter;

   private int kuenstlerArray;

   @Bind (R.id.card_recycler_view)
   RecyclerView recyclerView;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      kuenstlerArray = getArguments().getInt("KUENSTLER_ARRAY");

      mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
      mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.spacing);

      ImageCache.ImageCacheParams cacheParams =
            new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

      cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

      // The ImageFetcher takes care of loading images into our ImageView children asynchronously
      mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
      mImageFetcher.setLoadingImage(R.drawable.ic_launcher_demokratie);
      mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);

      mAdapter = new ArtistViewAdapter(getActivity(), mImageFetcher, getKuenstlerProfilesList(),
            getKuenstlerList());
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_wall_images, container, false);
      ButterKnife.bind(this, rootView);
      initViews();
      return rootView;
   }

   private void initViews() {
      recyclerView.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
      recyclerView.setAdapter(mAdapter);
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

   private List<Integer> getKuenstlerProfilesList() {
      String[] kuenstlerList = getResources().getStringArray(kuenstlerArray);

      List<Integer> imagesList = new ArrayList<>();
      for (String kuenstler : kuenstlerList) {
         imagesList.add(ResHelper.getResId("kuenstler_profile_" + kuenstler, R.drawable.class));
      }
      return imagesList;
   }

   private List<String> getKuenstlerList() {
      String[] kuenstlers = getResources().getStringArray(kuenstlerArray);

      List<String> kuenstlerList = new ArrayList<>();
      Collections.addAll(kuenstlerList, kuenstlers);
      return kuenstlerList;
   }
}
