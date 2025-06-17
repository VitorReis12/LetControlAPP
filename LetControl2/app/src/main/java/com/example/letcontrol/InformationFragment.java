package com.example.letcontrol;


import static com.example.letcontrol.R.drawable.*;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    ImageButton buttonConsumo, buttonGastos, buttonMetas;

    TextView textViewConsumo, textViewGastos, textViewMetas;

    ConsumoFragment consumoFragment = new ConsumoFragment();
    GastosFragment gastosFragment = new GastosFragment();

    MetasFragment metasFragment = new MetasFragment();

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

            buttonConsumo = view.findViewById(R.id.ButtonConsumoRT);
            buttonGastos = view.findViewById(R.id.ButtonGastosRT);
            buttonMetas = view.findViewById(R.id.ButtonMetasRT);

            textViewConsumo = view.findViewById(R.id.textViewConsumoRT);
            textViewGastos = view.findViewById(R.id.textViewGastosRT);
            textViewMetas = view.findViewById(R.id.textViewMetasRT);

            buttonConsumo.setOnClickListener(V-> FragmentConsumo());
            buttonGastos.setOnClickListener(V-> FragmentGastos());
            buttonMetas.setOnClickListener(V-> FragmentMetas());

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            if (transaction == null) {
            transaction.add(R.id.frame_conteudo_estatisticas, consumoFragment);
            } else {
            transaction.replace(R.id.frame_conteudo_estatisticas, consumoFragment);
            }
            transaction.commit();

            return view;


    }

    public void FragmentConsumo(){
        try {
            ResetButton();
            buttonConsumo.setBackgroundResource(R.drawable.button_relatorio);
            textViewConsumo.setTextColor(Color.parseColor("#0A98FF"));
            buttonConsumo.setImageResource(water_relatorio);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_conteudo_estatisticas,consumoFragment);
            transaction.commit();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cuidado ao Utilizar o App", Toast.LENGTH_SHORT).show();
        }



    }
    public void FragmentGastos() {
        try {
            if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
                ResetButton();
                buttonGastos.setBackgroundResource(R.drawable.button_relatorio);
                textViewGastos.setTextColor(Color.parseColor("#0A98FF"));
                buttonGastos.setImageResource(consumo_de_agua_white);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_conteudo_estatisticas, gastosFragment);
                transaction.commit(); // ou .commitAllowingStateLoss() se preferir
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cuidado ao Utilizar o App", Toast.LENGTH_SHORT).show();
        }
    }

    public void FragmentMetas(){

        try {
            ResetButton();
            buttonMetas.setBackgroundResource(R.drawable.button_relatorio);
            textViewMetas.setTextColor(Color.parseColor("#0A98FF"));
            buttonMetas.setImageResource(metas_white);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_conteudo_estatisticas, metasFragment);
            transaction.commit();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cuidado ao Utilizar o App", Toast.LENGTH_SHORT).show();
        }


    }


    public void ResetButton(){
        try {
            buttonConsumo.setBackgroundResource(R.drawable.button_relatorio_gray);
            buttonGastos.setBackgroundResource(R.drawable.button_relatorio_gray);
            buttonMetas.setBackgroundResource(R.drawable.button_relatorio_gray);
            buttonConsumo.setImageResource(R.drawable.water_gray);
            buttonGastos.setImageResource(R.drawable.gastos_but_relatorio);
            buttonMetas.setImageResource(R.drawable.metas_but_relatorio);
            textViewConsumo.setTextColor(Color.parseColor("#757474"));
            textViewGastos.setTextColor(Color.parseColor("#757474"));
            textViewMetas.setTextColor(Color.parseColor("#757474"));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Cuidado ao Utilizar o App", Toast.LENGTH_SHORT).show();
        }

    }






}