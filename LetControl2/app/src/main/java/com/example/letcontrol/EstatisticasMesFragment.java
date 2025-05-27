package com.example.letcontrol;

import static java.util.Objects.*;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstatisticasMesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstatisticasMesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgressBar progressBar;
    Button buttonMeta;

    TextView textViewMeta;

    public EstatisticasMesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstatisticasMesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstatisticasMesFragment newInstance(String param1, String param2) {
        EstatisticasMesFragment fragment = new EstatisticasMesFragment();
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
        View view = inflater.inflate(R.layout.fragment_estatisticas_mes, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        textViewMeta = view.findViewById(R.id.textViewMetaProgresbar);
        buttonMeta = view.findViewById(R.id.buttonMeta);
        buttonMeta.setOnClickListener(V -> AbrirDialog());

        Animation scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        progressBar.startAnimation(scaleUp);

        int targetProgress = 30000;

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, targetProgress);
        animation.setDuration(1500); // duração da animação (1.5s)
        animation.setInterpolator(new DecelerateInterpolator()); // animação suave
        animation.start();

        return view;
    }



    public void AbrirDialog() {
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
                            progressBar.setProgress(valor);
                            textViewMeta.setText(String.valueOf(valor));
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