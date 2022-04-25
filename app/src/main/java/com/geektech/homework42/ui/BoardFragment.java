package com.geektech.homework42.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.homework42.Prefs;
import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentBoardBinding;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;


public class BoardFragment extends Fragment {

    private FragmentBoardBinding binding;
    private ViewPager2 viewPager2;
    private WormDotsIndicator indicator;
    private ViewPagerAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BoardAdapter adapter = new BoardAdapter();

        indicator = (WormDotsIndicator) view.findViewById(R.id.worm_dots_indicator);
        viewPager2 = (ViewPager2) view.findViewById(R.id.view_pager);
        viewPager2.setAdapter(adapter);
        indicator.setViewPager2(viewPager2);

        binding.textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

    }
    private void closeFragment() {
//        Prefs prefs = new Prefs(requireContext());
//        prefs.saveState();
        NavController navController = Navigation.findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}