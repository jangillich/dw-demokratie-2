package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectFestivalMachtFragment extends Fragment {

   private int machtKuenstler = R.array.kuenstler_festival_2;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_select_festival_macht, container, false);
      initOnClickListener(v);
      return v;
   }
   private void initOnClickListener(View v) {
      v.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            Intent intent = new Intent(getActivity(),  FestivalOverviewActivity.class);
            intent.putExtra("FESTIVAL", 2);
            intent.putExtra("KUENSTLER_ARRAY", machtKuenstler);
            startActivity(intent);
         }
      });
   }
}
