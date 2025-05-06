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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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


                new Thread(() -> {
                    try {
                        URL url = new URL("http://letcontrol.free.nf/letcontrolphp/cadastro.php");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setConnectTimeout(15000);
                        conn.setReadTimeout(15000);

                        String nome = inputNome.getText().toString().trim();
                        String email = inputEmail.getText().toString().trim();
                        String senha = inputSenha.getText().toString().trim();
                        String cfSenha = inputCfSenha.getText().toString().trim();

                        if (!senha.equals(cfSenha)) {
                            runOnUiThread(() ->
                                    Toast.makeText(getApplicationContext(), "As senhas não coincidem", Toast.LENGTH_LONG).show()
                            );
                            return;
                        }

                        String estado = "SP";

                        String postData = "nome=" + URLEncoder.encode(nome, "UTF-8") +
                                "&email=" + URLEncoder.encode(email, "UTF-8") +
                                "&senha=" + URLEncoder.encode(senha, "UTF-8") +
                                "&estado=" + URLEncoder.encode(estado, "UTF-8");

                        OutputStream os = conn.getOutputStream();
                        os.write(postData.getBytes());
                        os.flush();
                        os.close();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();

                        JSONObject json = new JSONObject(response.toString());
                        String status = json.getString("status");

                        runOnUiThread(() -> {
                            if (status.equals("ok")) {
                                String token = null;
                                try {
                                    token = json.getString("token");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                // Você pode salvar o token aqui usando SharedPreferences
                                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), TutorialFragmentsActivity.class);
                                intent.putExtra("token", token);
                                finish();
                                startActivity(intent);
                            } else {
                                String mensagem = json.optString("mensagem", "Erro desconhecido");
                                Toast.makeText(getApplicationContext(), "Erro: " + mensagem, Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (Exception e) {
                        runOnUiThread(() ->
                                Toast.makeText(getApplicationContext(), "Erro de conexão: " + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                        e.printStackTrace();
                    }
                }).start();
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