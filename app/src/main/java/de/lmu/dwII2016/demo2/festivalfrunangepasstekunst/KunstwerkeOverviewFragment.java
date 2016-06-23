package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KunstwerkeOverviewFragment extends Fragment {


   private final Integer kunstwerke[] = {
         R.drawable.kunstwerke_08,
         R.drawable.kunstwerke_12,
         R.drawable.kunstwerke_16,
         R.drawable.kunstwerke_22,
         R.drawable.kunstwerke_26,
         R.drawable.kunstwerke_37,
         R.drawable.kunstwerke_08,
         R.drawable.kunstwerke_12,
         R.drawable.kunstwerke_16,
         R.drawable.kunstwerke_22,
         R.drawable.kunstwerke_26,
         R.drawable.kunstwerke_37
   };

   @Bind(R.id.card_recycler_view)
   RecyclerView recyclerView;

   private Toolbar toolbar;
   private TabLayout tabLayout;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_demokratie_kunstwerke, container, false);
      ButterKnife.bind(this, rootView);
      initViews();
      return rootView;
   }

   private void initViews() {
      recyclerView.setHasFixedSize(true);
      RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
      recyclerView.setLayoutManager(layoutManager);

      ArrayList<Integer> kunstwerkeArray = prepareData();
      KunstwerkAdapter adapter = new KunstwerkAdapter(getActivity(), kunstwerkeArray);
      recyclerView.setAdapter(adapter);

      toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
      tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
      recyclerView.setOnScrollListener(new HidingScrollListener() {
         @Override
         public void onHide() {
            hideViews();
         }
         @Override
         public void onShow() {
            showViews();
         }
      });
   }

   private ArrayList<Integer> prepareData(){

      ArrayList<Integer> kunstwerkeArray = new ArrayList<>();
      for(int i = 0; i < kunstwerke.length; i++){
         kunstwerkeArray.add(kunstwerke[i]);
      }
      return kunstwerkeArray;
   }

   private void hideViews() {
      toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
      tabLayout.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
}

   private void showViews() {
      toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
      tabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
   }

}
