package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RitmoAtualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RitmoAtualFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewFluxoAtual, textViewConsumoMedioHora, textViewConsumoMedioMin;
    public ImageView arrowback;
    public TextView back_home;

    private final android.os.Handler handler = new android.os.Handler();
    private final int intervaloAtualizacao = 15000;
    private Runnable atualizadorConsumo;

    public RitmoAtualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RitmoAtualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RitmoAtualFragment newInstance(String param1, String param2) {
        RitmoAtualFragment fragment = new RitmoAtualFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ritmo_atual, container, false);


        arrowback = view.findViewById(R.id.arrow_back);
        back_home = view.findViewById(R.id.back_home);

        arrowback.setOnClickListener(v -> BackHome());
        back_home.setOnClickListener(v -> BackHome());

        textViewFluxoAtual = view.findViewById(R.id.textViewFluxoCorrente);
        textViewConsumoMedioHora = view.findViewById(R.id.textViewConsumoMedioH);
        textViewConsumoMedioMin = view.findViewById(R.id.textViewConsumoMedioMin);


        AtomicReference<String> vazao = new AtomicReference<>("");
        AtomicReference<String> totalLitros = new AtomicReference<>("");



        SharedPreferences prefs = requireActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);
        String email = prefs.getString("email", null);

        atualizadorConsumo = new Runnable() {
            @Override
            public void run() {
                buscarDadosConsumo();
                handler.postDelayed(this, intervaloAtualizacao);
            }
        };
        handler.post(atualizadorConsumo);

        if (email != null) {
            new Thread(() -> {
                try {
                    String link = "https://f5e0-138-94-194-66.ngrok-free.app/letcontrolphp/buscar_consumo.php?email=" + URLEncoder.encode(email, "UTF-8");
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String linha;
                    while ((linha = in.readLine()) != null) {
                        result.append(linha);
                    }
                    in.close();

                    String json = result.toString();
                    JSONArray array = new JSONArray(json);


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        double vazaoOriginal = obj.getDouble("vazao_l_min");
                        double vazaoMultiplicada = vazaoOriginal * 4;
                        textViewFluxoAtual.setText((int) vazaoOriginal);
                        textViewConsumoMedioMin.setText((int) vazaoMultiplicada);
                        textViewConsumoMedioHora.setText((int) (vazaoMultiplicada * 60));
                        totalLitros.set(obj.getString("total_litros"));
                        String data = obj.getString("data_leitura");

                        Log.d("CONSUMO", "Vazão: " + vazao + " L/min | Total: " + totalLitros + " L | Data: " + data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Log.e("CONSUMO", "Email não encontrado no SharedPreferences.");
        }





        return view;


    }

    public void BackHome(){

        handler.removeCallbacks(atualizadorConsumo);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo, new Fragment_Home());
        transaction.addToBackStack(null);
        transaction.commit();

    }



    private void buscarDadosConsumo() {
        if (!isAdded()) return;
        SharedPreferences prefs = requireActivity().getSharedPreferences("login", android.content.Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email != null) {
            new Thread(() -> {
                try {
                    String link = "https://caca-138-94-194-166.ngrok-free.app/letcontrolphp/busca_consumo.php?email=" + URLEncoder.encode(email, "UTF-8");
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String linha;
                    while ((linha = in.readLine()) != null) {
                        result.append(linha);
                    }
                    in.close();

                    String json = result.toString();
                    JSONArray array = new JSONArray(json);


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        double vazaoOriginal = obj.getDouble("vazao_l_min");
                        double vazaoMultiplicada = vazaoOriginal * 4;
                        textViewFluxoAtual.setText(String.valueOf((int) vazaoOriginal));
                        textViewConsumoMedioMin.setText(String.valueOf((int) vazaoMultiplicada) + " L/Min");
                        textViewConsumoMedioHora.setText(String.valueOf((int) (vazaoMultiplicada * 60)) + " L/hora");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Log.e("CONSUMO", "Email não encontrado no SharedPreferences.");
        }
    }

}