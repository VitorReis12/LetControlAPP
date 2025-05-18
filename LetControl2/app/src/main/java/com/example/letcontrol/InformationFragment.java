package com.example.letcontrol;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonHoje, buttonMes, buttonAno;

    EstatisticasMesFragment estatisticasMesFragment = new EstatisticasMesFragment();
    EstatisticasAnoFragment estatisticasAnoFragment = new EstatisticasAnoFragment();
    EstatisticasHojeFragment estatisticasHojeFragment = new EstatisticasHojeFragment();

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        buttonHoje = view.findViewById(R.id.buttonHojeEstatisticas);
        buttonMes = view.findViewById(R.id.buttonMesEstatisticas);
        buttonAno = view.findViewById(R.id.buttonAnoEstatisticas);

        buttonHoje.setOnClickListener(V-> FragmentHoje());
        buttonMes.setOnClickListener(V-> FragmentMes());
        buttonAno.setOnClickListener(V-> FragmentAno());

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.frame_conteudo_estatisticas, estatisticasMesFragment);
        transaction.commit();

        return view;
    }

    public void FragmentHoje(){

        ResetButton();
        buttonHoje.setBackgroundResource(R.drawable.button_branco_estatisticas);
        buttonHoje.setTextColor(Color.parseColor("#16ACF8"));

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo_estatisticas,estatisticasHojeFragment);
        transaction.commit();


    }
    public void FragmentMes(){

        ResetButton();
        buttonMes.setBackgroundResource(R.drawable.button_branco_estatisticas);
        buttonMes.setTextColor(Color.parseColor("#16ACF8"));

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo_estatisticas,estatisticasMesFragment);
        transaction.commit();


    }
    public void FragmentAno(){

        ResetButton();
        buttonAno.setBackgroundResource(R.drawable.button_branco_estatisticas);
        buttonAno.setTextColor(Color.parseColor("#16ACF8"));

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_conteudo_estatisticas,estatisticasAnoFragment);
        transaction.commit();

    }

    public void ResetButton(){
        buttonHoje.setBackgroundResource(R.drawable.button_transparente);
        buttonAno.setBackgroundResource(R.drawable.button_transparente);
        buttonMes.setBackgroundResource(R.drawable.button_transparente);
        buttonHoje.setTextColor(Color.parseColor("#ffffff"));
        buttonAno.setTextColor(Color.parseColor("#ffffff"));
        buttonMes.setTextColor(Color.parseColor("#ffffff"));
    }






}