package com.geektech.homework42.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.homework42.App;
import com.geektech.homework42.MainActivity;
import com.geektech.homework42.NewsAdapter;
import com.geektech.homework42.OnItemClickListener;
import com.geektech.homework42.R;
import com.geektech.homework42.databinding.FragmentHomeBinding;
import com.geektech.homework42.models.Article;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static NewsAdapter adapter;

    private ArrayList<String> searchList;

    private List<Article> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        adapter = new NewsAdapter();
        list = App.getDataBase().articleDao().getAll();
        adapter.addItems(list);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.example_menu,menu);


        //requireActivity().getMenuInflater().inflate(R.menu.example_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                Toast.makeText(requireContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Toast.makeText(requireContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
                adapter.setList(App.getDataBase().articleDao().getSearch(s));

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);



        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.fub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment();
            }
        });
        getParentFragmentManager().setFragmentResultListener("news",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Article article = (Article) result.getSerializable("key");
                        Log.e("Home", "result = " + article.getText());
                        adapter.addItem(article);
                    }
                });

        binding.recycler.setAdapter(adapter);
        adapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Article article = adapter.getItem(position);

            }

            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Alert!");
                alert.setMessage("Delete?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Article article = list.get(position);
                        App.getDataBase().articleDao().delete(list.get(position));
                        adapter.remove(position);

                        dialogInterface.dismiss();


                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });
                alert.show();
            }
        });


    }

    private void openFragment() {
        NavController navController = Navigation.findNavController
                (requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





}