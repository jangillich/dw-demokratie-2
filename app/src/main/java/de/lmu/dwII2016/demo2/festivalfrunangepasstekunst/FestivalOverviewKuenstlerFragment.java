package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.ArtistViewAdapter;


public class FestivalOverviewKuenstlerFragment extends TabWallFragment {

   private int kuenstlerArray;

   @Bind(R.id.card_recycler_view)
   RecyclerView recyclerView;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kuenstlerArray = getArguments().getInt("KUENSTLER_ARRAY");
      View rootView = super.onCreateView(inflater, container, savedInstanceState);
      ButterKnife.bind(this, rootView);
      initViews();
      return rootView;
   }

   private void initViews() {
//      recyclerView.setHasFixedSize(true);
//      StaggeredGridLayoutManager
//            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//      recyclerView.setLayoutManager(layoutManager);

      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

      ArtistViewAdapter
            adapter = new ArtistViewAdapter(FestivalOverviewKuenstlerFragment.this, getKuenstlerProfilesList(), getKuenstlerList());
      recyclerView.setAdapter(adapter);
   }

   private List<Integer> getKuenstlerProfilesList(){
      String[] kuenstlerList = getResources().getStringArray(kuenstlerArray);

      List<Integer> imagesList = new ArrayList<>();
      for (String kuenstler : kuenstlerList) {
         imagesList.add(ResHelper.getResId("kuenstler_profile_" + kuenstler, R.drawable.class));
      }
      return imagesList;
   }

   private List<String> getKuenstlerList(){
      String[] kuenstlers = getResources().getStringArray(kuenstlerArray);

      List<String> kuenstlerList = new ArrayList<>();
      Collections.addAll(kuenstlerList, kuenstlers);
      return kuenstlerList;
   }

}
