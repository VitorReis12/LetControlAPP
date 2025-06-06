package com.example.letcontrol;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class FluxoAtualActivity extends AppCompatActivity {

    TextView back_home, textViewFluxoAtual, textViewConsumoMedioHora, textViewConsumoMedioMin;
    ImageView arrowback;
    private Handler handler = new Handler();
    private final int INTERVALO = 15000;

    private Runnable tarefaAtualizacao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fluxo_atual);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowback = findViewById(R.id.imageViewBackRitmoAtualAc);
        back_home = findViewById(R.id.textViewRitmoAtualAc);

        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textViewFluxoAtual = findViewById(R.id.TextViewFluxoCorrenteAC);
        textViewConsumoMedioHora = findViewById(R.id.textViewLitrosHoraAc);
        textViewConsumoMedioMin = findViewById(R.id.textViewLMinAc);

        iniciarAtualizacao();
    }

    private void iniciarAtualizacao() {
        tarefaAtualizacao = new Runnable() {
            @Override
            public void run() {
                buscarDados();
                handler.postDelayed(this, INTERVALO);
            }
        };
        handler.post(tarefaAtualizacao); // começa imediatamente
    }


    @SuppressLint("SetTextI18n")
    private void buscarDados() {
        new Thread(() -> {
            try {
                SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                String email = prefs.getString("email", null);

                if (email == null || email.isEmpty()) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Email não encontrado", Toast.LENGTH_SHORT).show());
                    return;
                }

                URL url = new URL("https://6b3d-143-0-189-182.ngrok-free.app/letcontrolphp/busca_consumo.php?email=" + URLEncoder.encode(email, "UTF-8"));
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");

                InputStream resposta = conexao.getInputStream();
                Scanner scanner = new Scanner(resposta);
                StringBuilder resultado = new StringBuilder();

                while (scanner.hasNext()) {
                    resultado.append(scanner.nextLine());
                }

                JSONArray array = new JSONArray(resultado.toString());
                if (array.length() > 0) {
                    JSONObject json = array.getJSONObject(0);
                    String fluxo = json.getString("vazao_l_min");



                    runOnUiThread(() -> {
                        textViewFluxoAtual.setText(fluxo + " ");
                        textViewConsumoMedioHora.setText(fluxo + " L/h");
                        float f = Float.parseFloat(fluxo);
                        float l = f/60;
                        String lmin = String.valueOf(l);
                        textViewConsumoMedioMin.setText( lmin + " L/m");
                        Log.d(TAG, "buscarDados: " + fluxo);
                    });
                }

            } catch (Exception e) {
                Log.d(TAG, "buscarDados: "+e);
                runOnUiThread(() ->

                        Toast.makeText(getApplicationContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}
