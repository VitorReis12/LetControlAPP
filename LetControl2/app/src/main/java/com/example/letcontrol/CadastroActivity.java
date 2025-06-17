package com.example.letcontrol;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
        buttonCadastrar.setBackgroundResource(R.drawable.button_off_cadastro);


        textViewActivityPoliticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(getApplicationContext(), TermosActivity.class);
                startActivity(intent);
            }
        });


        buttonCadastrar.setOnClickListener(view -> {

            if (inputNome.getText().toString().isEmpty() ||
                    inputEmail.getText().toString().isEmpty() ||
                    inputSenha.getText().toString().isEmpty() ||
                    inputCfSenha.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                return;
            }

            String nome = inputNome.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String senha = inputSenha.getText().toString().trim();
            String cfSenha = inputCfSenha.getText().toString().trim();

            SharedPreferences preferences = getSharedPreferences("cadastro",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.apply();

            SharedPreferences preferences2 = getSharedPreferences("cadastro2",MODE_PRIVATE);
            SharedPreferences.Editor editor2 = preferences.edit();
            editor.putString("nome", nome);
            editor.apply();

            if (!senha.equals(cfSenha)) {
                Toast.makeText(getApplicationContext(), "As senhas não coincidem", Toast.LENGTH_LONG).show();
                return;
            }

            new Thread(() -> {
                try {
                    String estado = "SP";

                    String postData = "nome=" + URLEncoder.encode(nome, "UTF-8") +
                            "&email=" + URLEncoder.encode(email, "UTF-8") +
                            "&senha=" + URLEncoder.encode(senha, "UTF-8") +
                            "&estado=" + URLEncoder.encode(estado, "UTF-8");

                    URL url = new URL("https://88ff-177-23-2-16.ngrok-free.app/letcontrolphp/cadastro.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    conn.setRequestProperty("Content-Length", String.valueOf(postData.getBytes().length));
                    conn.setRequestProperty("Content-Language", "pt-BR");
                    conn.setUseCaches(false);

                    try (OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "utf-8")) {
                        os.write(postData);
                        os.flush();
                    }

                    StringBuilder resposta = new StringBuilder();
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            resposta.append(line);
                        }
                    }
                    conn.disconnect();

                    JSONObject json = new JSONObject(resposta.toString());
                    String status = json.getString("status");

                    if (!isFinishing() && !isDestroyed()) {
                        runOnUiThread(() -> {
                            if (status.equals("ok")) {
                                String token = json.optString("usuario_id", ""); // corrigido aqui

                                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), TutorialFragmentsActivity.class);
                                intent.putExtra("user", token);
                                startActivity(intent);
                                finish();

                                Log.d(TAG, "Resposta do servidor: " + resposta.toString());

                            } else {
                                String mensagem = json.optString("mensagem", "Erro desconhecido");
                                Toast.makeText(getApplicationContext(), "Erro: " + mensagem, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "Erro: " + mensagem);
                                Log.d(TAG, "Resposta do servidor: " + resposta.toString());
                            }
                        });
                    }

                } catch (Exception e) {
                    if (!isFinishing() && !isDestroyed()) {
                        runOnUiThread(() ->
                                Toast.makeText(getApplicationContext(), "Erro de conexão: " + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                    }
                    e.printStackTrace();
                }
            }).start();
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