package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeekendSelectActivity extends AppCompatActivity {

    @Bind(R.id.demokratie_images)
    ImageSwitcher demokratieSwitcher;
    @Bind(R.id.macht_images)
    ImageSwitcher machtSwitcher;
    @Bind(R.id.partizipation_images)
    ImageSwitcher partizipationSwitcher;
    @Bind(R.id.demokratie_view)
    View demokratieView;
    @Bind(R.id.macht_view)
    View machtView;
    @Bind(R.id.partizipation_view)
    View partizipationView;

    @Bind(R.id.notification_container)
    CardView notificationContainer;
    @Bind(R.id.notification_button_ok)
    Button notificationButtonOk;
    @Bind(R.id.notification_button_more)
    Button notificationButtonMore;

    private String last_demokratie_image;
    private String last_macht_image;
    private String last_partizipation_image;

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
        initImageSwitcher(demokratieSwitcher, 1, R.array.demokratie_kunstwerke);
        // TODO: change arrays
        initImageSwitcher(machtSwitcher, 2, R.array.demokratie_kunstwerke);
        initImageSwitcher(partizipationSwitcher, 3, R.array.demokratie_kunstwerke);
        initOnClickListener();
    }

    private void initOnClickListener() {
        demokratieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeekendSelectActivity.this,  DemokratieOverviewActivity.class);
                startActivity(intent);
            }
        });
        machtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        partizipationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initNotification() {
        boolean showNotification = !sharedpreferences.getBoolean(NotificationClickedAway, false);
        if(showNotification) {
            notificationContainer.setVisibility(View.VISIBLE);
            notificationButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationContainer.animate()
                          .translationX(getResources().getDisplayMetrics().widthPixels)
                          .alpha(1.0f)
                          .setDuration(500)
                          .withEndAction(new Runnable() {
                              @Override
                              public void run() {
                                  notificationContainer.setVisibility(View.GONE);
                                  saveHideNotificationButton();
                              }
                          }).start();
                }
            });
            notificationButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: go to festival page
                    saveHideNotificationButton();
                }
            });
        }
    }

    private void saveHideNotificationButton() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(NotificationClickedAway, true);
        editor.apply();
    }

    private void initImageSwitcher(final ImageSwitcher switcher, final int festival, final int arrayRes) {

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                      LinearLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Animation animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        switcher.setInAnimation(animIn);
        switcher.setOutAnimation(animOut);
        switcher.postDelayed(new Runnable() {
            public void run() {
                switcher.setImageDrawable(getRandomImage(festival, arrayRes));
                switcher.postDelayed(this, 5000);
            }
        }, 5000);
    }

    private Drawable getRandomImage(int festival, int arrayRes) {
        String[] array = getResources().getStringArray(arrayRes);
        String new_image = array[new Random().nextInt(array.length)];
        switch (festival) {
            case 1:
                while (new_image.equals(last_demokratie_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_demokratie_image = new_image;
                break;
            case 2:
                while (new_image.equals(last_macht_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_macht_image = new_image;
                break;
            case 3:
                while (new_image.equals(last_partizipation_image)) {
                    new_image = array[new Random().nextInt(array.length)];
                }
                last_partizipation_image = new_image;
                break;
            default:
        }
        String kunstwerkName = "kunstwerke_" + new_image;

        final int resourceId = getResources().getIdentifier(kunstwerkName, "drawable", getPackageName());
        return getResources().getDrawable(resourceId);
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
