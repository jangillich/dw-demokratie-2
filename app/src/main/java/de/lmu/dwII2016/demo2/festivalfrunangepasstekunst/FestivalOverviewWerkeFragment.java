package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FestivalOverviewWerkeFragment extends TabWallFragment {

   private int kuenstlerArrayId;
   private List<Integer> werkeArray = new ArrayList<>();
   private List<String> kuenstlerArray = new ArrayList<>();

   @Bind(R.id.card_recycler_view)
   RecyclerView recyclerView;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kuenstlerArrayId = getArguments().getInt("KUENSTLER_ARRAY");
      View rootView = super.onCreateView(inflater, container, savedInstanceState);
      ButterKnife.bind(this, rootView);
      initializeWerkeArray();
      initViews();
      return rootView;
   }

   private void initViews() {
      recyclerView.setHasFixedSize(true);
      StaggeredGridLayoutManager
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(layoutManager);

      WallImageWerkeAdapter
            adapter = new WallImageWerkeAdapter(FestivalOverviewWerkeFragment.this, werkeArray, kuenstlerArray);
      recyclerView.setAdapter(adapter);
   }

   private void initializeWerkeArray(){
      String[] kuenstlers = getResources().getStringArray(kuenstlerArrayId);
      Field[] fields = R.drawable.class.getFields();
      for (String kuenstler : kuenstlers) {
         for (Field field : fields) {
            if (field.getName().startsWith(kuenstler + "_")) {
               try {
                  werkeArray.add(field.getInt(null));
                  kuenstlerArray.add(kuenstler);
               } catch (IllegalAccessException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }
}
