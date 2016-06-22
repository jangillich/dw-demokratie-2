package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemokratieOverviewActivity extends AppCompatActivity {

//   public static class PlaceholderFragment extends Fragment {
//
//      private static final String ARG_SECTION_NUMBER = "section_number";
//
//      public static PlaceholderFragment newInstance(int sectionNumber) {
//         PlaceholderFragment fragment = new PlaceholderFragment();
//         Bundle args = new Bundle();
//         args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//         fragment.setArguments(args);
//         return fragment;
//      }
//
//      public PlaceholderFragment() {
//      }
//
//      @Override
//      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//         View rootView = inflater.inflate(R.layout.fragment_demokratie_overview, container, false);
//         TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//         textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//         return rootView;
//      }
//   }

//   public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//      public SectionsPagerAdapter(FragmentManager fm) {
//         super(fm);
//      }
//
//      @Override
//      public int getCount() {
//         return 2;
//      }
//
//      @Override
//      public Fragment getItem(int position) {
//         switch (position) {
//            case 0:
//               return PlaceholderFragment.newInstance(position + 1);
//            case 1:
//               return PlaceholderFragment.newInstance(position + 1);
//         }
//         return null;
//      }
//
//      @Override
//      public CharSequence getPageTitle(int position) {
//         switch (position) {
//            case 0:
//               return "KUNSTWERKE";
//            case 1:
//               return "KÜNSTLER";
//         }
//         return null;
//      }
//   }

//   private SectionsPagerAdapter mSectionsPagerAdapter;
   @Bind(R.id.pager)
   ViewPager viewPager;
   @Bind(R.id.tab_layout)
   TabLayout tabLayout;
   @Bind(R.id.toolbar)
   Toolbar toolbar;

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_demokratie_overview, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.action_settings) {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

//   @Override
//   public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
//      mViewPager.setCurrentItem(tab.getPosition());
//   }
//
//   @Override
//   public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
//
//   }
//
//   @Override
//   public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
//
//   }
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demokratie_overview);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);

      tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.tab_kunstwerke)));
      tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.tab_kuenstler)));
      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

      DemokratiePagerAdapter adapter = new DemokratiePagerAdapter
            (getSupportFragmentManager(), tabLayout.getTabCount());
      viewPager.setAdapter(adapter);
      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
         }

         @Override
         public void onTabUnselected(TabLayout.Tab tab) {

         }

         @Override
         public void onTabReselected(TabLayout.Tab tab) {

         }
      });

//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////      getSupportActionBar().setIcon(R.drawable.ic_launcher);
//      getSupportActionBar().setElevation(R.dimen.cardview_default_elevation);
//      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
//      // Create the adapter that will return a fragment for each of the three
//      // primary sections of the activity.
//      mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
//
//
//      mViewPager.setAdapter(mSectionsPagerAdapter);
//
//      // Set up the action bar.
//      final ActionBar actionBar = getSupportActionBar();
//      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//      mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//         @Override
//         public void onPageSelected(int position) {
//            actionBar.setSelectedNavigationItem(position);
//         }
//      });
//
//      // For each of the sections in the app, add a tab to the action bar.
//      for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//         actionBar.addTab(actionBar.newTab()
//               .setText(mSectionsPagerAdapter.getPageTitle(i))
//               .setTabListener(this));
//      }
   }

//   private void setupActionBar() {
//
//      ActionBar actionBar = getSupportActionBar();
//      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//      actionBar.setDisplayShowTitleEnabled(true);
//
//      ActionBar.Tab tab1 = actionBar
//            .newTab()
//            .setText("First")
//            .setIcon(R.drawable.ic_home)
//            .setTabListener(
//                  new FragmentTabListener<FirstFragment>(R.id.flContainer, this, "first",
//                        FirstFragment.class));
//
//      actionBar.addTab(tab1);
//      actionBar.selectTab(tab1);
//
//      Tab tab2 = actionBar
//            .newTab()
//            .setText("Second")
//            .setIcon(R.drawable.ic_mentions)
//            .setTabListener(
//                  new FragmentTabListener<SecondFragment>(R.id.flContainer, this, "second",
//                        SecondFragment.class));
//
//      actionBar.addTab(tab2);
//   }
}
