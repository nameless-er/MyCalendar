package com.baiyu.mycalendar.ui.month;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.databinding.ItemMonthPageBinding;
import com.baiyu.mycalendar.model.DayCell;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//for month_vp2
public class MonthPagerAdapter extends RecyclerView.Adapter<MonthPagerAdapter.PageViewHolder>{
    //listener and provider go from MonthViewFragment to MonthPagerAdapter then to MonthPageAdapter
    private final OnDayCellClickListener listener;
    private final DayCellsProvider provider;
    private final Map<YearMonth, List<DayCell>> dayCellsByMonth = new HashMap<>(); //store dayCells for each month
    private final Map<YearMonth, MonthPageAdapter> adaptersByMonth = new HashMap<>(); //store adapter for each month

    public MonthPagerAdapter(OnDayCellClickListener listener, DayCellsProvider provider) {
        this.listener = listener;
        this.provider = provider;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMonthPageBinding binding = ItemMonthPageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        binding.datesRv.setLayoutManager(new GridLayoutManager(parent.getContext(), 7));
        return new PageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        //get month, the position it set from 1000
        YearMonth month = YearMonth.now().plusMonths(position - 1000);
        holder.setMonth(month);
        //get existing list, or generate it if it doesn't exist
        List<DayCell> dayCells = dayCellsByMonth.get(month);
        if (dayCells == null) {
            dayCells = provider.generateDayCells(month);
            dayCellsByMonth.put(month, dayCells);
        }
        //create the adapter for this month's RecyclerView
        MonthPageAdapter pageAdapter = new MonthPageAdapter(listener);
        adaptersByMonth.put(month, pageAdapter);
        holder.binding.datesRv.setAdapter(pageAdapter);
        pageAdapter.submitList(dayCells);
        //disableRvAnimation
        disableRvAnimation(holder);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onViewRecycled(@NonNull PageViewHolder holder) {

        if (holder.month != null) {
            adaptersByMonth.remove(holder.month);
        }

        super.onViewRecycled(holder);
    }

    //viewHolder for a month page
    static class PageViewHolder extends RecyclerView.ViewHolder{
        ItemMonthPageBinding binding;
        YearMonth month;

        public PageViewHolder(@NonNull ItemMonthPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setMonth(YearMonth month) {
            this.month = month;
        }
    }

    public void disableRvAnimation(@NonNull PageViewHolder holder){
        RecyclerView.ItemAnimator animator = holder.binding.datesRv.getItemAnimator();
        if (animator instanceof androidx.recyclerview.widget.SimpleItemAnimator) {
            ((androidx.recyclerview.widget.SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    //used in fragment
    public void updateSelectedDate(LocalDate newDate) {

        for (Map.Entry<YearMonth, List<DayCell>> entry : dayCellsByMonth.entrySet()) {

            YearMonth month = entry.getKey();
            List<DayCell> currentCells = entry.getValue();

            List<DayCell> updatedCells =
                    provider.upDateSelectedDayCell(
                            newDate,
                            currentCells
                    );

            entry.setValue(updatedCells);

            MonthPageAdapter adapter =
                    adaptersByMonth.get(month);

            if (adapter != null) {
                adapter.submitList(updatedCells);
            }
        }
    }

}
