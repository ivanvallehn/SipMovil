package org.grupovision.sipmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grupovision.sipmovil.R;
import org.grupovision.sipmovil.adapters.CaseRVAdapter;
import org.grupovision.sipmovil.models.Case;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseRVFragment extends Fragment implements View.OnClickListener, CaseRVAdapter.OnItemClickListener {

    RecyclerView rvCase;
    private FloatingActionButton fabAdd;
    private CallbackInterface mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (CallbackInterface) getActivity();
    }

    public CaseRVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_case_rv, container, false);
        rvCase = (RecyclerView) view.findViewById(R.id.rvCase);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        CaseRVAdapter adapter = new CaseRVAdapter(Case.getCases(getContext()),this);
        rvCase.setLayoutManager(layoutManager);
        rvCase.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view) {

        mListener.addCase();
    }

    @Override
    public void OnItemClick(Case _case) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content,DetailCaseFragment.newInstance(_case))
                .addToBackStack(null)
                .commit();
    }


    public interface CallbackInterface{
        public void addCase();
    }
}