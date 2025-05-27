package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeuPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeuPerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    TextInputEditText nomeinput, emailinput;
    Button buttonSalvar;


    public MeuPerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeuPerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeuPerfilFragment newInstance(String param1, String param2) {
        MeuPerfilFragment fragment = new MeuPerfilFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meu_perfil, container, false);

        emailinput = view.findViewById(R.id.InputTextEmailPerfil);
        nomeinput = view.findViewById(R.id.InputTextNomePerfil);
        buttonSalvar = view.findViewById(R.id.buttonSalvarPerfil);
        buttonSalvar.setOnClickListener(V -> Salvar());

//        SharedPreferences prefs = requireActivity().getSharedPreferences("login", android.content.Context.MODE_PRIVATE);
//        String email = prefs.getString("email", null);
//
//        if (email != null) {
//            buscarDadosUsuario(email);
//        } else {
//            Toast.makeText(getActivity(), "Email não encontrado nas preferências.", Toast.LENGTH_SHORT).show();
//        }

        return view;
    }

    private void buscarDadosUsuario(String emailUsuario) {
//        new Thread(() -> {
//            try {
//                URL url = new URL("https://f6da-143-0-191-104.ngrok-free.app/letcontrolphp/buscar_nome.php");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//                String postData = "email=" + URLEncoder.encode(emailUsuario, "UTF-8");
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                writer.write(postData);
//                writer.flush();
//                writer.close();
//                os.close();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//
//                reader.close();
//                conn.disconnect();
//
//                JSONObject json = new JSONObject(response.toString());
//                String status = json.getString("status");
//
//                requireActivity().runOnUiThread(() -> {
//                    if (status.equals("ok")) {
//                        try {
//                            String nome = json.getString("nome");
//                            nomeinput.setHint(nome);
//                            emailinput.setHint(emailUsuario);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(), "Erro no JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getActivity(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                requireActivity().runOnUiThread(() ->
//                        Toast.makeText(getActivity(), "Erro de conexão: " + e.getMessage(), Toast.LENGTH_LONG).show()
//                );
//            }
//        }).start();
    }

    public void Salvar(){

    }


}



