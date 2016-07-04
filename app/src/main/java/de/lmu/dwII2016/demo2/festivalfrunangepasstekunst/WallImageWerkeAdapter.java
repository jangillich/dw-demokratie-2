package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bianka.roppelt on 23/06/16.
 */
public class WallImageWerkeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   public interface OnItemClickListener {
      void onItemClick(View item);
   }

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      ImageView img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }


   private List<Integer> images;
   private TabWallFragment context;
   private OnItemClickListener itemClickListener;

   public WallImageWerkeAdapter(TabWallFragment context, List<Integer> images) {
      this.images = images;
      this.context = context;
   }

   public void setItemClickListener(OnItemClickListener itemClickListener) {
      this.itemClickListener = itemClickListener;
   }
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = context.getActivity().getLayoutInflater().inflate(R.layout.item_wall_image_werk, parent, false);
      ItemViewHolder viewHolder = new ItemViewHolder(view);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      context.loadBitmap(images.get(position), itemViewHolder.img);

      itemViewHolder.img.setTag(images.get(position));
      itemViewHolder.img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            itemClickListener.onItemClick(v);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
