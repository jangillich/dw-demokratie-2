package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FestivalOverviewWerkeFragment extends TabWallFragment {


//   private final Integer kunstwerke[] = {
//         R.drawable.kunstwerke_08,
//         R.drawable.kunstwerke_12,
//         R.drawable.kunstwerke_16,
//         R.drawable.kunstwerke_22,
//         R.drawable.kunstwerke_26
//   };

   private int kunstwerke;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kunstwerke = getArguments().getInt("WERKE_ARRAY");
      View rootView = super.onCreateView(inflater, container, savedInstanceState);
      System.out.println("kunstlerArray3: " + kunstwerke);
      setImagesArray(kunstwerke);
      return rootView;
   }

   @Override
   protected int getImagesArray() {
      return kunstwerke;
   }

}
