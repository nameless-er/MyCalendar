package com.baiyu.mycalendar.ui.month;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.databinding.ItemDaycellBinding;
import com.baiyu.mycalendar.model.DayCell;

import java.util.List;

public class MonthViewAdapter extends RecyclerView.Adapter<MonthViewAdapter.DayCellViewHolder>{
    private List<DayCell> dayCells;

    public MonthViewAdapter(List<DayCell> dayCells) {
        this.dayCells = dayCells;
    }

    @NonNull
    @Override
    //create a new view holder and return it
    public DayCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDaycellBinding dayCellBinding = ItemDaycellBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new DayCellViewHolder(dayCellBinding);
    }

    @Override
    //bind data from the list to view holder
    public void onBindViewHolder(@NonNull DayCellViewHolder holder, int position) {
        DayCell dayData = dayCells.get(position);
        holder.binding.DayTv.setText(String.valueOf(dayData.getDate().getDayOfMonth()));
    }

    @Override
    public int getItemCount() {
        return dayCells.size();
    }

    //define the view hold for the daycell
    static class DayCellViewHolder extends RecyclerView.ViewHolder {
        //extra attribute
        ItemDaycellBinding binding;
        public DayCellViewHolder(@NonNull ItemDaycellBinding binding) {
            //set viewItem
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<DayCell> getDayCells() {
        return dayCells;
    }

    public void setDayCells(List<DayCell> dayCells) {
        this.dayCells = dayCells;
        notifyDataSetChanged();
    }
}
