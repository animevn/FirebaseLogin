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

    @BindView(R.id.etEmail)
    EditText email;
    @BindView(R.id.bnReset)
    Button btnResetPassword;
    @BindView(R.id.bnBack)
    Button btnBack;
    @BindView(R.id.pbrLogin)
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

    @OnClick({R.id.bnReset, R.id.bnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnReset:
                resetPass();
                break;
            case R.id.bnBack:
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
