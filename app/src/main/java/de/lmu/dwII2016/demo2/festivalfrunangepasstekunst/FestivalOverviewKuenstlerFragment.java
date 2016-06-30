package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FestivalOverviewKuenstlerFragment extends TabWallFragment {

//   private final Integer kuenstler[] = {
//         R.drawable.kunstwerke_08,
//         R.drawable.kunstwerke_12,
//         R.drawable.kunstwerke_16,
//         R.drawable.kunstwerke_22,
//         R.drawable.kunstwerke_26,
//         R.drawable.kunstwerke_37,
//         R.drawable.kunstwerke_08,
//         R.drawable.kunstwerke_12,
//         R.drawable.kunstwerke_16,
//         R.drawable.kunstwerke_22,
//         R.drawable.kunstwerke_26,
//         R.drawable.kunstwerke_37
//   };
   private int kuenstlerArray;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kuenstlerArray = getArguments().getInt("KUENSTLER_ARRAY");
      View view = super.onCreateView(inflater, container, savedInstanceState);
      System.out.println("kunstlerArray2: " + kuenstlerArray);
      setImagesArray(kuenstlerArray);
      return view;
   }

   @Override
   protected int getImagesArray() {
      return kuenstlerArray;
   }
}