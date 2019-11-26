package com.haanhgs.app.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    @BindView(R.id.login_google_button)
    Button loginGoogleButton;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1979;

    private void logUserToFirebase(String mail, String pass) {
        //noinspection Convert2Lambda
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
                    finish();
                }
            }
        });
    }

    private void login() {
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


    private void initGoogleSignin(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
    }

    private void signInWithGoogle(){
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }


    @OnClick({R.id.login_button, R.id.reset_button, R.id.btn_signup, R.id.login_google_button})
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
                break;
            case R.id.login_google_button:
                signInWithGoogle();
                break;
        }
    }

    private void checkUserSignInAlready() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void receivedIntent(){
        Intent intent = getIntent();
        String string = intent.getStringExtra(FragmentMain.LOGOUT);
        if (string != null && string.equals(FragmentMain.LOGOUT)){
            Log.d("D.LoginActivity", string);
            googleSignInClient.signOut();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserSignInAlready();
        initGoogleSignin();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        receivedIntent();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("D.LoginActivity", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //noinspection Convert2Lambda
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("D.LoginActivity", "signInWithCredential:success");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("error", "Google sign in failed", e);
            }
        }
    }
}
