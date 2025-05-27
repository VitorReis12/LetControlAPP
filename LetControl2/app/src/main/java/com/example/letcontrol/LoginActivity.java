package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

public class LoginActivity extends AppCompatActivity {
    TextInputEditText InputEmail, InputPassword;
    Button buttonLogin;
    Button buttonTesteTelas;
    TextView textViewErro, textViewActionCadastrar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        InputEmail = findViewById(R.id.InputEmail);
        InputPassword = findViewById(R.id.InputPassword);
        textViewErro = findViewById(R.id.textViewErro);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewActionCadastrar = findViewById(R.id.textViewActionCadastrar);
        buttonTesteTelas = findViewById(R.id.buttonTesteTelas);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = InputEmail.getText().toString();
                String password = InputPassword.getText().toString();

                new Thread(() -> {
                    try {
                        URL url = new URL("https://1c79-143-0-189-24.ngrok-free.app/letcontrolphp/login.php");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        String postData = "email=" + email + "&senha=" + password;
                        OutputStream os = conn.getOutputStream();
                        os.write(postData.getBytes());
                        os.flush();
                        os.close();

                        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", email);
                        editor.apply();


                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();

                        JSONObject json = new JSONObject(response.toString());


                        runOnUiThread(() -> {
                            try {
                                String status = json.getString("status");
                                runOnUiThread(() -> {
                                    if (status.equals("ok")) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        textViewErro.setText("Login inválido.");
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                runOnUiThread(() -> textViewErro.setText("Erro no JSON: " + e.getMessage()));
                            }
                        });

                    } catch (Exception e) {
                        runOnUiThread(() -> textViewErro.setText("Erro de conexão: " + e.getMessage()));
                        e.printStackTrace();
                    }
                }).start();

            }
        });

        textViewActionCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        buttonTesteTelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConnectingWifiActivity.class);
                startActivity(intent);
            }
        });

    }



}