package com.baiyu.mycalendar.ui.month;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.baiyu.mycalendar.databinding.FragmentMonthViewBinding;

public class MonthViewFragment extends Fragment {

    private FragmentMonthViewBinding binding;
    private MonthViewViewModel mViewModel;
    private MonthViewAdapter adapter;

    public static MonthViewFragment newInstance() {
        return new MonthViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //bind
        binding = FragmentMonthViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MonthViewViewModel.class);
        // TODO: Use the ViewModel
        adapter = new MonthViewAdapter();
        binding.datesRv.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        binding.datesRv.setAdapter(adapter);
        //set the observer of dayCells
        mViewModel.getDayCells().observe(getViewLifecycleOwner(), newDayCells -> {adapter.submitList(newDayCells);});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}