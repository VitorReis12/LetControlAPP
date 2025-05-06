package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

    public Button buttonhome, buttonUser, buttoninformation, buttonwarnings;

    private TextView textViewHome, textViewWarnings, textViewinformation, textViewUser;

    private Fragment_Home fragmentHome;
    private UserFragment userFragment;

    private WarningsFragment warningsFragment;

    private InformationFragment informationFragment;


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

        userFragment = new UserFragment();
        fragmentHome = new Fragment_Home();
        warningsFragment = new WarningsFragment();
        informationFragment = new InformationFragment();

        textViewWarnings = findViewById(R.id.textViewAvisos);
        textViewHome = findViewById(R.id.textViewInicio);
        textViewUser = findViewById(R.id.textViewPerfil);
        textViewinformation = findViewById(R.id.textViewInforme);

        buttonwarnings = findViewById(R.id.buttonAvisos);
        buttonUser = findViewById(R.id.buttonPerfil);
        buttonhome = findViewById(R.id.buttonInicio);
        buttoninformation = findViewById(R.id.buttonInforme);

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_conteudo, fragmentHome);
        transaction.commit();

        buttonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonhome.setBackgroundResource(R.drawable.inicio_blue);

                ResetText();
                textViewHome.setTextColor(Color.parseColor("#2CB4ED"));

                androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, fragmentHome );
                transaction.commit();
            }
        });



        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonUser.setBackgroundResource(R.drawable.perfil_blue);

                ResetText();
                textViewUser.setTextColor(Color.parseColor("#2CB4ED"));

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, userFragment);
                transaction.commit();
            }
        });

        buttonwarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttonwarnings.setBackgroundResource(R.drawable.avisos_blue);


                ResetText();
                textViewWarnings.setTextColor(Color.parseColor("#2CB4ED"));

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo, warningsFragment);
                transaction.commit();
            }
        });

        buttoninformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetButton();
                buttoninformation.setBackgroundResource(R.drawable.informe_blue);

                ResetText();
                textViewinformation.setTextColor(Color.parseColor("#2CB4ED"));

                FragmentTransaction transaction = getSupportFragmentManager ().beginTransaction();
                transaction.replace(R.id.frame_conteudo, informationFragment);
                transaction.commit();
            }
        });


    }

    public void ResetButton(){
        buttonhome.setBackgroundResource(R.drawable.inicio_gray);
        buttonUser.setBackgroundResource(R.drawable.perfil_gray);
        buttonwarnings.setBackgroundResource(R.drawable.aviso_new_gray);
        buttoninformation.setBackgroundResource(R.drawable.informe_gray);
    }

    public void ResetText(){
        textViewHome.setTextColor(Color.parseColor("#BFC0C2"));
        textViewUser.setTextColor(Color.parseColor("#BFC0C2"));
        textViewinformation.setTextColor(Color.parseColor("#BFC0C2"));
        textViewWarnings.setTextColor(Color.parseColor("#BFC0C2"));
    }


}