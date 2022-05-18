package com.geektech.homework42.profile;

import static androidx.navigation.Navigation.findNavController;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.ui.NavigationUI;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.homework42.ImageFragment;
import com.geektech.homework42.Prefs;
import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


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
            Glide.with(requireContext()).load(uri).centerCrop().into(binding.imageViewProfile);
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

        NavController navController = findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);

//        loadText();
        binding.imageViewProfile.setOnClickListener(view1 -> loadAvatar());

       // Toast.makeText(requireContext(), String.valueOf(binding.imageViewProfile.getId()), Toast.LENGTH_SHORT).show();


        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteАromFirestore();
            }
        });


        binding.btnShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String image = uri.toString();
                bundle.putString("image",image);

                navController.navigate(R.id.imageFragment,bundle);




            }
        });

    }

//    private void gorillaTransition() {
//        binding.btnShared.setOnClickListener(view -> {
//            /*ImageView itemImageView = binding.imageViewProfile;
//            ViewCompat.setTransitionName(itemImageView, "item_image");*/
//
//            ImageView imageView = binding.imageViewProfile;
//            FragmentNavigator.Extras extras =
//                    new FragmentNavigator.Extras.Builder()
//                            .addSharedElement(imageView, "my_image")
//                            .build();
//            findNavController(view).navigate(
//                    R.id.action_navigation_profile_to_gorillaFragment,
//                    null,
//                    null,
//                    extras);
//
//        });
//    }
    /*Этот метод полностью создан мной. Комментарий для чтения
    http://developer.alexanderklimov.ru/android/animation/tweenanimation.php  в этой ссылке информация
    про анимации. Многие материалы можно найти там же.*/
//    private void animationProfile() {
//        /*Мы создаем переменную типа Animation, затем ее приравниваем к родительскому контексту и айдишке нашей
//        анимации, которую мы создали в папке res по пути anim. AnimationUtils это класс который как раз таки отвечает
//        за анимации. */
//        final Animation animationProfile = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha);
//        final Animation animationProfileZoomIn = AnimationUtils.loadAnimation(requireContext(), R.anim.zoomin);
//        //Дальше реализуем работу анимации после нажатия на imageView.
//        binding.imageViewProfile.setOnClickListener(view1 -> {
//            //startAnimation - это начать работу установленной анимации
//            view1.startAnimation(animationProfile);
//            view1.startAnimation(animationProfileZoomIn);
//            /*Внутри наших xml анимации, наборы характеристик. Их можно изменять как угодна. При создании
//            нужно не забыть записать туда интерполятор. android:interpolator="@android:anim/accelerate_interpolator"
//            В противном случае будет происходить краш*/
//            loadAvatar();
//        });
//    }


    private void uppLoad(Uri uri){

        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseStorage.getInstance()
                .getReference()
                .child(userId + ".jpg")
                .putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(requireContext(), "UpLoaded", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(requireContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("profile", "onUploadFailed" + task.getException().getMessage());
                        }
                    }
                });

    }

    private void deleteАromFirestore() {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference desertRef = storageReference.child(userId + ".jpg");
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(requireContext(), "Is success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "Failure" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImage(){
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("key",binding.imageViewProfile.getId());
        editor.apply();
    }

    private void saveText(){
        saveToFireStoreName(binding.etName.getText().toString());

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

    private void saveToFireStoreName(String name){

//        String userId = FirebaseAuth.getInstance().getUid();
        Map<String,String> user = new HashMap<>();
        final String userId = FirebaseAuth.getInstance().getUid();

        user.put("userName",name);


        FirebaseFirestore.getInstance()
                .collection("users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(requireContext(), "Name saved", Toast.LENGTH_SHORT).show();

                        }else {
                           // Toast.makeText(requireContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Profile","saveNameFireStore" + task.getException().getMessage());
                        }
                    }
                });

    }

    private void saveToFireStoreImageUri(Uri uri){
        Map<String,Uri> uriMap = new HashMap<>();
        final String userId = FirebaseAuth.getInstance().getUid();

        uriMap.put(userId,uri);

        FirebaseFirestore.getInstance()
                .collection("avatarUri")
                .add(uriMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(requireContext(), "Image uri saved", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(requireContext(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    ActivityResultLauncher<Intent> mGetContext = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        uri = result.getData().getData();
                        prefs.saveImage(String.valueOf(uri));
                        binding.imageViewProfile.setImageURI(uri);

                        saveToFireStoreImageUri(uri);


                        uppLoad(uri);



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