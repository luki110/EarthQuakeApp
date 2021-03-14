package org.me.gcu.equakestartercode;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.LinkedHashMap;

public class HashMapAdapter extends BaseAdapter {
    private static final String TAG = "HashMapAdapter";
    private LinkedHashMap<String, EarthQItem> mData = new LinkedHashMap<String, EarthQItem>();
    private String[] mKeys;
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder{
        TextView location, magnitude, date, latlong, depth, key;
    }

    public HashMapAdapter(@NonNull Context context, int resource, @NonNull LinkedHashMap<String, EarthQItem> data) {
        this.mData = data;
        this.mContext = context;
        this.mResource = resource;
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public EarthQItem getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String key = mKeys[position];
        String location = getItem(position).getLocation();
        double magnitude = getItem(position).getMagnitude();
        String sDate = getItem(position).getStringDate();
        double latitude = getItem(position).getLatitude();
        double longitude = getItem(position).getLongitude();
        double depth = getItem(position).getDepth();


        final View result;
        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.location = (TextView) convertView.findViewById(R.id.tvLocation);
            holder.magnitude = (TextView) convertView.findViewById(R.id.tvMagnitude);
            holder.date = (TextView) convertView.findViewById(R.id.tvDate);
            holder.key = (TextView) convertView.findViewById(R.id.tvKey);
            holder.latlong = (TextView) convertView.findViewById(R.id.tvLatlong);
            holder.depth = (TextView) convertView.findViewById(R.id.tvDepth);

            result = convertView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (HashMapAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.location.setText(location);
        holder.date.setText(sDate);
        holder.key.setText(key);
        holder.latlong.setText(String.valueOf(latitude) + " " + String.valueOf(longitude));
        holder.depth.setText("Depth: " + String.valueOf(depth) + "km");

        holder.magnitude.setText("Magnitude: " + String.valueOf(magnitude));

        if(getItem(position).getMagnitude() > 3)
        {
            holder.magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.purple));
        }
        else if(getItem(position).getMagnitude() > 2)
        {
            holder.magnitude.setTextColor(Color.RED);
        }
        else if(getItem(position).getMagnitude() > 1)
        {
            holder.magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }
        else
        {
            holder.magnitude.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
        }


        return convertView;
    }
}
