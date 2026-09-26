package com.baiyu.mycalendar.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.YearMonth;

public class MainViewModel extends ViewModel {
    //this is use as global ViewModel for shared data
    // the lifecycle of this ViewModel should follow main activity
    private final MutableLiveData<LocalDate> selectedDate = new MutableLiveData<>();
    private final MutableLiveData<YearMonth> displayedMonth = new MutableLiveData<>();

    public MutableLiveData<LocalDate> getSelectedDate() {
        return selectedDate;
    }

    public MutableLiveData<YearMonth> getDisplayedMonth() {
        return displayedMonth;
    }

    public MainViewModel() {
        selectedDate.setValue(LocalDate.now());
        displayedMonth.setValue(YearMonth.now());
    }
}
