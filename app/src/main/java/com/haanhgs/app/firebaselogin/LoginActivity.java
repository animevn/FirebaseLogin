package com.haanhgs.app.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.reset_button)
    Button resetButton;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private void logUserToFirebase(String mail, String pass){
        firebaseAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    if (pass.length() < 6) {
                        password.setError(getString(R.string.minimum_password));
                    } else {
                        Log.d("D.LoginActivity", "log in failed");
                    }
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
            }
        });
    }

    private void login(){
        String emailString = email.getText().toString().trim();
        final String passwordString = this.password.getText().toString().trim();
        if (TextUtils.isEmpty(emailString)) {
            Log.d("D.LoginActivity", "email can not be empty");
            email.setError("Empty");
            return;
        }
        if (TextUtils.isEmpty(passwordString)) {
            Log.d("D.LoginActivity", "password can not be empty");
            password.setError("Empty");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        logUserToFirebase(emailString, passwordString);
    }


    @OnClick({R.id.login_button, R.id.reset_button, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.reset_button:
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(this, SignupActivity.class));
                finish();
                break;
        }
    }

    private void checkUserSignInAlready(){
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserSignInAlready();
        setContentView(R.layout.activity_login1);
        ButterKnife.bind(this);
    }

}
