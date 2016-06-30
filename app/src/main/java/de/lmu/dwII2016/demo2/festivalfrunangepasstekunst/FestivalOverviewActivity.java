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

public class FestivalOverviewActivity extends AppCompatActivity {

   @Bind(R.id.pager)
   ViewPager viewPager;
   @Bind(R.id.tab_layout)
   TabLayout tabLayout;
   @Bind(R.id.toolbar)
   Toolbar toolbar;

   private int festival;
   private int werkeArray;
   private int kuenstlerArray;

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_festival_overview, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();

      if (id == R.id.action_events) {
         return true;
      } else if (id == R.id.action_anfahrt) {
         return true;
      } else if (id == R.id.action_festival) {
         return true;
      } else if (id == R.id.action_gruender) {
         return true;
      } else if (id == R.id.action_impressum) {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_festival_overview);
      ButterKnife.bind(this);

      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         festival = extras.getInt("FESTIVAL");
         kuenstlerArray = extras.getInt("KUENSTLER_ARRAY");
         werkeArray = extras.getInt("WERKE_ARRAY");
         //The key argument here must match that used in the other activity
      }
      initToolbar();
   }

   private void initToolbar() {

      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      tabLayout.addTab(tabLayout.newTab()
            .setText(getResources().getText(R.string.tab_kunstwerke)));
      tabLayout.addTab(tabLayout.newTab()
            .setText(getResources().getText(R.string.tab_kuenstler)));
      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
      switch (festival) {
         case 2:
            setTitle(R.string.name_festival_2);
            // TODO: set different navigation icon
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_indicator_macht));
            break;
         case 3:
            setTitle(R.string.name_festival_3);
            // TODO: set different navigation icon
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_indicator_partizipation));
            break;
         case 1:
         default:
            setTitle(R.string.name_festival_1);
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_indicator_demokratie));
            toolbar.setNavigationIcon(R.drawable.ic_launcher);
      }

      FestivalOverviewPagerAdapter adapter =
            new FestivalOverviewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), werkeArray, kuenstlerArray);
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
   }

}
