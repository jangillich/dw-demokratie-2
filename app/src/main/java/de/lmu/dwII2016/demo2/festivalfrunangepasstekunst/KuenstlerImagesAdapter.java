package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KuenstlerImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      WerkViewItem img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }


   private List<Integer> images;
   private String kuenstler;
   private KuenstlerDetailActivity context;

   public KuenstlerImagesAdapter(KuenstlerDetailActivity context, List<Integer> images, String kuenstler) {
      this.images = images;
      this.kuenstler = kuenstler;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = context.getLayoutInflater().inflate(R.layout.item_wall_image_werk, parent, false);
      return new ItemViewHolder(view);
   }

   @Override
   public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
      ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      context.loadBitmap(images.get(position), itemViewHolder.img);
      itemViewHolder.img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context,  WerkDetailActivity.class);
            intent.putExtra("IMAGE", images.get(viewHolder.getAdapterPosition()));
            intent.putExtra("KUENSTLER_NAME", kuenstler);
            intent.putExtra("WERK_TITLE", ((WerkViewItem) v).getWerkTitle());
            context.startActivity(intent);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
