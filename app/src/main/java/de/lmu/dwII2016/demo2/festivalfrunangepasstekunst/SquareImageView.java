package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView
{
   public SquareImageView(Context context)
   {
      super(context);
   }

   public SquareImageView(Context context, AttributeSet attrs)
   {
      super(context, attrs);
   }

   public SquareImageView(Context context, AttributeSet attrs, int defStyle)
   {
      super(context, attrs, defStyle);
   }


   @TargetApi (Build.VERSION_CODES.LOLLIPOP)
   public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr,
         int defStyleRes) {
      super(context, attrs, defStyleAttr, defStyleRes);
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
   {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
   }
}