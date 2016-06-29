package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewAnimationUtils {

   public static void collapse(final View collapseView) {


      final int startHeightCollapse = collapseView.getHeight();
      collapseView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

      final Animation animationCollapse = new Animation() {
         @Override
         protected void applyTransformation(float interpolatedTime, Transformation t) {
            ViewGroup.LayoutParams params  = collapseView.getLayoutParams();
            params.height = (int) (startHeightCollapse - (startHeightCollapse * interpolatedTime));
            collapseView.setLayoutParams(params);
            if(interpolatedTime == 1) {
               collapseView.setVisibility(View.GONE);
            }
         }

         @Override
         public boolean willChangeBounds() {
            return true;
         }
      };
      animationCollapse.setDuration(300);

      collapseView.setAnimation(animationCollapse);
      collapseView.startAnimation(animationCollapse);
   }

}