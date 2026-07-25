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
        generateDayCells(YearMonth.now());
    }

    //generate daycells based on a month
    public void generateDayCells(YearMonth month) {
        DayOfWeek startWeekDay = month.atDay(1).getDayOfWeek();
        //check whether to create 35 cells or 42 cells
        int requiredCells = startWeekDay.getValue() + month.lengthOfMonth() - 1;
        int totalCells = requiredCells <= 35 ? 35 : 42;
        LocalDate firstDayCell = month.atDay(1).minusDays(startWeekDay.getValue()-1);
        List<DayCell> dayCells = new ArrayList<>();
        for (int i = 0; i < totalCells; i++)
        {
            LocalDate date = firstDayCell.plusDays(i);
            boolean isDisplayedMonth = YearMonth.from(date).equals(month);
            boolean isToday = date.equals(LocalDate.now());
            dayCells.add(new DayCell(date, isToday, isDisplayedMonth));
        }
        this.dayCells.setValue(dayCells);
    }

}