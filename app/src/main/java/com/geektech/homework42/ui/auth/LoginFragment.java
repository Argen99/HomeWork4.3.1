package com.geektech.homework42.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mAuth = FirebaseAuth.getInstance();

        NavController navController = Navigation.findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);


        binding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etNumber.getText().toString().isEmpty())
                    Toast.makeText(requireActivity(), "Enter phone number", Toast.LENGTH_SHORT).show();
                else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btnGetOtp.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle();
                    String phoneNumber = binding.etNumber.getText().toString();
                    bundle.putString("number", phoneNumber);
                    navController.navigate(R.id.verifyFragment,bundle);

                    sendVerificationCode(phoneNumber);
                }
            }
        });

//        binding.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (binding.resultNum.getText().toString().isEmpty())
//                    Toast.makeText(requireActivity(), "Enter verify code", Toast.LENGTH_SHORT).show();
//                else {
//                    String verifyNumber = binding.resultNum.getText().toString();
//                    verifyCode(verifyNumber);
//                }
//            }
//        });

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+996"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            binding.progressBar.setVisibility(View.GONE);
            binding.btnGetOtp.setVisibility(View.VISIBLE);

//            final String code = credential.getSmsCode();
//            if (code!=null)
//                verifyCode(code);
        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            binding.progressBar.setVisibility(View.GONE);
            binding.btnGetOtp.setVisibility(View.VISIBLE);
            Toast.makeText(requireActivity(), "Verification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);

            NavController navController = Navigation.findNavController
                    (requireActivity(), R.id.nav_host_fragment_activity_main);

            verificationId = s;

            binding.progressBar.setVisibility(View.GONE);
            binding.btnGetOtp.setVisibility(View.VISIBLE);

            Bundle bundle = new Bundle();
            bundle.putString("number", binding.etNumber.getText().toString());
            bundle.putString("verify", verificationId);


            navController.navigate(R.id.verifyFragment,bundle);


        }
    };

//    private void verifyCode(String code) {
//
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
//        signInByCredentials(credential);
//    }
//
//    private void signInByCredentials(PhoneAuthCredential credential) {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if (task.isSuccessful()){
//                    Toast.makeText(requireContext(), "Login is Successful", Toast.LENGTH_SHORT).show();
//
//                    navController.navigate(R.id.navigation_home);
//                }
//
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user!=null){
//            NavController navController = Navigation.findNavController
//                    (requireActivity(), R.id.nav_host_fragment_activity_main);
//            navController.navigateUp();
//        }
    }
}