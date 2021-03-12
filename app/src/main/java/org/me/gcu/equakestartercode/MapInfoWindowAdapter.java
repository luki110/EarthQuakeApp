package org.me.gcu.equakestartercode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfoWindowAdapter  implements GoogleMap.InfoWindowAdapter{
    private Context mContext;

    public MapInfoWindowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)mContext).getLayoutInflater()
                .inflate(R.layout.adapter_window_info_map, null);
        EarthQItem earthQItem = (EarthQItem) marker.getTag();

        //get xml
        TextView title = view.findViewById(R.id.title);
        TextView magnitude = view.findViewById(R.id.magnitude);
        TextView date = view.findViewById(R.id.date);
        TextView latlong = view.findViewById(R.id.latlong);
        TextView depth = view.findViewById(R.id.depth);

        //display
        title.setText(earthQItem.getLocation());
        title.setTextColor(Color.BLACK);
        magnitude.setText("Magnitude: " + earthQItem.getMagnitude());
        date.setText("Date: " + earthQItem.getDate());
        date.setTextColor(Color.BLACK);
        latlong.setText(earthQItem.getLatitude() + ", " + earthQItem.getLongitude());
        latlong.setTextColor(Color.BLACK);
        depth.setText("Depth: " + earthQItem.getDepth() + " km");
        depth.setTextColor(Color.BLACK);

        //colour magnitude
        if(earthQItem.getMagnitude() > 3)
        {
            magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.purple));
        }
        else if(earthQItem.getMagnitude() > 2)
        {
            magnitude.setTextColor(Color.RED);
        }
        else if(earthQItem.getMagnitude() > 1)
        {
            magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }
        else
        {
            magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
        }

        return view;
    }
}
