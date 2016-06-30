package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by bianka.roppelt on 29/06/16.
 */
public class SelectFestivalPagerAdapter extends FragmentPagerAdapter {

   private List<Fragment> fragments;

   public SelectFestivalPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
   }
   @Override
   public Fragment getItem(int position) {
      return this.fragments.get(position);
   }
   @Override
   public int getCount() {
      return this.fragments.size();
   }
}