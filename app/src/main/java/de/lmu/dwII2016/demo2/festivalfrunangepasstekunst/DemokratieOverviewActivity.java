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
      setContentView(R.layout.activity_demokratie_overview);
      ButterKnife.bind(this);

      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      tabLayout.addTab(tabLayout.newTab()
            .setText(getResources().getText(R.string.tab_kunstwerke)));
      tabLayout.addTab(tabLayout.newTab()
            .setText(getResources().getText(R.string.tab_kuenstler)));
      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

      DemokratiePagerAdapter adapter =
            new DemokratiePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
