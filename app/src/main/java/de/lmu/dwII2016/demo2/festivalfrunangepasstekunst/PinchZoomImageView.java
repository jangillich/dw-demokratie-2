package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jan on 10.07.2016.
 */
public class PinchZoomImageView extends RecyclingImageView implements View.OnTouchListener {


    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    Matrix initialMatrix = new Matrix ();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    float lastDragX = 0;
    float lastDragY = 0;
    float dragDist = 0f;

    double scaleFactor = 1.0;

    public PinchZoomImageView(Context context) {
        this(context, null, 0);
    }

    public PinchZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinchZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnTouchListener(this);

        post(new Runnable() {
            @Override
            public void run() {
                if(getDrawable() != null) {
                    RectF drawableRect = new RectF(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
                    RectF viewRect = new RectF(0, 0, getWidth(), getHeight());
                    if(drawableRect.width() != 0 && drawableRect.height() != 0) {
                        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
                        initialMatrix.set(matrix);
                        setImageMatrix(matrix);
                    }
                }
            }
        });

    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if(drawable != null) {
            RectF drawableRect = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            RectF viewRect = new RectF(0, 0, getWidth(), getHeight());
            if(drawableRect.width() != 0 && drawableRect.height() != 0) {
                matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
                initialMatrix.set(matrix);
                setImageMatrix(matrix);
            }
        }
        super.setImageDrawable(drawable);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                lastDragX = event.getX();
                lastDragY = event.getY();
                dragDist = 0;
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                if (dragDist == 0) {
                    callOnClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = lastDragX - event.getX();
                float dy = lastDragY - event.getY();
                dragDist = (float) Math.sqrt((double) dx * dx + dy * dy);
                lastDragX = event.getX();
                lastDragY = event.getY();
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        scaleFactor = scaleFactor * scale;

                        matrix.postScale(scale, scale, mid.x, mid.y);
                        float[] matrixArray = new float[9];
                        matrix.getValues(matrixArray);
                        if(matrixArray[0] < 1.0)
                            matrix.set(initialMatrix);
                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        return true;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
