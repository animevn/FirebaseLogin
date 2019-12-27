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

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.etEmail)
    EditText email;
    @BindView(R.id.etPassword)
    EditText password;
    @BindView(R.id.bnSignUp)
    Button signUpButton;
    @BindView(R.id.bnLogin)
    Button signInButton;
    @BindView(R.id.pbrSignUp)
    ProgressBar progressBar;
    @BindView(R.id.bnReset)
    Button resetButton;

    private FirebaseAuth firebaseAuth;

    private void registerWithFirebase(String userEmail, String userPassword){
        //noinspection Convert2Lambda
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("D.ResetActivity", "mail empty");
                        if (!task.isSuccessful()) {
                            Log.d("D.ResetActivity", "register failed");
                        } else {
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void registerUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail)) {
            Log.d("D.ResetActivity", "mail empty");
            email.setError("Mail empty");
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Log.d("D.ResetActivity", "password empty");
            password.setError("password empty");
            return;
        }
        if (userPassword.length() < 6) {
            Log.d("D.ResetActivity", "password at least 6 letters");
            password.setError("password at least 6 letters");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        registerWithFirebase(userEmail, userPassword);
    }

    @OnClick({R.id.bnSignUp, R.id.bnReset, R.id.bnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnSignUp:
                registerUser();
                break;
            case R.id.bnReset:
                startActivity(new Intent(SignupActivity.this, ResetActivity.class));
                finish();
                break;
            case R.id.bnLogin:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
