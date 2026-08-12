package com.baiyu.mycalendar.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

public class MainViewModel extends ViewModel {
    //this is use as global ViewModel for shared data
    // the lifecycle of this ViewModel should follow main activity
    private final MutableLiveData<LocalDate> selectedDate = new MutableLiveData<>();

    public MutableLiveData<LocalDate> getSelectedDate() {
        return selectedDate;
    }

    public MainViewModel() {
        selectedDate.setValue(LocalDate.now());
    }
}
