package org.me.gcu.equakestartercode;
//Student Id S1911301 Lukasz Bonkowski
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthQAdapter  extends ArrayAdapter<EarthQItem> {

    private static final String TAG = "EarthQAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView location, magnitude, date;
    }

    public EarthQAdapter(@NonNull Context context, int resource, @NonNull ArrayList<EarthQItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String location = getItem(position).getLocation();
        double magnitude = getItem(position).getMagnitude();

        String sDate = getItem(position).getStringDate();
        //Date date = getItem(position).getDate();

//        EarthQItem earthQItem = new EarthQItem(location, date);

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
            result = convertView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.location.setText(location);
        holder.date.setText(sDate);

        holder.magnitude.setText(String.format("Magnitude: %s", String.valueOf(magnitude)));

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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EarthQItem item = getItem(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Item", (Serializable) item);
                MapsFragment fragment = new MapsFragment();
                fragment.setArguments(bundle);
                ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit();
            }
        });

        return convertView;
    }
}








