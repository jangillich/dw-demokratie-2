package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WerkViewItem extends ImageView {

   private String kuenstlerName;
   private String werkTitle;
   private boolean quadratic = false;

   public WerkViewItem(Context context) {
      super(context);
   }

   public WerkViewItem(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public WerkViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }
   public String getKuenstlerName() {
      return  kuenstlerName;
   }

   public void setKuenstlerName(String kuenstlerName) {
      this.kuenstlerName = kuenstlerName;
   }

   public String getWerkTitle() {
      return  werkTitle;
   }

   public void setWerkTitle(String werkTitle) {
      this.werkTitle = werkTitle;
   }


   public void setQuadratic() {
      this.quadratic = true;
      setScaleType(ScaleType.CENTER_CROP);
      requestLayout();
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      if(quadratic) {
         setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
      }
   }

}
