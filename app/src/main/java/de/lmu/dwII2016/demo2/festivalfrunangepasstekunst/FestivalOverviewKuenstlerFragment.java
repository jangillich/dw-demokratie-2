package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FestivalOverviewKuenstlerFragment extends TabWallFragment {

   private int kuenstlerArray;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      kuenstlerArray = getArguments().getInt("KUENSTLER_ARRAY");
      View view = super.onCreateView(inflater, container, savedInstanceState);
      setImagesArray(kuenstlerArray);
      return view;
   }

   @Override
   protected int getImagesArray() {
      return kuenstlerArray;
   }


   @Override
   public void onItemClick(View item) {
      Intent intent = new Intent(getActivity(),  KuenstlerDetailActivity.class);
      intent.putExtra("KUENSTLER", (Integer)item.getTag());
      getActivity().startActivity(intent);
   }

}
