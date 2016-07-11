package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.ArtistViewAdapter;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;

public class FestivalOverviewKuenstlerFragment extends Fragment {

   private ArtistViewAdapter mAdapter;

   private int kuenstlerArray;

   @Bind (R.id.card_recycler_view)
   RecyclerView recyclerView;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      kuenstlerArray = getArguments().getInt("KUENSTLER_ARRAY");

      mAdapter =
            new ArtistViewAdapter(getActivity(), getKuenstlerProfilesList(), getKuenstlerList());
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
   }

   @Override
   public void onResume() {
      super.onResume();
      mAdapter.notifyDataSetChanged();
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
