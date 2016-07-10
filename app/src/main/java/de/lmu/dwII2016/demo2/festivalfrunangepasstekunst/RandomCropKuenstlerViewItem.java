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

    private static final int MAX_OFFSET = 25;

    private String kuenstlerName;

    public RandomCropKuenstlerViewItem(Context context) {
        super(context);
    }

    public RandomCropKuenstlerViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //create a second canvas
        Bitmap mask = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mask);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.demokratie));

        Path path = createCropPath();
        c.drawPath(path, p);

        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); //change transfer mode

        c.drawRect(0, 0, getWidth(), getHeight(), p);

        canvas.drawBitmap(mask, 0, 0, null); //draw the result back onto the canvas

    }

    private Path createCropPath () {
        Random rnd = new Random();

        int offsetTop = rnd.nextInt(MAX_OFFSET/4) + rnd.nextInt(2) * 3 * (MAX_OFFSET/4);
        int offsetBot = MAX_OFFSET - offsetTop;

        Path path = new Path();
        path.moveTo(getWidth(), 0);
        path.lineTo(getWidth() - offsetTop, 0);
        path.lineTo(getWidth() - offsetBot, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), 0);

        return path;
    }
}

