package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.KuenstlerDetailActivity;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.KuenstlerViewItem;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.R;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ImageFetcher;
import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.util.ResHelper;

public class ArtistViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public class ArtistViewHolder extends RecyclerView.ViewHolder {
      @Bind (R.id.img)
      KuenstlerViewItem img;
      @Bind (R.id.img_selector)
      ImageView imgSelector;
      @Bind(R.id.artist_text)
      RelativeLayout artistText;
      @Bind (R.id.artist_name)
      TextView artistName;
      @Bind (R.id.artist_subtitle)
      TextView artistSubtitle;
      @Bind (R.id.image_name)
      ImageView imageName;

      public ArtistViewHolder(View view) {
         super(view);
         ButterKnife.bind(this, view);
      }
   }

   private List<Integer> images;
   private List<String> kuenstlerNames;
   private Activity activity;
   private ImageFetcher imageFetcher;

   public ArtistViewAdapter(Activity activity, ImageFetcher imageFetcher, List<Integer> images,
         List<String> kuenstlerNames) {
      this.images = images;
      this.kuenstlerNames = kuenstlerNames;
      this.activity = activity;
      this.imageFetcher = imageFetcher;
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = activity.getLayoutInflater()
            .inflate(R.layout.artist_row, parent, false);
      return new ArtistViewHolder(view);
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
      final ArtistViewHolder itemViewHolder = (ArtistViewHolder) viewHolder;
      imageFetcher.loadImage(activity.getResources()
            .getResourceEntryName(images.get(position)), itemViewHolder.img);
      String kuenstlerNameId = kuenstlerNames.get(position);
      String kuenstlerName = activity.getString(
            ResHelper.getResId("kuenstler_name_" + kuenstlerNameId, R.string.class));
      String kuenstlerSubtitle = activity.getString(
            ResHelper.getResId("kuenstler_subtitle_" + kuenstlerNameId, R.string.class));

      itemViewHolder.img.setKuenstlerName(kuenstlerNameId);


      int kuenstlerNameImageId =
              ResHelper.getResId("kuenstler_name_" + kuenstlerNameId, R.drawable.class);
      if (kuenstlerNameImageId < 0) {
         itemViewHolder.imageName.setVisibility(View.GONE);
         itemViewHolder.artistText.setVisibility(View.VISIBLE);
         itemViewHolder.artistName.setText(kuenstlerName);
         if (kuenstlerSubtitle != null && !kuenstlerSubtitle.isEmpty()) {
            itemViewHolder.artistSubtitle.setText(kuenstlerSubtitle);
            itemViewHolder.artistSubtitle.setVisibility(View.VISIBLE);
         } else {
            itemViewHolder.artistSubtitle.setVisibility(View.GONE);
         }
      } else {
         itemViewHolder.imageName.setImageResource(kuenstlerNameImageId);
         itemViewHolder.imageName.setVisibility(View.VISIBLE);
         itemViewHolder.artistText.setVisibility(View.GONE);
      }


      itemViewHolder.imgSelector.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(activity, KuenstlerDetailActivity.class);
            intent.putExtra("KUENSTLER_NAME", itemViewHolder.img.getKuenstlerName());
            activity.startActivity(intent);
         }
      });
   }

   @Override
   public int getItemCount() {
      return images.size();
   }
}
