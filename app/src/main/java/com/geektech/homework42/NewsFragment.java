package com.geektech.homework42;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geektech.homework42.databinding.FragmentNewsBinding;
import com.geektech.homework42.models.Article;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (binding.progressBarNews.getVisibility() == View.GONE) {
                    save();
                //}

            }
        });

    }

    private void save() {
        binding.progressBarNews.setVisibility(View.VISIBLE);

        binding.btnSave.setClickable(false);

        String text = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        Article article = new Article(text, System.currentTimeMillis());
        App.getDataBase().articleDao().insert(article);

        saveToFireStore(article);

        bundle.putSerializable("key", article);
        getParentFragmentManager().setFragmentResult("news", bundle);


    }

    private void saveToFireStore(Article article) {

        FirebaseFirestore.getInstance()
                .collection("news")
                .add(article)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            binding.progressBarNews.setVisibility(View.GONE);
                            binding.btnSave.setClickable(true);
                            closeFragment();
                        } else {
                            Toast.makeText(requireContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void closeFragment() {
        NavController navController = Navigation.findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}