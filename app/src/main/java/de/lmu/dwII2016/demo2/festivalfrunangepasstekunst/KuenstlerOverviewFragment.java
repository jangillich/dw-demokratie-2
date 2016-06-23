package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KuenstlerOverviewFragment extends Fragment {

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_demokratie_kunstwerke, container, false);
   }
}
