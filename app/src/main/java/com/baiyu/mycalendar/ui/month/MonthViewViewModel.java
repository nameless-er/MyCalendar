package com.baiyu.mycalendar.ui.month;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baiyu.mycalendar.model.DayCell;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class MonthViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // The month currently displayed
    private final MutableLiveData<List<DayCell>> dayCells = new MutableLiveData<>();

    public MutableLiveData<List<DayCell>> getDayCells() {
        return dayCells;
    }

    public MonthViewViewModel() {
    }

    //generate daycells based on a month
    public void generateDayCells(YearMonth month) {
        DayOfWeek startWeekDay = month.atDay(1).getDayOfWeek();
        LocalDate firstDayCell = month.atDay(1).minusDays(startWeekDay.getValue()-1);
        List<DayCell> dayCells = new ArrayList<>();
        for (int i = 0; i < 35; i++)
        {
            LocalDate date = firstDayCell.plusDays(i);
            boolean isCurrentMonth = YearMonth.from(date).equals(YearMonth.now());
            boolean isToday = date.equals(LocalDate.now());
            dayCells.add(new DayCell(date, isToday, isCurrentMonth));
        }
        this.dayCells.setValue(dayCells);
    }

}