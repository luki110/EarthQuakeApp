package org.me.gcu.equakestartercode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel  extends ViewModel {
    private MutableLiveData<ArrayList<EarthQItem>> list = new MutableLiveData<>();

    public void setList(ArrayList<EarthQItem> input){
        list.setValue(input);
    }

    public LiveData<ArrayList<EarthQItem>> getList(){
        return list;
    }

}
