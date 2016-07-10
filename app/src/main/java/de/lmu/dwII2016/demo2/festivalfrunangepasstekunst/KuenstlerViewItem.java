package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class KuenstlerViewItem extends RecyclingImageView {

   private String kuenstlerName;

   public KuenstlerViewItem(Context context) {
      super(context);
   }

   public KuenstlerViewItem(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public String getKuenstlerName() {
      return  kuenstlerName;
   }

   public void setKuenstlerName(String kuenstlerName) {
      this.kuenstlerName = kuenstlerName;
   }

}
