package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.old_email) EditText oldEmail;
    @BindView(R.id.new_email) EditText newEmail;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.newPassword) EditText newPassword;
    @BindView(R.id.changeEmail) Button changeEmail;
    @BindView(R.id.changePass) Button changePass;
    @BindView(R.id.send) Button send;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.remove) Button remove;
    @BindView(R.id.change_email_button) Button changeEmailButton;
    @BindView(R.id.change_password_button) Button changePasswordButton;
    @BindView(R.id.sending_pass_reset_button) Button sendingPassResetButton;
    @BindView(R.id.remove_user_button) Button removeUserButton;
    @BindView(R.id.sign_out) Button signOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

    }
}
