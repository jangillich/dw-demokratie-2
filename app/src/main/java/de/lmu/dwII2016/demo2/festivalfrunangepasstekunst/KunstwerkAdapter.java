package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bianka.roppelt on 23/06/16.
 */
public class KunstwerkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img_kunstwerk)
      ImageView imgKunstwerk;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }

   public class HeaderViewHolder extends RecyclerView.ViewHolder {
      public HeaderViewHolder(View itemView) {
         super(itemView);
      }
   }

   private static final int TYPE_HEADER = 2;
   private static final int TYPE_ITEM = 1;

   private ArrayList<Integer> kunstwerke;
   private Fragment context;

   public KunstwerkAdapter(Fragment context,ArrayList<Integer> kunstwerke) {
      this.kunstwerke = kunstwerke;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if (viewType == TYPE_ITEM) {
         View view = context.getActivity().getLayoutInflater().inflate(R.layout.item_kunstwerk, parent, false);
         ItemViewHolder viewHolder = new ItemViewHolder(view);
         return viewHolder;
      } else if (viewType == TYPE_HEADER) {
         View view = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_recycler_header, parent, false);
         return new HeaderViewHolder(view);
      }
      throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
      if (!isPositionHeader(position)) {
         ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
//         itemViewHolder.imgKunstwerk.setImageDrawable(ImageHelper.decodeSampledBitmapFromResource(context.getResources(), kunstwerke.get(position - 2), 150, 150));
         //
         ((KunstwerkeOverviewFragment)context).loadBitmap(kunstwerke.get(position - 2), itemViewHolder.imgKunstwerk);
//         loadBitmap(context, kunstwerke.get(position - 2), itemViewHolder.imgKunstwerk);
//         Picasso.with(context).load(kunstwerke.get(position - 2)).into(itemViewHolder.imgKunstwerk);
      }
   }

   @Override
   public int getItemCount() {
      return kunstwerke.size() + 2;
   }

   @Override
   public int getItemViewType(int position) {
      if (isPositionHeader(position)) {
         return TYPE_HEADER;
      }
      return TYPE_ITEM;
   }

   private boolean isPositionHeader(int position) {
      return position == 0 || position == 1;
   }


//   public void loadBitmap(Context context, int resId, ImageView imageView) {
//      final String imageKey = String.valueOf(resId);
//
//      final Bitmap bitmap = KunstwerkeOverviewFragment.getBitmapFromMemCache(imageKey);
//      if (bitmap != null) {
//         imageView.setImageBitmap(bitmap);
//      } else {
//         imageView.setImageResource(R.drawable.ic_launcher);
//         KunstwerkeOverviewFragment.BitmapWorkerTask task = new KunstwerkeOverviewFragment.BitmapWorkerTask(context, imageView);
//         task.execute(resId);
//      }
//   }


}
