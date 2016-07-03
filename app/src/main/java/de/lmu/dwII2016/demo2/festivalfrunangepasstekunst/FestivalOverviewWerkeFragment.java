package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FestivalOverviewWerkeFragment extends TabWallFragment {

   private int kunstwerke;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kunstwerke = getArguments().getInt("WERKE_ARRAY");
      View rootView = super.onCreateView(inflater, container, savedInstanceState);
      setImagesArray(kunstwerke);
      return rootView;
   }

   @Override
   protected int getImagesArray() {
      return kunstwerke;
   }


   @Override
   public void onItemClick(View item) {
      Intent intent = new Intent(getActivity(),  WerkDetailActivity.class);
      intent.putExtra("IMAGE", (Integer)item.getTag());
      getActivity().startActivity(intent);
   }

}
