package com.haanhgs.app.loginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.haanhgs.app.loginfirebase.main.MainActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.bnSignUp)
    Button bnSignUp;
    @BindView(R.id.bnReset)
    Button bnReset;
    @BindView(R.id.bnLogin)
    Button bnLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void registerWithFirebase(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task->{
            if (task.isSuccessful()){
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            } else {
                Log.e(Base.SIGNUP_TAG, "signup failed");
            }
        });
    }

    private void signup(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            etEmail.setError(getString(R.string.email_empty));
            return;
        }

        if (TextUtils.isEmpty(password)){
            etPassword.setError(getString(R.string.password_empty));
            return;
        }

        if (password.length() < 6){
            etPassword.setError(getString(R.string.minimum_password));
            return;
        }
        registerWithFirebase(email, password);
    }

    @OnClick({R.id.bnSignUp, R.id.bnReset, R.id.bnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnSignUp:
                signup();
                break;
            case R.id.bnReset:
                startActivity(new Intent(this, ResetActivity.class));
                finish();
                break;
            case R.id.bnLogin:
                finish();
                break;
        }
    }
}
