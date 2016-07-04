package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bianka.roppelt on 29/06/16.
 */
public class SelectFestivalDemokratieFragment extends Fragment {

   private int demokratieKuenstler = R.array.kuenstler_festival_1;
   private int demokratieWerke = R.array.demokratie_werke;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_select_festival_demokratie, container, false);
      initOnClickListener(v);
      return v;
   }
   private void initOnClickListener(View v) {
      v.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(getActivity(),  FestivalOverviewActivity.class);
            intent.putExtra("FESTIVAL", 1);
            intent.putExtra("KUENSTLER_ARRAY", demokratieKuenstler);
            intent.putExtra("WERKE_ARRAY", demokratieWerke);
            startActivity(intent);
         }
      });
   }
}
