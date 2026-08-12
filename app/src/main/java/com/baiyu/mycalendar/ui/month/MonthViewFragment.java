package com.baiyu.mycalendar.ui.month;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.baiyu.mycalendar.databinding.FragmentMonthViewBinding;
import com.baiyu.mycalendar.ui.main.MainViewModel;

import java.time.format.TextStyle;
import java.util.Locale;

public class MonthViewFragment extends Fragment {

    private FragmentMonthViewBinding binding;
    private MainViewModel mainViewModel;
    private MonthViewViewModel monthViewViewModel;
    private MonthPageAdapter adapter;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set ViewModel
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        monthViewViewModel = new ViewModelProvider(this).get(MonthViewViewModel.class);
        //set adapter with a listener overriding onDayCellClick() to set the selected date in mainViewModel

        //set observers


        monthViewViewModel.getDisplayedMonth().observe(getViewLifecycleOwner(), newMonth -> {
            String month = newMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            binding.textView.setText(month);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}