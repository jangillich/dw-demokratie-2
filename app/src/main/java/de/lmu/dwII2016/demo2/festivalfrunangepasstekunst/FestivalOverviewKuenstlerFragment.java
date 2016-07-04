package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
      recyclerView.setHasFixedSize(true);
      StaggeredGridLayoutManager
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(layoutManager);

      WallImageKuenstlerAdapter
            adapter = new WallImageKuenstlerAdapter(FestivalOverviewKuenstlerFragment.this, getKuenstlerProfilesList(), getKuenstlerList());
      recyclerView.setAdapter(adapter);
   }

   private List<Integer> getKuenstlerProfilesList(){
      String[] kuenstlerList = getResources().getStringArray(kuenstlerArray);

      List<Integer> imagesList = new ArrayList<Integer>();
      for (String kuenstler : kuenstlerList) {
         imagesList.add(getResId("kuenstler_profile_" + kuenstler, R.drawable.class));
      }
      return imagesList;
   }

   private List<String> getKuenstlerList(){
      String[] kuenstlers = getResources().getStringArray(kuenstlerArray);

      List<String> kuenstlerList = new ArrayList<String>();
      for (String kuenstler : kuenstlers) {
         kuenstlerList.add(kuenstler);
      }
      return kuenstlerList;
   }

}
