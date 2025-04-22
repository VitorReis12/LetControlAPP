package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class TutorialFragmentsActivity extends AppCompatActivity {


    private Bem_vindo_tutorial1Fragment tutorial1Fragment;
    private Tela2_bt_Fragment bt3Fragment;
    private Motivo_tutorial_3_Fragment tutorial3Fragment;
    private Wifi_Tutorial_Fragment tutorialwifi;

    int NumberPage = 0;
    Button buttontutorial;

    FragmentTransaction fragmentTransaction;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial_fragments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tutorial1Fragment = new Bem_vindo_tutorial1Fragment();
        bt3Fragment = new Tela2_bt_Fragment();
        tutorial3Fragment = new Motivo_tutorial_3_Fragment();
        tutorialwifi = new Wifi_Tutorial_Fragment();


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameconteudo_tutorial,tutorial1Fragment);
        fragmentTransaction.commit();


        buttontutorial = findViewById(R.id.button_mudar_tutuorial);

        buttontutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MudarActivity();
            }
        });

    }

    public void MudarActivity(){

        if (NumberPage == 0) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameconteudo_tutorial,bt3Fragment);
            fragmentTransaction.commit();
            NumberPage += 1;
        } else if (NumberPage == 1) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameconteudo_tutorial,tutorial3Fragment);
            fragmentTransaction.commit();
            NumberPage += 1;
        } else if (NumberPage == 2) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameconteudo_tutorial,tutorialwifi);
            fragmentTransaction.commit();
            NumberPage += 1;

        } else if (NumberPage == 3) {
            Intent intent = new Intent(getApplicationContext(), ConnectingWifiActivity.class);
            finish();
            startActivity(intent);
        }

    }


}