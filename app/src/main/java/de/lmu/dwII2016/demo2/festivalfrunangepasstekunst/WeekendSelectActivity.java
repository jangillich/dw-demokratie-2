package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeekendSelectActivity extends AppCompatActivity {

    @Bind(R.id.festival_view_pager)
    ViewPager festivalViewPager;
    @Bind(R.id.vp_indicators)
    RadioGroup vpIndicators;
    @Bind(R.id.vp_indicator1)
    RadioButton vpIndicator1;
    @Bind(R.id.vp_indicator2)
    RadioButton vpIndicator2;
    @Bind(R.id.vp_indicator3)
    RadioButton vpIndicator3;

//    @Bind(R.id.notification_container)
//    CardView notificationContainer;
//    @Bind(R.id.notification_button_ok)
//    Button notificationButtonOk;
//    @Bind(R.id.notification_button_more)
//    Button notificationButtonMore;
//

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String NotificationClickedAway = "notAway";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekend_select);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        initNotification();
        initViewPager();
    }
    private List<Fragment> getViewPagerFraments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(new FestivalViewPagerDemokratieFragment());
        fList.add(new FestivalViewPagerMachtFragment());
        fList.add(new FestivalViewPagerPartizipationFragment());
        return fList;
    }

    private void initViewPager() {
        FestivalViewPagerAdapter adapter = new FestivalViewPagerAdapter(getSupportFragmentManager(), getViewPagerFraments());

        festivalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        vpIndicators.check(vpIndicator1.getId());
                        break;
                    case 1:
                        vpIndicators.check(vpIndicator2.getId());
                        break;
                    case 2:
                        vpIndicators.check(vpIndicator3.getId());
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        festivalViewPager.setAdapter(adapter);
        festivalViewPager.setCurrentItem(0);
    }

    private void initNotification() {
        boolean showNotification = !sharedpreferences.getBoolean(NotificationClickedAway, false);
        if(showNotification) {
//            notificationContainer.setVisibility(View.VISIBLE);
//            notificationButtonOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    notificationContainer.animate()
//                          .translationX(getResources().getDisplayMetrics().widthPixels)
//                          .alpha(1.0f)
//                          .setDuration(500)
//                          .withEndAction(new Runnable() {
//                              @Override
//                              public void run() {
//                                  notificationContainer.setVisibility(View.GONE);
//                                  saveHideNotificationButton();
//                              }
//                          }).start();
//                }
//            });
//            notificationButtonMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO: go to festival page
//                    saveHideNotificationButton();
//                }
//            });
        }
    }

    private void saveHideNotificationButton() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(NotificationClickedAway, true);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weekend_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

}
