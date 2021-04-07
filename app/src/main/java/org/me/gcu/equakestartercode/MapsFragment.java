package org.me.gcu.equakestartercode;
//Student Id S1911301 Lukasz Bonkowski
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements Observer<ArrayList<EarthQItem>> {
    private SharedViewModel viewModel;
    private ArrayList<EarthQItem> list;
    private Bundle bundle;
    private EarthQItem earthQItem;
    private GoogleMap mMap;


    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {


        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            mMap = googleMap;
            LatLng def;
            Marker marker1 = null;
            UiSettings settings = mMap.getUiSettings();
            settings.setZoomControlsEnabled(true);
            settings.setCompassEnabled(true);
            MapInfoWindowAdapter adapter = new MapInfoWindowAdapter(getActivity());
            mMap.setInfoWindowAdapter(adapter);

            for(int i = 0; i < list.size(); ++i)
            {
                LatLng location = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());

                float colour;
                if(list.get(i).getMagnitude() > 3)
                {
                    colour = BitmapDescriptorFactory.HUE_VIOLET;
                }
                else if(list.get(i).getMagnitude() > 2)
                {
                    colour = BitmapDescriptorFactory.HUE_RED;
                } else if(list.get(i).getMagnitude() > 1)
                {
                    colour = BitmapDescriptorFactory.HUE_ORANGE;
                } else
                {
                    colour = BitmapDescriptorFactory.HUE_YELLOW;
                }

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(list.get(i).getLocation())
                        .snippet("Magnitude: " + list.get(i).getMagnitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(colour)));

                marker.setTag(list.get(i));

                if(list.get(i) == earthQItem)
                {
                    marker1 = marker;
                }
            }
            //end of for loop
            if(earthQItem != null)
            {
                def = new LatLng(earthQItem.getLatitude() , earthQItem.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(def,10));
                marker1.showInfoWindow();
            }
            else
             {
                def =  new LatLng(55, 0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(def,5));
             }
        } // end of map ready
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        Bundle bundle = getArguments();

        if(bundle != null)
        {
            earthQItem = (EarthQItem) bundle.getSerializable("Item");
        }

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<ArrayList<EarthQItem>>() {
            @Override
            public void onChanged(ArrayList<EarthQItem> earthQItems) {
                list = new ArrayList<>();
                list = earthQItems;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onChanged(ArrayList<EarthQItem> earthQItems) {

    }
}