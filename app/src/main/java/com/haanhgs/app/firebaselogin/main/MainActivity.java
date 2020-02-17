package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import com.haanhgs.app.firebaselogin.Base;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private void openFragmentMain(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMain fragmentMain = (FragmentMain) getSupportFragmentManager()
                .findFragmentByTag(Base.FRAGMENT_MAIN);
        if (fragmentMain == null){
            FragmentMain main = new FragmentMain();
            ft.replace(R.id.flMain, main, Base.FRAGMENT_MAIN);
            ft.commit();
        }else {
            ft.attach(fragmentMain);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragmentMain();
    }
}
