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

//    private void hideViews(){
//        oldEmail.setVisibility(View.GONE);
//        newEmail.setVisibility(View.GONE);
//        password.setVisibility(View.GONE);
//        newPassword.setVisibility(View.GONE);
//        changeEmail.setVisibility(View.GONE);
//        changePass.setVisibility(View.GONE);
//        send.setVisibility(View.GONE);
//        remove.setVisibility(View.GONE);
//
//        if (progressBar != null) {
//            progressBar.setVisibility(View.GONE);
//        }
//    }
//
//    private void changeEmailButton(){
//        changeEmailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldEmail.setVisibility(View.GONE);
//                newEmail.setVisibility(View.VISIBLE);
//                password.setVisibility(View.GONE);
//                newPassword.setVisibility(View.GONE);
//                changeEmail.setVisibility(View.VISIBLE);
//                changePass.setVisibility(View.GONE);
//                send.setVisibility(View.GONE);
//                remove.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    private void changePasswordButton(){
//
//        //change button visible for password changing
//        changePasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldEmail.setVisibility(View.GONE);
//                newEmail.setVisibility(View.GONE);
//                password.setVisibility(View.GONE);
//                newPassword.setVisibility(View.VISIBLE);
//                changeEmail.setVisibility(View.GONE);
//                changePass.setVisibility(View.VISIBLE);
//                send.setVisibility(View.GONE);
//                remove.setVisibility(View.GONE);
//            }
//        });
//
//        changePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                String newPasswordText = newPassword.getText().toString().trim();
//
//                if (user != null && !newPasswordText.equals("")) {
//                    user.updatePassword(newPasswordText)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this,
//                                                "Password is updated, sign in with new password!",
//                                                Toast.LENGTH_SHORT).show();
//                                        firebaseAuth.signOut();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this,
//                                                "Failed to update password!",
//                                                Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                } else if (newPasswordText.equals("")) {
//                    newPassword.setError("Enter password");
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//    }
//
//    private void resetEmailButton(){
//        //reset email button
//        sendingPassResetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                oldEmail.setVisibility(View.VISIBLE);
//                newEmail.setVisibility(View.GONE);
//                password.setVisibility(View.GONE);
//                newPassword.setVisibility(View.GONE);
//                changeEmail.setVisibility(View.GONE);
//                changePass.setVisibility(View.GONE);
//                send.setVisibility(View.VISIBLE);
//                remove.setVisibility(View.GONE);
//            }
//        });
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                String oldEmailText = oldEmail.getText().toString().trim();
//
//                if (!oldEmailText.equals("")) {
//                    firebaseAuth.sendPasswordResetEmail(oldEmailText)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                } else {
//                    oldEmail.setError("Enter email");
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//    }
//
//    private void deleteUser(){
//        //deleting some user
//        removeUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                if (user != null) {
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this,
//                                                "Your profile is deleted:( Create a account now!",
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
//                                        MainActivity.this.startActivity(intent);
//                                        MainActivity.this.finish();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this,
//                                                "Failed to delete your account!",
//                                                Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                }
//            }
//        });
//    }
//
//    private void signout(){
//        //simple signing out
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Log.d("Debug.MainActivity", "logout");
//            }
//        });
//    }
}
