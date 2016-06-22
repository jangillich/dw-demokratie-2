package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private String last_demokratie_image;
    private String last_macht_image;
    private String last_partizipation_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekend_select);
        ButterKnife.bind(this);
        initImageSwitcher(demokratieSwitcher, 1, R.array.demokratie_kunstwerke);
        // TODO: change arrays
        initImageSwitcher(machtSwitcher, 2, R.array.demokratie_kunstwerke);
        initImageSwitcher(partizipationSwitcher, 3, R.array.demokratie_kunstwerke);
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
//                Picasso.with(WeekendSelectActivity.this).load(getRandomImage(festival, arrayRes))
//                      .into(switcher.getvie());
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
        getMenuInflater().inflate(R.menu.weekend_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
