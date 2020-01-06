package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.bnReset)
    Button bnReset;
    @BindView(R.id.bnBack)
    Button bnBack;

    private static final String ETAG = "E.ResetActivity";
    private FirebaseAuth firebaseAuth;

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
        initFirebase();
    }

    private void sendRequestPasswordReset(String mail){
        firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                firebaseAuth.signOut();
            }else {
                Log.e(ETAG, "request reset password failed");
            }
        });
    }

    private void resetPassword(){
        String mail = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(mail)){
            etEmail.setError(getString(R.string.send_password_reset_email));
        }
        sendRequestPasswordReset(mail);
    }

    @OnClick({R.id.bnReset, R.id.bnBack, R.id.pbrLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnReset:
                resetPassword();
                break;
            case R.id.bnBack:
                finish();
                break;
        }
    }
}
