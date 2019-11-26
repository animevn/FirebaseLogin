package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private void openFragmentMain(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMain fragment = (FragmentMain)getSupportFragmentManager().findFragmentByTag("main");
        if (fragment == null){
            FragmentMain fragmentMain = new FragmentMain();
            ft.replace(R.id.framelayout_main, fragmentMain, "main");
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        openFragmentMain();
//        ButterKnife.bind(this);
//        firebaseAuth = FirebaseAuth.getInstance();
//        user = firebaseAuth.getCurrentUser();
//        fireAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user1 = firebaseAuth.getCurrentUser();
//
//                if (user1 == null) {
//                    //user not login
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    MainActivity.this.startActivity(intent);
//                    MainActivity.this.finish();
//                }
//            }
//        };



    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(fireAuthListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if(fireAuthListener != null){
//            firebaseAuth.removeAuthStateListener(fireAuthListener);
//        }
//    }



//
//    private void deleteUser(){
//        //deleting some user
//        removeUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });
//    }
//

}
