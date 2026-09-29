package com.baiyu.mycalendar.ui.year;

import androidx.lifecycle.ViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class YearViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<List<LocalDate>> generateMonthCells(Year year){
        List<List<LocalDate>> monthCells = new ArrayList<>();
        for(int i = 1; i < 13; i++){
            YearMonth month = year.atMonth(i);
            DayOfWeek startWeekDay = month.atDay(1).getDayOfWeek();
            LocalDate firstDayCell = month.atDay(1).minusDays(startWeekDay.getValue()-1);
            List<LocalDate> days = new ArrayList<>();
            for (int j = 0; j < 42; j++)
            {
                LocalDate date = firstDayCell.plusDays(j);
                if(YearMonth.from(date).equals(month)){
                    days.add(date);
                }
                else {
                    days.add(null);
                }
            }
            monthCells.add(days);
        }
        return monthCells;
    }
}