package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.FestivalOverviewKuenstlerFragment;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.FestivalOverviewWerkeFragment;

public class FestivalOverviewPagerAdapter extends FragmentStatePagerAdapter {
   int mNumOfTabs;
   int kuenstlerArray;

   public FestivalOverviewPagerAdapter(FragmentManager fm, int NumOfTabs, int kuenstlerArray) {
      super(fm);
      this.mNumOfTabs = NumOfTabs;
      this.kuenstlerArray = kuenstlerArray;
   }

   @Override
   public Fragment getItem(int position) {

      switch (position) {
         case 0:

//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(android.R.id.content, new FestivalOverviewWerkeFragment(), TAG);
//            ft.commit();
            FestivalOverviewWerkeFragment tab1 = new FestivalOverviewWerkeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("KUENSTLER_ARRAY", kuenstlerArray);
            tab1.setArguments(bundle1);
            return tab1;
         case 1:
            FestivalOverviewKuenstlerFragment tab2 = new FestivalOverviewKuenstlerFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("KUENSTLER_ARRAY", kuenstlerArray);
            tab2.setArguments(bundle2);
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