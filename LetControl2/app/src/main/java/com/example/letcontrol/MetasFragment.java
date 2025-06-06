package com.example.letcontrol;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MetasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MetasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton Metas;

    public MetasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MetasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MetasFragment newInstance(String param1, String param2) {
        MetasFragment fragment = new MetasFragment();
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
        View view = inflater.inflate(R.layout.fragment_metas, container, false);


        Metas = view.findViewById(R.id.imageButtonMetasRT);
        Metas.setOnClickListener(V ->DefinirMetas());
        return view;
    }

    public void DefinirMetas(){

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout_meta, null);
        TextInputEditText editText = dialogView.findViewById(R.id.editText);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Alterar Minha Meta")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    String input = String.valueOf(editText.getText());

                    if (!input.isEmpty()) {
                        try {
                            int valor = Integer.parseInt(input);
                            SharedPreferences preferences = requireActivity().getSharedPreferences("metas", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("meta", String.valueOf(valor));
                            editor.apply();
                            Toast.makeText(getContext(), "Metas salvas com sucesso!", Toast.LENGTH_LONG).show();

                        } catch (NumberFormatException e) {
                            Toast.makeText(requireContext(), "Digite um número válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "O campo não pode estar vazio", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Fechar", (dialog, which) -> dialog.dismiss())
                .show();
    }

}