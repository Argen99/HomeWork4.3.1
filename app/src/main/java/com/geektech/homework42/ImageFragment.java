package com.geektech.homework42;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.homework42.databinding.FragmentImageBinding;


public class ImageFragment extends Fragment {

    private FragmentImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            String str = bundle.getString("image");
            Uri uri = Uri.parse(str);
            Glide.with(requireContext()).load(uri).centerCrop().into(binding.imageView1);
        }else {
            Toast.makeText(requireContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }
}