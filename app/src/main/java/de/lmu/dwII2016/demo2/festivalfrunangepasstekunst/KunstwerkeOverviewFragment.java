package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KunstwerkeOverviewFragment extends TabWallFragment {


   private final Integer kunstwerke[] = {
         R.drawable.kunstwerke_08,
         R.drawable.kunstwerke_12,
         R.drawable.kunstwerke_16,
         R.drawable.kunstwerke_22,
         R.drawable.kunstwerke_26
   };

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = super.onCreateView(inflater, container, savedInstanceState);
      setImages(kunstwerke);
      return rootView;
   }

   @Override
   protected Integer[] getImages() {
      return kunstwerke;
   }

}
