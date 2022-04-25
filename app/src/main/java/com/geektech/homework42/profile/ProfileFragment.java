package com.geektech.homework42.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.geektech.homework42.Prefs;
import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private final int REQUEST_CODE = 1;
    private FragmentProfileBinding binding;
    private SharedPreferences mSettings;
    private Prefs prefs;
    private Uri uri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (prefs.getImageUri() != null){
            uri = Uri.parse(prefs.getImageUri());
            Glide.with(requireContext()).load(uri).centerCrop().into(binding.imageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadText();
        binding.imageView.setOnClickListener(view1 -> loadAvatar());

    }

    private void saveImage(){
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("key",binding.imageView.getId());
        editor.apply();
    }

    private void saveText(){
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("key",binding.etName.getText().toString());
        editor.apply();
    }
    private void loadText(){
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String savedText = mSettings.getString("key","");
        binding.etName.setText(savedText);
    }
    private void loadAvatar(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        mGetContext.launch(intent);
    }

    ActivityResultLauncher<Intent> mGetContext = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        uri = result.getData().getData();
                        prefs.saveImage(String.valueOf(uri));
                        binding.imageView.setImageURI(uri);
                    }
                }
            });

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveImage();
        saveText();
    }
}