package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
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

public class FestivalOverviewWerkeFragment extends TabWallFragment implements
      WallImageWerkeAdapter.OnItemClickListener{

   private int kunstwerkeArray;

   @Bind(R.id.card_recycler_view)
   RecyclerView recyclerView;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kunstwerkeArray = getArguments().getInt("WERKE_ARRAY");
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

      WallImageWerkeAdapter
            adapter = new WallImageWerkeAdapter(FestivalOverviewWerkeFragment.this, getWerkeArray());
      adapter.setItemClickListener(this);
      recyclerView.setAdapter(adapter);
   }

   private List<Integer> getWerkeArray(){
      String[] werke = getResources().getStringArray(kunstwerkeArray);

      List<Integer> imagesArray = new ArrayList<Integer>();
      for (String werk : werke) {
         imagesArray.add(getResId("werk_" + werk, R.drawable.class));
      }
      return imagesArray;
   }
   @Override
   public void onItemClick(View item) {
      Intent intent = new Intent(getActivity(),  WerkDetailActivity.class);
      intent.putExtra("IMAGE", (Integer)item.getTag());
      getActivity().startActivity(intent);
   }

}
