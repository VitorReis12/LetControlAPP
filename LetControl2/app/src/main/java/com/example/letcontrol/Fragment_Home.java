package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgressBar progressBar;
    public String variableprogress;
    public TextView progressText;

    public ImageView imageViewAccount;

    private Button buttonHome, buttonUser, buttonInformation, buttonWarnings, buttonRitmoAtual, buttonVerMetas;
    public UserFragment accontFragment;

    public String TextMeta = "";
    public TextView textViewDialog, textViewMeta, textViewHome, textViewWarnings, textViewInformation, textViewUser;


    public AlertDialog incomeAlert;

    public Fragment_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Home newInstance(String param1, String param2) {
        Fragment_Home fragment = new Fragment_Home();
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
        View view = inflater.inflate(R.layout.fragment__home, container, false);

//        progressBar = view.findViewById(R.id.progressBar);
//        progressText = view.findViewById(R.id.textViewProgressBar);
//
//        PorcentagemProgressbar(progressBar);
//
//        textViewMeta = view.findViewById(R.id.textViewMeta);
//        textViewDialog = view.findViewById(R.id.textViewDialog);
//        textViewDialog.setOnClickListener(v -> AbrirDialog());

        buttonVerMetas = view.findViewById(R.id.buttonVerMetas);
        buttonWarnings = view.findViewById(R.id.buttonAvisos);
        buttonUser = view.findViewById(R.id.buttonPerfil);
        buttonHome = view.findViewById(R.id.buttonInicio);
        buttonInformation = view.findViewById(R.id.buttonInforme);
        imageViewAccount = view.findViewById(R.id.imageViewAccount);
        textViewWarnings = view.findViewById(R.id.textViewAvisos);
        textViewHome = view.findViewById(R.id.textViewInicio);
        textViewUser = view.findViewById(R.id.textViewPerfil);
        textViewInformation = view.findViewById(R.id.textViewInforme);
        buttonRitmoAtual = view.findViewById(R.id.button_ritmo_atual);


        buttonVerMetas.setOnClickListener(V -> VerMetas());
        imageViewAccount.setOnClickListener(v -> FrameAccount());
        buttonRitmoAtual.setOnClickListener(v -> RitmoAtual());


        return view;
    }

    //Senha do apk é "letcontrol"
    public void PorcentagemProgressbar(ProgressBar progressBar) {
        variableprogress = String.valueOf(progressBar.getProgress());
        progressText.setText(variableprogress + "%");

    }

    public void FrameAccount() {


//        textViewUser.setTextColor(Color.parseColor("#2CB4ED"));
//        buttonUser.setBackgroundResource(R.drawable.perfil_blue);
//
//
        androidx.fragment.app.FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo, new UserFragment());
        transaction.commit();
    }

    public void ResetButton() {
        buttonHome.setBackgroundResource(R.drawable.inicio_gray);
        buttonUser.setBackgroundResource(R.drawable.perfil_gray);
        buttonWarnings.setBackgroundResource(R.drawable.aviso_new_gray);
        buttonInformation.setBackgroundResource(R.drawable.informe_gray);
    }


    public void VerMetas(){
        androidx.fragment.app.FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo, new InformationFragment());
        transaction.commit();
    }
    public void ResetText() {
        textViewHome.setTextColor(Color.parseColor("#BFC0C2"));
        textViewUser.setTextColor(Color.parseColor("#BFC0C2"));
        textViewInformation.setTextColor(Color.parseColor("#BFC0C2"));
        textViewWarnings.setTextColor(Color.parseColor("#BFC0C2"));
    }

//    public void AbrirDialog() {
//        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout_meta, null);
//        TextInputEditText editText = dialogView.findViewById(R.id.editText);
//
//        new MaterialAlertDialogBuilder(requireContext())
//                .setTitle("Alterar Minha Meta")
//                .setView(dialogView)
//                .setPositiveButton("OK", (dialog, which) -> {
//                    textViewMeta.setText(MessageFormat.format("{0}L", Objects.requireNonNull(editText.getText())));
//                })
//                .setNegativeButton("Close", (dialog, which) -> dialog.dismiss())
//                .show();
//    }

    public void RitmoAtual(){

        androidx.fragment.app.FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo, new RitmoAtualFragment());
        transaction.commit();


    }


}