package com.baiyu.mycalendar.ui.year;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.databinding.ItemYearPageBinding;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearPagerAdapter extends RecyclerView.Adapter<YearPagerAdapter.PageViewHolder> {
    private final OnMonthCellClickListener listener;
    private final MonthCellsProvider provider;
    private final Map<Year, List<List<LocalDate>>> monthCellsByYear = new HashMap<>();

    public YearPagerAdapter(OnMonthCellClickListener listener, MonthCellsProvider provider) {
        this.listener = listener;
        this.provider = provider;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemYearPageBinding binding = ItemYearPageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        binding.monthsRv.setLayoutManager(new GridLayoutManager(parent.getContext(), 3));
        return new PageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        //get year, the position it set from 1000
        Year year = Year.now().plusYears(position - 1000);
        holder.setYear(year);
        //get existing list, or generate it if it doesn't exist
        List<List<LocalDate>> monthCells = monthCellsByYear.get(year);
        if (monthCells == null) {
            monthCells = provider.generateMonthCells(year);
            monthCellsByYear.put(year, monthCells);
        }
        //create the adapter for this year's RecyclerView
        YearPageAdapter pageAdapter = new YearPageAdapter(listener, year, monthCells);
        holder.binding.monthsRv.setAdapter(pageAdapter);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class PageViewHolder extends RecyclerView.ViewHolder{
        ItemYearPageBinding binding;
        Year year;
        public PageViewHolder(@NonNull ItemYearPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setYear(Year year) {
            this.year = year;
        }
    }
}
