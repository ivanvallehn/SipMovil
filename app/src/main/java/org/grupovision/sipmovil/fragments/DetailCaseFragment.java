package org.grupovision.sipmovil.fragments;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.grupovision.sipmovil.R;
import org.grupovision.sipmovil.models.Case;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCaseFragment extends Fragment {
    private static final String ARG_PARAM_CASE = "paramCase";
    private Case _case;
    private TextView txtIdentificationNumber,txtName ;
    private ImageView imgProfile;


    public static DetailCaseFragment newInstance(Case _case){
        DetailCaseFragment fragment = new DetailCaseFragment();
        Bundle args= new Bundle();
        args.putSerializable(ARG_PARAM_CASE, _case);
        fragment.setArguments(args);
        return  fragment;
    }

    public DetailCaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments()!=null){
            this._case = (Case) getArguments().getSerializable(ARG_PARAM_CASE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_case,menu);
        MenuItem menuDelete = menu.findItem(R.id.menuDelete);
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.important))
                        .setMessage(getString(R.string.confirmationDelete))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Case.deleteCaseById(getContext(), _case.getCaseId());
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


                return false;
            }
        });

        MenuItem menuEdit = menu.findItem(R.id.menuEdit);
        menuEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, InputCaseFragment.newInstance(_case))
                        .addToBackStack(null)
                        .commit();

                return false;
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_case, container, false);

        txtIdentificationNumber = (TextView) view.findViewById(R.id.txtIdentificationNumber);
        txtName = (TextView) view.findViewById(R.id.txtName);

        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);

        if(_case.getIdentificationNumber()!=null)
            txtIdentificationNumber.setText(_case.getIdentificationNumber());

        if(_case.getLastName()!=null)
            txtName.setText(" "+_case.getFirstName()+" " + _case.getLastName());
        else
            txtName.setText(" "+_case.getFirstName());

        if (_case.getPathPhoto()!=null)
            loadPhoto(_case.getPathPhoto());

        return view;
    }

    private void loadPhoto(String fileName)
    {
        File imgFile = new File(fileName);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgProfile.setImageBitmap(bitmap);
        }
    }

}
