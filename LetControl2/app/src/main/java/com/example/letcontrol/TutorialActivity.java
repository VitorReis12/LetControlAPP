package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.letcontrol.databinding.ActivityTutorialBinding;

public class TutorialActivity extends AppCompatActivity {

    int NumberPage = 0;
    private ActivityTutorialBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTutorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tutorial);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NumberPage == 0) {
                    navController.setGraph(R.id.nav_graph_tela2bt);
                    NumberPage += 1;
                } else if (NumberPage == 1) {
                    navController.setGraph(R.id.nav_graph3_motivo);
                    NumberPage += 1;
                } else if (NumberPage == 2) {
                    finish();
                }
            }
        });
    }

}