package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void sendRequestResetPassword(String email){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task->{
            if (task.isSuccessful()){
                Toast.makeText(ResetActivity.this,
                        getString(R.string.reset_ok), Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            } else {
                Log.e(Base.RESET_TAG, "reset failed");
            }
        });
    }

    private void resetPassword(){
        String mail = etEmail.getText().toString();
        if (TextUtils.isEmpty(mail)){
            etEmail.setError(getString(R.string.email_empty));
        }else {
            sendRequestResetPassword(mail);
        }
    }

    @OnClick({R.id.bnReset, R.id.bnBack})
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
