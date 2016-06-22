package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DemokratiePagerAdapter extends FragmentStatePagerAdapter {
   int mNumOfTabs;

   public DemokratiePagerAdapter(FragmentManager fm, int NumOfTabs) {
      super(fm);
      this.mNumOfTabs = NumOfTabs;
   }

   @Override
   public Fragment getItem(int position) {

      switch (position) {
         case 0:
            KunstwerkeOverviewFragment tab1 = new KunstwerkeOverviewFragment();
            return tab1;
         case 1:
            KuenstlerOverviewFragment tab2 = new KuenstlerOverviewFragment();
            return tab2;
         default:
            return null;
      }
   }

   @Override
   public int getCount() {
      return mNumOfTabs;
   }
}