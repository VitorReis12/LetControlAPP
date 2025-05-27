package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TextView MeuPerfil, TermosECondicoes, PoliticaDePrivacidade, Logout, RedeDoAparelho;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccontFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        MeuPerfil = view.findViewById(R.id.textViewMeuPerfil);
        TermosECondicoes = view.findViewById(R.id.textViewTermosECondicoes);
        PoliticaDePrivacidade = view.findViewById(R.id.textViewPoliticaDePrivacidade);
        Logout = view.findViewById(R.id.logout);
        RedeDoAparelho = view.findViewById(R.id.textViewRedeDoAprarelho);

        Logout.setOnClickListener(V -> logouttext());
        TermosECondicoes.setOnClickListener(V -> AbrirTermos());
        PoliticaDePrivacidade.setOnClickListener(V -> AbrirPoliticas());
        RedeDoAparelho.setOnClickListener(V -> Rede());
        return view;
    }

    public void logouttext(){
        requireActivity().finish();
    }

    public void AbrirTermos(){
        Intent intent = new Intent(getContext(), TermosActivity.class);
        startActivity(intent);
    }

    public void AbrirPoliticas(){
        Intent intent = new Intent(getContext(), PoliticaActivity.class);
        startActivity(intent);
    }

    public void Rede(){
      Intent intent = new Intent(getContext(), ConnectingWifiActivity.class);
      startActivity(intent);
    };
}