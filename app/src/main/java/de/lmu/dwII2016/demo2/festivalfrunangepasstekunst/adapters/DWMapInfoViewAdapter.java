package de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.lmu.dwII2016.demo2.festivalfrunangepasstekunst.R;

/**
 * Created by Jan on 05.07.2016.
 */
public class DWMapInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity _activity;

    public DWMapInfoViewAdapter (Activity context) {
        _activity = context;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = _activity.getLayoutInflater().inflate(R.layout.view_dwmapinfowindow, null);

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView desc = (TextView) v.findViewById(R.id.desc);

        title.setText(marker.getTitle());
        desc.setText(marker.getSnippet());

        return v;

    }
}
