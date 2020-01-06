package com.haanhgs.app.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.haanhgs.app.firebaselogin.main.MainActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.cvEmail)
    CardView cvEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.bnSignUp)
    Button bnSignUp;
    @BindView(R.id.bnReset)
    Button bnReset;
    @BindView(R.id.bnLogin)
    Button bnLogin;

    private static final String ETAG = "E.SignUpActivity";
    private FirebaseAuth firebaseAuth;

    private void initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initFirebaseAuth();
    }

    private void openMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void openResetActivity(){
        startActivity(new Intent(this, ResetActivity.class));
        finish();
    }


    private void registerFirebaseWithEmailAndPassword(String mail, String password){
        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                openMainActivity();
            }else {
                Log.e(ETAG, "create user with email and password failed");
            }
        });
    }

    private void registerUser(){
        String mail = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) etEmail.setError(getString(R.string.mail_empty));
        if (TextUtils.isEmpty(password)) etPassword.setError(getString(R.string.minimum_password));
        if (password.length() < 6) etPassword.setError(getString(R.string.minimum_password));
        registerFirebaseWithEmailAndPassword(mail, password);
    }

    @OnClick({R.id.bnSignUp, R.id.bnReset, R.id.bnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnSignUp:
                registerUser();
                break;
            case R.id.bnReset:
                openResetActivity();
                break;
            case R.id.bnLogin:
                finish();
                break;
        }
    }
}
