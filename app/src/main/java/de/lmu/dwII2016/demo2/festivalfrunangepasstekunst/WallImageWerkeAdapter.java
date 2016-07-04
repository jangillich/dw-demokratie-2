package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WallImageWerkeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.img)
      WerkViewItem img;

      public ItemViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }

   private List<Integer> images;
   private List<String> kuenstlerNames;
   private TabWallFragment context;

   public WallImageWerkeAdapter(TabWallFragment context, List<Integer> images, List<String> kuenstlerNames) {
      this.images = images;
      this.kuenstlerNames = kuenstlerNames;
      this.context = context;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = context.getActivity().getLayoutInflater().inflate(R.layout.item_wall_image_werk, parent, false);
      return new ItemViewHolder(view);
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
      final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
      context.loadBitmap(images.get(position), itemViewHolder.img);

      itemViewHolder.img.setKuenstlerName(kuenstlerNames.get(position));
      itemViewHolder.img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            Intent intent = new Intent(context.getActivity(),  WerkDetailActivity.class);
            intent.putExtra("KUENSTLER_NAME", ((WerkViewItem) v).getKuenstlerName());
            intent.putExtra("WERK_TITLE", ((WerkViewItem) v).getWerkTitle());
            intent.putExtra("IMAGE", images.get(itemViewHolder.getAdapterPosition()));
            context.getActivity().startActivity(intent);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }


}
