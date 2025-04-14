package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button buttonhome, buttonAccont, buttonseach, buttonstatistics, buttonwacht;

    private TextView textViewHome, textViewWatch, textViewStatistics, textViewAccount, textViewSearch;

    private Fragment_Home fragmentHome;
    private AccontFragment accontFragment;

    private statisticsFragment statisticsFragment;

    private SearchFragment searchFragment;

    private WatchFragment watchFragment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accontFragment = new AccontFragment();
        fragmentHome = new Fragment_Home();
        statisticsFragment = new statisticsFragment();
        searchFragment = new SearchFragment();
        watchFragment = new WatchFragment();

        textViewHome = findViewById(R.id.textViewHome);
        textViewWatch = findViewById(R.id.textViewWatch);
        textViewStatistics = findViewById(R.id.textViewStatistics);
        textViewAccount = findViewById(R.id.textViewAccont);
        textViewSearch = findViewById(R.id.textViewSearch);

        buttonhome = findViewById(R.id.buttonHome);
        buttonAccont = findViewById(R.id.buttonAccont);
        buttonstatistics = findViewById(R.id.buttonStatistics);
        buttonwacht = findViewById(R.id.buttonWatch);
        buttonseach = findViewById(R.id.buttonSearch);

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_conteudo, fragmentHome);
        transaction.commit();

        buttonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonhome.setBackgroundResource(R.drawable.li_home);

                ResetText();
                textViewHome.setText("Home");

                androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, fragmentHome );
                transaction.commit();
            }
        });

        buttonAccont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonAccont.setBackgroundResource(R.drawable.li_user_blue);


                ResetText();
                textViewAccount.setText("Account");


                androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, accontFragment );
                transaction.commit();
            }
        });

        buttonseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonseach.setBackgroundResource(R.drawable.li_search_blue);

                ResetText();
                textViewSearch.setText("Search");

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, searchFragment);
                transaction.commit();
            }
        });

        buttonwacht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonwacht.setBackgroundResource(R.drawable.li_clock_blue);


                ResetText();
                textViewWatch.setText("Watch");

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, watchFragment);
                transaction.commit();
            }
        });

        buttonstatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonstatistics.setBackgroundResource(R.drawable.li_pie_chart_blue);

                ResetText();
                textViewStatistics.setText("Statistics");

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, statisticsFragment);
                transaction.commit();
            }
        });


    }

    public void ResetButton(){
        buttonhome.setBackgroundResource(R.drawable.li_home_grey);
        buttonAccont.setBackgroundResource(R.drawable.li_user);
        buttonstatistics.setBackgroundResource(R.drawable.li_statistics);
        buttonwacht.setBackgroundResource(R.drawable.li_clock);
        buttonseach.setBackgroundResource(R.drawable.li_search);
    }

    public void ResetText(){
        textViewHome.setText("");
        textViewAccount.setText("");
        textViewSearch.setText("");
        textViewStatistics.setText("");
        textViewWatch.setText("");
    }


}