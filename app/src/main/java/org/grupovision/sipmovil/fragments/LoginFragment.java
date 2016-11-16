package org.grupovision.sipmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.grupovision.sipmovil.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    Button btnEnter;
    Context context;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnEnter = (Button) view.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        //TODO: Logica de validacion de Usuarios locales? o Webservice?


        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new CaseRVFragment())
                .addToBackStack(null).commit();

    }
}
