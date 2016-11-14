package org.grupovision.sipmovil.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.grupovision.sipmovil.R;
import org.grupovision.sipmovil.fragments.CaseRVFragment;
import org.grupovision.sipmovil.fragments.InputCaseFragment;

/**
 * Created by ivalle on 07/11/2016.
 */

public class MainActivity extends AppCompatActivity implements CaseRVFragment.CallbackInterface{

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content,new CaseRVFragment()).commit();
    }

    @Override
    public void addCase() {
        //Se usa replace pq se está usando el mismo contenedor
        fm.beginTransaction().replace(R.id.content,new InputCaseFragment()).addToBackStack(null).commit();
    }
}
