package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.letcontrol.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    public Button buttonhome, buttonUser, buttoninformation, buttonwarnings;
    private TextView textViewHome, textViewWarnings, textViewinformation, textViewUser;

    private Fragment_Home fragmentHome;
    private UserFragment userFragment;
    private WarningsFragment warningsFragment;
    private InformationFragment informationFragment;

    private Fragment currentFragment;

    private BottomNavigationView bottomNavigation;

    ActivityMainBinding binding;

    //CardView cardViewInicio, cardViewRelatorio, cardViewAvisos, cardViewPerfil;

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

        try {
            // Instanciar fragments
            fragmentHome = new Fragment_Home();
            userFragment = new UserFragment();
            warningsFragment = new WarningsFragment();
            informationFragment = new InformationFragment();



            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            // TextViews
//            textViewWarnings = findViewById(R.id.textViewAvisos);
//            textViewHome = findViewById(R.id.textViewInicio);
//            textViewUser = findViewById(R.id.textViewPerfil);
//            textViewinformation = findViewById(R.id.textViewInforme);

            // Buttons
//            buttonwarnings = findViewById(R.id.buttonAvisos);
//            buttonUser = findViewById(R.id.buttonPerfil);
//            buttonhome = findViewById(R.id.buttonInicio);
//            buttoninformation = findViewById(R.id.buttonInforme);

            // Inicializa com fragmentHome
            showFragment(fragmentHome, "HOME");
//            buttonhome.setBackgroundResource(R.drawable.inicio_blue);
//            textViewHome.setTextColor(Color.parseColor("#2CB4ED"));

            bottomNavigation = findViewById(R.id.bottomNavigationView2);

            binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    showFragment(fragmentHome,"HOME");
                    return true;
                } else if (id == R.id.nav_relatorio) {
                    showFragment(informationFragment,"INFO");
                    return true;
                } else if (id == R.id.nav_avisos) {
                    showFragment(warningsFragment,"WARNINGS");
                    return true;
                } else if (id == R.id.nav_perfil) {
                    showFragment(userFragment,"USER");
                    return true;
                }

                return false;
            });



//            // Botões
//            buttonhome.setOnClickListener(v -> {
//                ResetButton();
//                buttonhome.setBackgroundResource(R.drawable.inicio_blue);
//                ResetText();
//                textViewHome.setTextColor(Color.parseColor("#2CB4ED"));
//                showFragment(fragmentHome, "HOME");
//            });
//
//            buttonUser.setOnClickListener(v -> {
//                ResetButton();
//                buttonUser.setBackgroundResource(R.drawable.perfil_blue);
//                ResetText();
//                textViewUser.setTextColor(Color.parseColor("#2CB4ED"));
//                showFragment(userFragment, "USER");
//            });
//
//            buttonwarnings.setOnClickListener(v -> {
//                ResetButton();
//                buttonwarnings.setBackgroundResource(R.drawable.avisos_blue);
//                ResetText();
//                textViewWarnings.setTextColor(Color.parseColor("#2CB4ED"));
//                showFragment(warningsFragment, "WARNINGS");
//            });
//
//            buttoninformation.setOnClickListener(v -> {
//                ResetButton();
//                buttoninformation.setBackgroundResource(R.drawable.informe_blue);
//                ResetText();
//                textViewinformation.setTextColor(Color.parseColor("#2CB4ED"));
//                showFragment(informationFragment, "INFO");
//            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cuidado ao Utilizar o App", Toast.LENGTH_LONG).show();
        }
    }

    // Método central para trocar fragments com segurança

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_conteudo, fragment);
        fragmentTransaction.commit();
    }

    private void showFragment(Fragment fragment, String tag) {
        if (currentFragment == fragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


         transaction.setCustomAnimations(
                 R.anim.fade_in,
                 R.anim.fade_out,
                 R.anim.fade_in,
                 R.anim.fade_out
         );

        transaction.replace(R.id.frame_conteudo, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        currentFragment = fragment;
    }

//    public void ResetButton() {
//        buttonhome.setBackgroundResource(R.drawable.inicio_gray);
//        buttonUser.setBackgroundResource(R.drawable.perfil_gray);
//        buttonwarnings.setBackgroundResource(R.drawable.aviso_new_gray);
//        buttoninformation.setBackgroundResource(R.drawable.informe_gray);
//    }
//
//    public void ResetText() {
//        textViewHome.setTextColor(Color.parseColor("#BFC0C2"));
//        textViewUser.setTextColor(Color.parseColor("#BFC0C2"));
//        textViewinformation.setTextColor(Color.parseColor("#BFC0C2"));
//        textViewWarnings.setTextColor(Color.parseColor("#BFC0C2"));
//    }
}
