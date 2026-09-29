package com.baiyu.mycalendar.ui.year;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.baiyu.mycalendar.R;
import com.baiyu.mycalendar.databinding.FragmentYearViewBinding;
import com.baiyu.mycalendar.ui.main.MainViewModel;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class YearViewFragment extends Fragment {

    private YearViewViewModel yearViewViewModel;
    private MainViewModel mainViewModel;
    private FragmentYearViewBinding binding;
    private YearPagerAdapter pagerAdapter;

    public static YearViewFragment newInstance() {
        return new YearViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentYearViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        yearViewViewModel = new ViewModelProvider(this).get(YearViewViewModel.class);
        //create the provider and listener
        MonthCellsProvider provider = year -> yearViewViewModel.generateMonthCells(year);
        OnMonthCellClickListener listener = month -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_yearViewFragment_to_monthViewFragment);
            mainViewModel.getSelectedDate().setValue(month.atDay(1));
        };
        //set ViewPager adapter and give it to ViewPager
        pagerAdapter = new YearPagerAdapter(listener, provider);
        binding.yearVp2.setAdapter(pagerAdapter);
        //set current page position 1000
        binding.yearVp2.setCurrentItem(1000, false);
        //set swipe callback
        binding.yearVp2.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    //the onPageSelected() will be called by swipe, setCurrentItem
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        Year yearOfPage = Year.now().plusYears(position - 1000);

                        LocalDate selectedDate = mainViewModel.getSelectedDate().getValue();

                        if (selectedDate == null) {
                            return;
                        }

                        if (yearOfPage.equals(Year.from(selectedDate))) {
                            return;
                        }

                        mainViewModel.getSelectedDate().setValue(
                                selectedDate.withYear(yearOfPage.getValue())
                        );
                    }
                }
        );
        //set observers
        mainViewModel.getSelectedDate().observe(getViewLifecycleOwner(), newDate ->
        { //change the page to the selected date
            Year targetYear = Year.from(newDate);
            int position = (int) ChronoUnit.YEARS.between(
                    Year.now(),
                    targetYear
            ) + 1000;
            if (binding.yearVp2.getCurrentItem() != position) {
                binding.yearVp2.setCurrentItem(position, false);
            }
        });
    }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
}