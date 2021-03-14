package org.me.gcu.equakestartercode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


import java.util.ArrayList;;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class FilterFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "HashMapAdapter";
//    private EditText datePicker1, datePicker2;
    private Button btnFilter, datePicker;
    private SharedViewModel viewModel;
    private ArrayList<EarthQItem> list;
    private LinkedHashMap<String, EarthQItem> filteredHashMap;
    private Date startDate, endDate;
    private TextView selectedDate;
    private ArrayList<EarthQItem> filteredList;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        datePicker = v.findViewById(R.id.btnDatePicker);
//        datePicker2 = v.findViewById(R.id.datePicker2);
        btnFilter = v.findViewById(R.id.btnFilter);
        listView = v.findViewById(R.id.filteredListView);
        selectedDate = v.findViewById(R.id.tvSelectedDate);
        btnFilter.setOnClickListener(this);
        datePicker.setOnClickListener(this);

        showRangeCalendar();


        return v;
    }

    private void showRangeCalendar(){
        MaterialDatePicker.Builder<Pair<Long, Long>> builderRange = MaterialDatePicker.Builder.dateRangePicker();
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar min = Calendar.getInstance();
        min.add(Calendar.DATE, -100);
        Calendar max = Calendar.getInstance();
        max.set(Calendar.HOUR_OF_DAY, 0);

        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(min.getTimeInMillis());
        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(max.getTimeInMillis());

        ArrayList<CalendarConstraints.DateValidator> listValidators =
                new ArrayList<CalendarConstraints.DateValidator>();
        listValidators.add(dateValidatorMin);
        listValidators.add(dateValidatorMax);
        CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);
        constraintsBuilderRange.setValidator(validators);

        builderRange.setCalendarConstraints(constraintsBuilderRange.build());
        MaterialDatePicker<Pair<Long, Long>> pickerRange = builderRange.build();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerRange.show(getChildFragmentManager(), "Date_Picker");
            }
        });
        pickerRange.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        String selectionDate = pickerRange.getHeaderText();
                        startDate = new Date(pickerRange.getSelection().first);
                        endDate = new Date(pickerRange.getSelection().second);

                        selectedDate.setText("Selected Date is : " + selectionDate);

                    }
                });
    }


    public ArrayList<EarthQItem> filterList(ArrayList<EarthQItem> unfilteredList,
                                            Date sDate, Date eDate){
        filteredList = new ArrayList<>();

        for (EarthQItem i: unfilteredList)
        {
            if(sDate.toString().equals(eDate.toString()))
            {
                Log.d(TAG, "filterList: " + i.getStringDate() + " "+ sDate);
//                Calendar calendar = Calendar.getInstance();
//                calendar.
                if(i.getDate() == sDate){
                    filteredList.add(i);
                }

            }
            else{
                if(i.getDate().after(sDate) && i.getDate().before(eDate)){
                    filteredList.add(i);
                }
            }

        }
        if(filteredList.size() ==0){
            Toast.makeText(getContext(), "There was no earthquakes on this day/dates", Toast.LENGTH_LONG).show();
            return null;
        }
        else{
            return filteredList;
       }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFilter:
                try {
                    filterResults();
                }catch (Exception e){
                    Toast.makeText(getActivity(), "There was no earthquakes on this day/dates", Toast.LENGTH_LONG).show();
                    listView.setVisibility(View.INVISIBLE);
                }

                break;
        }
    }
    public void filterResults (){
        filterList(list, startDate, endDate);
        filteredHashMap = new LinkedHashMap<String, EarthQItem>();
        filteredHashMap.put("Shallowest", getShallowestEarthQuake(filteredList));
        filteredHashMap.put("Deepest", getDeepestEarthQuake(filteredList));
        filteredHashMap.put("Largest Magnitude", getLargestMagnituteEarthQuake(filteredList));
        filteredHashMap.put("Most Northerly", getMostNortherlyEarthQuake(filteredList));
        filteredHashMap.put("Most Southerly", getMostSoutherlyEarthQuake(filteredList));
        filteredHashMap.put("Most Easterly", getMostEasterlyEarthQuake(filteredList));
        filteredHashMap.put("Most Westerly", getMostWesterlyEarthQuake(filteredList));
        HashMapAdapter adapter = new HashMapAdapter(getActivity(), R.layout.filter_adapter_layout,
                filteredHashMap);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    public EarthQItem getDeepestEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem deepest = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getDepth() > deepest.getDepth())
            {
                deepest = i;
            }
        }
        return deepest;
    }
    public EarthQItem getShallowestEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem shallowest = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getDepth() < shallowest.getDepth())
            {
                shallowest = i;
            }
        }
        return shallowest;
    }
    public EarthQItem getLargestMagnituteEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem maxMagnitude = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getMagnitude() > maxMagnitude.getMagnitude())
            {
                maxMagnitude = i;
            }
        }
        return maxMagnitude;
    }
    public EarthQItem getMostNortherlyEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem maxLat =unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {

            if(i.getLatitude() > maxLat.getLatitude())
            {
                maxLat = i;
            }
        }
        return maxLat;
    }
    public EarthQItem getMostSoutherlyEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem minLat = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getLatitude() < minLat.getLatitude())
            {
                minLat = i;
            }
        }
        return minLat;
    }
    public EarthQItem getMostEasterlyEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem maxLon = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getLongitude() > maxLon.getLongitude())
            {
                maxLon = i;
            }
        }
        return maxLon;
    }
    public EarthQItem getMostWesterlyEarthQuake(ArrayList<EarthQItem> unfilteredList){
        EarthQItem minLon = unfilteredList.get(0);
        for (EarthQItem i : unfilteredList)
        {
            if(i.getLongitude() < minLon.getLongitude())
            {
                minLon = i;
            }
        }
        return minLon;
    }



}
