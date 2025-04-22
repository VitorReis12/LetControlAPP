package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {

    TextView textViewActivityPoliticas;
    Button buttonCadastrar;

    CheckBox checkBoxTermos;

    TextInputEditText inputNome, inputEmail, inputSenha, inputCfSenha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textViewActivityPoliticas = findViewById(R.id.textViewAbrirActivityPoliticas);
        buttonCadastrar = findViewById(R.id.buttonCadastroLogin);
        checkBoxTermos = findViewById(R.id.checkBoxTermos);

        inputNome = findViewById(R.id.InputNomeCD);
        inputEmail = findViewById(R.id.InputEmailCD);
        inputSenha = findViewById(R.id.InputSenhaCD);
        inputCfSenha = findViewById(R.id.InputCFSenhaCD);


        buttonCadastrar.setEnabled(false);
        buttonCadastrar.setBackgroundColor(Color.parseColor("#7BB4CC"));


        textViewActivityPoliticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermosActivity.class);
                startActivity(intent);
            }
        });


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputNome.getText().toString().isEmpty() ||
                        inputEmail.getText().toString().isEmpty() ||
                        inputSenha.getText().toString().isEmpty() ||
                        inputCfSenha.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    return;
                }


                Intent intent = new Intent(getApplicationContext(), TutorialFragmentsActivity.class);
                finish();
                startActivity(intent);
            }
        });


        checkBoxTermos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean isChecked) {
                buttonCadastrar.setEnabled(isChecked);
                if (!isChecked) {
                    buttonCadastrar.setBackgroundColor(Color.parseColor("#7BB4CC"));
                } else {
                    buttonCadastrar.setBackgroundResource(R.drawable.button_login);
                }

            }
        });

    }
}