package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import com.haanhgs.app.firebaselogin.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private void openFragmentMain(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMain fragment = (FragmentMain)getSupportFragmentManager().findFragmentByTag("main");
        if (fragment == null){
            FragmentMain fragmentMain = new FragmentMain();
            ft.replace(R.id.flMain, fragmentMain, "main");
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragmentMain();
    }
}
