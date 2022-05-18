package com.geektech.homework42.ui.auth;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyFragment extends Fragment {

    private FragmentVerifyBinding binding;
    private String verificationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);

        Bundle bundle = getArguments();
        if (bundle != null){
            binding.phoneNumber.setText("+996 " + bundle.getString("number"));
            verificationId = bundle.getString("verify");
        }else
            Toast.makeText(requireContext(), "FACK", Toast.LENGTH_SHORT).show();

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etCode.getText().toString().isEmpty()){
                    Toast.makeText(requireActivity(), "Please enter valid code", Toast.LENGTH_SHORT).show();
                }else{
                    String code = binding.etCode.getText().toString();
                    if (verificationId != null){
                        binding.progressBarVerify.setVisibility(View.VISIBLE);
                        binding.btnVerify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider
                                .getCredential(verificationId, code);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        binding.progressBarVerify.setVisibility(View.GONE);
                                        binding.btnVerify.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()){
                                            navController.navigate(R.id.navigation_home);
                                            Toast.makeText(requireContext(), "Login is Successful", Toast.LENGTH_SHORT).show();
                                        }else
                                            Toast.makeText(requireContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                }
            }
        });

        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (binding.etCode.getText().toString().length() == 6)
                    binding.btnVerify.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.green));
                else
                    binding.btnVerify.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.dark_blue));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}