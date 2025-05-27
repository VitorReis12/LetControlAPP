package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bem_vindo_tutorial1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bem_vindo_tutorial1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewBemVindo;

    public Bem_vindo_tutorial1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bem_vindo_tutorial1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bem_vindo_tutorial1Fragment newInstance(String param1, String param2) {
        Bem_vindo_tutorial1Fragment fragment = new Bem_vindo_tutorial1Fragment();
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



        View view =inflater.inflate(R.layout.fragment_bem_vindo_tutorial1, container, false);
        textViewBemVindo = view.findViewById(R.id.textViewTTBemVindo);


        SharedPreferences prefs = requireActivity().getSharedPreferences("nomecadastro", android.content.Context.MODE_PRIVATE);

        String nome = prefs.getString("nomecadastro", null);

        textViewBemVindo.setText("Bem Vindo, " + nome + "!");

        return view;
    }
}