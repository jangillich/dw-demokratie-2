package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WerkDetailActivity extends AppCompatActivity {


   @Bind(R.id.toolbar)
   Toolbar toolbar;
   @Bind(R.id.img)
   ImageView imageView;
   @Bind(R.id.title)
   TextView titel;
   @Bind(R.id.kuenstler)
   Button kuenstler;

   private int imageRes;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_werk_detail);
      ButterKnife.bind(this);

      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         imageRes = extras.getInt("IMAGE");
      }
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setTitle(imageRes);
      imageView.setImageResource(imageRes);
      imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(WerkDetailActivity.this,  FullscreenActivity.class);
            intent.putExtra("IMAGE", imageRes);
            startActivity(intent);
         }
      });
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            onBackPressed();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
}
