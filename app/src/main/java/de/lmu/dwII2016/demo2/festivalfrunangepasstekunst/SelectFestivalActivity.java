package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters.SelectFestivalPagerAdapter;

public class SelectFestivalActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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

    @Bind(R.id.notification_container)
    CardView notificationContainer;
    @Bind(R.id.notification_button_ok)
    Button notificationButtonOk;
    @Bind(R.id.notification_button_more)
    Button notificationButtonMore;
    @Bind(R.id.notification_shadow)
    View notificationShadow;


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String NotificationClickedAway = "notAway";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_festival);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        initNotification();
        initViewPager();
    }
    private List<Fragment> getViewPagerFraments() {
        List<Fragment> fList = new ArrayList<>();
        fList.add(new SelectFestivalDemokratieFragment());
        fList.add(new SelectFestivalMachtFragment());
        fList.add(new SelectFestivalPartizipationFragment());
        return fList;
    }

    private void initViewPager() {
        SelectFestivalPagerAdapter adapter = new SelectFestivalPagerAdapter(getSupportFragmentManager(), getViewPagerFraments());

        festivalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            notificationContainer.setVisibility(View.VISIBLE);
            notificationShadow.setVisibility(View.VISIBLE);
            notificationButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveHideNotificationButton();
                    ViewAnimationUtils.collapse(notificationContainer);
                    notificationShadow.setVisibility(View.GONE);
                }
            });
            notificationButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveHideNotificationButton();
                    notificationShadow.setVisibility(View.GONE);
                    notificationContainer.setVisibility(View.GONE);
                    Intent intent = new Intent(SelectFestivalActivity.this,  EventInfosActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void saveHideNotificationButton() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(NotificationClickedAway, true);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_festival, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_anfahrt) {
            Intent intent = new Intent(SelectFestivalActivity.this,  AnfahrtActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_festival) {
            Intent intent = new Intent(SelectFestivalActivity.this,  EventInfosActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_impressum) {
            Intent intent = new Intent(SelectFestivalActivity.this,  ImpressumActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
