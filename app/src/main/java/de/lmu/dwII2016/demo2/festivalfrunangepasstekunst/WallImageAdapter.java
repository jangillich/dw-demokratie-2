package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bianka.roppelt on 23/06/16.
 */
public class WallImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      ImageView img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }


   private ArrayList<Integer> images;
   private Fragment context;

   public WallImageAdapter(Fragment context,ArrayList<Integer> images) {
      this.images = images;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = context.getActivity().getLayoutInflater().inflate(R.layout.item_wall_image, parent, false);
      ItemViewHolder viewHolder = new ItemViewHolder(view);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      ((TabWallFragment)context).loadBitmap(images.get(position), itemViewHolder.img);
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
