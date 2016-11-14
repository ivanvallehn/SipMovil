package org.grupovision.sipmovil.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import org.grupovision.sipmovil.R;
import org.grupovision.sipmovil.models.Case;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputCaseFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM_CASE ="paramCase";
    private EditText etIdentificationNumber, etFirstname, etLastname;
    private ImageView imgProfile;

    private String pathPhoto;
    private String directory;
    private String fileName;

    private static final Integer TAKE_PHOTO_CODE = 1699;
    private Case _case;

    public static final InputCaseFragment newInstance(Case _case)
    {
        InputCaseFragment fragment = new InputCaseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CASE,_case);
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/hondurasCases/";
        File newdir = new File(directory);
        if(!newdir.exists())
            newdir.mkdirs();

        if(getArguments()!=null){
            _case = (Case) getArguments().getSerializable(ARG_PARAM_CASE);
        }
    }


    public InputCaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_case, container, false);
        etIdentificationNumber = (EditText) view.findViewById(R.id.etIdentificationNumber);
        etFirstname = (EditText) view.findViewById(R.id.etFirstname);
        etLastname = (EditText) view.findViewById(R.id.etLastname);


        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        loadData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_input_case,menu);
        MenuItem menuSave = menu.findItem(R.id.menuSave);
        menuSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(validateFields())
                    save();
                return false;
            }
        });
    }

    private boolean validateFields(){
        boolean validate = true;
        if(etIdentificationNumber.getText().length()==0){
            validate = false;
            etIdentificationNumber.setError(getString(R.string.error_field));
        }

        return validate;
    }

    private void save(){
        String identificationNumber = etIdentificationNumber.getText().toString();
        String firstName = etFirstname.getText().toString();
        String lastName = etLastname.getText().toString();
        String date = "date";
        String pathLeftFinger = "pathLeftFinger";
        String pathRightFinger = "pathRightFinger";
        Integer isProcessed = 0;
        Integer isHit = 0;
        String candidateList = "candidateList";

        if(_case!=null){
            _case.setIdentificationNumber(identificationNumber);
            _case.setFirstName(firstName);
            _case.setLastName(lastName);
            _case.setPathPhoto(pathPhoto);

            Case.updateContact(getContext(),_case);
            Snackbar.make(getView(),getString(R.string.update_success), Snackbar.LENGTH_LONG).show();

        }else
        {
            Case caseSave = new Case( identificationNumber,  firstName,  lastName,  date,  pathPhoto,  pathLeftFinger,  pathRightFinger,  isProcessed,  isHit,  candidateList);
            caseSave.setCaseId((int) Case.insert(getContext(),caseSave));

          if(caseSave.getCaseId()!= -1){
                Snackbar.make(getView(),getString(R.string.save_success), Snackbar.LENGTH_SHORT).show();

            }else
                Snackbar.make(getView(),getString(R.string.failed_save), Snackbar.LENGTH_SHORT).show();
        }

        View v = getActivity().getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void loadData(){
        //ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, TypeNumber.getTypeNumbers(getContext()));

        if(_case!=null){

            if(_case.getIdentificationNumber()!=null)
                etIdentificationNumber.setText(_case.getIdentificationNumber());

            etFirstname.setText(_case.getFirstName());
            if(_case.getLastName()!=null)
                etLastname.setText(_case.getLastName());

            if(_case.getLastName()!=null)
                etLastname.setText(" "+_case.getFirstName());

            if (_case.getPathPhoto()!=null){
                pathPhoto = _case.getPathPhoto();
                fileName = pathPhoto;
                loadPhoto();
            }
        }
    }


    @Override
    public void onClick(View v) {

        Long tsLong = System.currentTimeMillis()/1000;
        fileName = directory+"case_"+tsLong.toString()+".jpg";
        File newFile = new File(fileName);
        try{
            newFile.createNewFile();
        }catch (IOException e){
            Snackbar.make(getView(),getString(R.string.failed_take_photo), Snackbar.LENGTH_SHORT).show();
            return;
        }

        Uri outputFileUri = Uri.fromFile(newFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK){
            loadPhoto();
        }
    }

    private void loadPhoto()
    {
        File imgFile = new File(fileName);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgProfile.setImageBitmap(bitmap);
            pathPhoto = fileName;
        }
    }
}
