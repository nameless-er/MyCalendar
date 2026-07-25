package com.baiyu.mycalendar.ui.month;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.databinding.ItemDaycellBinding;
import com.baiyu.mycalendar.model.DayCell;

public class MonthViewAdapter extends ListAdapter<DayCell, MonthViewAdapter.DayCellViewHolder> {
    //the list is in ListAdapter, use getItem()
    //the setData() is in ListAdapter namely submitList()

    private static final DiffUtil.ItemCallback<DayCell> DIFF_CALLBACK = new DiffUtil.ItemCallback<DayCell>() {
        @Override
        //compare the id of the object (date)
        public boolean areItemsTheSame(@NonNull DayCell oldItem, @NonNull DayCell newItem) {
            return oldItem.date().equals(newItem.date());
        }

        @Override
        //compare the other content of the object
        public boolean areContentsTheSame(@NonNull DayCell oldItem, @NonNull DayCell newItem) {
            return oldItem.equals(newItem);
        }
    };
    public MonthViewAdapter() {
        super(DIFF_CALLBACK);
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
        DayCell dayData = getItem(position);
        holder.binding.DayTv.setText(String.valueOf(dayData.date().getDayOfMonth()));
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
}
