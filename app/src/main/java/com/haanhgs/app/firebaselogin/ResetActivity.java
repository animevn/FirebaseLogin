package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private void requestResetPass(String userEmail){
        //noinspection Convert2Lambda
        firebaseAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("D.ResetActivity", "pass reset ok");
                } else {
                    Log.d("D.ResetActivity", "pass reset failed");
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void resetPass(){
        String userEmail = email.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail)) {
            Log.d("D.ResetActivity", "mail empty");
            email.setError("Empty");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        requestResetPass(userEmail);
    }

    @OnClick({R.id.btn_reset_password, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                resetPass();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
