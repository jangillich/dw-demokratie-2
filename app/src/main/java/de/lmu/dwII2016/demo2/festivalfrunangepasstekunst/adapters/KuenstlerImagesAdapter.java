package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.KuenstlerDetailActivity;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.R;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.WerkDetailActivity;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.WerkViewItem;

public class KuenstlerImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      WerkViewItem img;
      @Bind(R.id.img_selector)
      ImageView imgSelector;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
         img.setQuadratic();
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
      view.setPadding(0,0,0,0);
      return new ItemViewHolder(view);
   }

   @Override
   public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
      final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      context.loadBitmap(images.get(position), itemViewHolder.img);
      itemViewHolder.imgSelector.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context,  WerkDetailActivity.class);
            intent.putExtra("IMAGE", images.get(viewHolder.getAdapterPosition()));
            intent.putExtra("KUENSTLER_NAME", kuenstler);
            intent.putExtra("WERK_TITLE", itemViewHolder.img.getWerkTitle());
            context.startActivity(intent);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}