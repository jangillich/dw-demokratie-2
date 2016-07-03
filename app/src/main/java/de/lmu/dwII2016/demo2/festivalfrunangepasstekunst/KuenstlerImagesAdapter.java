package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
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
public class KuenstlerImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      ImageView img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }


   private List<Integer> images;
   private KuenstlerDetailActivity context;

   public KuenstlerImagesAdapter(KuenstlerDetailActivity context, List<Integer> images) {
      this.images = images;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = context.getLayoutInflater().inflate(R.layout.item_wall_image, parent, false);
      ItemViewHolder viewHolder = new ItemViewHolder(view);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      context.loadBitmap(images.get(position), itemViewHolder.img);

      itemViewHolder.img.setTag(images.get(position));
      itemViewHolder.img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context,  WerkDetailActivity.class);
            intent.putExtra("IMAGE", images.get(viewHolder.getAdapterPosition()));
            context.startActivity(intent);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
