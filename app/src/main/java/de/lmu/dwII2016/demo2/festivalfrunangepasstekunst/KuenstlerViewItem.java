package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by bianka.roppelt on 04/07/16.
 */
public class KuenstlerViewItem extends ImageView {

   private String kuenstlerName;

   public KuenstlerViewItem(Context context) {
      super(context);
   }

   public KuenstlerViewItem(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public KuenstlerViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   public KuenstlerViewItem(Context context, String kuenstlerName) {
      super(context);
      this.kuenstlerName = kuenstlerName;
   }

   public String getKuenstlerName() {
      return  kuenstlerName;
   }

   public void setKuenstlerName(String kuenstlerName) {
      this.kuenstlerName = kuenstlerName;
   }

}
