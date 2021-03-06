package com.geektech.homework42;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.geektech.homework42.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geektech.homework42.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);






//        Prefs prefs = new Prefs(this);
//
//        if(!prefs.isShown())
//            navController.navigate(R.id.boardFragment);
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user==null){
           // navController.navigate(R.id.loginFragment);
      //  }


        mAuth = FirebaseAuth.getInstance();


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                ArrayList<Integer> fragments = new ArrayList<>();
                fragments.add(R.id.navigation_home);
                fragments.add(R.id.navigation_dashboard);
                fragments.add(R.id.navigation_notifications);
                fragments.add(R.id.navigation_profile);

                if (fragments.contains(navDestination.getId()))
                    binding.navView.setVisibility(View.VISIBLE);
                else
                    binding.navView.setVisibility(View.GONE);

                if (navDestination.getId() == R.id.boardFragment
                        || navDestination.getId() == R.id.loginFragment
                        || navDestination.getId() == R.id.verifyFragment)
                    binding.navView.setVisibility(View.GONE);
                else
                    binding.navView.setVisibility(View.VISIBLE);

                if (navDestination.getId() == R.id.boardFragment
                        || navDestination.getId() == R.id.loginFragment
                        || navDestination.getId() == R.id.verifyFragment)
                    getSupportActionBar().hide();
                else
                    getSupportActionBar().show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Type here");
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logout:
//                mAuth.signOut();
//                navController.navigate(R.id.loginFragment);
//                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    public void onStartClick(View view) {
        navController.navigateUp();



    }
//    private void files() {
//        File dir = new File(getCacheDir(),"media");
//        dir.mkdir();
//
//        File file = new File(getCacheDir(), "note.txt");
//        if (file.exists()) {
//            file.delete();
//        } else{
//            file.createNewFile();
//    }
//    }

}