package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by Jan on 09.07.2016.
 */
public class RandomCropKuenstlerViewItem extends KuenstlerViewItem {

    private static final int MAX_OFFSET = 40;

    private String kuenstlerName;

    public RandomCropKuenstlerViewItem(Context context) {
        super(context);
    }

    public RandomCropKuenstlerViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = createCropPath();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    private Path createCropPath () {
        Random rnd = new Random();

        int offsetTop = rnd.nextInt(MAX_OFFSET/4) + rnd.nextInt(2) * 3 * (MAX_OFFSET/4);
        int offsetBot = MAX_OFFSET - offsetTop;

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getWidth() - offsetTop, 0);
        path.lineTo(getWidth() - offsetBot, getHeight());
        path.lineTo(0, getHeight());
        path.lineTo(0, 0);

        return path;
    }
}

