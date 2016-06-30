package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.support.v4.app.Fragment;
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
public class WallImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      @Bind(R.id.img)
      ImageView img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
         view.setOnClickListener(this);
      }

      @Override
      public void onClick(View v) {
         Intent intent = new Intent(context.getActivity(),  WerkDetailActivity.class);
         intent.putExtra("IMAGE", (Integer)img.getTag());
         context.getActivity().startActivity(intent);
      }
   }


   private List<Integer> images;
   private Fragment context;

   public WallImageAdapter(Fragment context, List<Integer> images) {
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
      ((ItemViewHolder) viewHolder).img.setTag(images.get(position));
      ((TabWallFragment)context).loadBitmap(images.get(position), itemViewHolder.img);
//      itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//
//         }
//      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
