package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
   private Activity context;

   public KunstwerkAdapter(Activity context,ArrayList<Integer> kunstwerke) {
      this.kunstwerke = kunstwerke;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//      Context context = parent.getContext();
//      context.getLayoutInflater().inflate()
      if (viewType == TYPE_ITEM) {
         View view = context.getLayoutInflater().inflate(R.layout.item_kunstwerk, parent, false);
         return new ItemViewHolder(view);
      } else if (viewType == TYPE_HEADER) {
         View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_header, parent, false);
         return new HeaderViewHolder(view);
      }
      throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
      if (!isPositionHeader(position)) {
         ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
         Picasso.with(context).load(kunstwerke.get(position - 2)).into(itemViewHolder.imgKunstwerk);
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
}
