package org.me.gcu.equakestartercode;
//Student Id S1911301 Lukasz Bonkowski
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
