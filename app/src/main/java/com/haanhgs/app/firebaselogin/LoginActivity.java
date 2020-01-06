package com.haanhgs.app.firebaselogin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.haanhgs.app.firebaselogin.main.MainActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.bnLoginGoogle)
    Button bnLoginGoogle;
    @BindView(R.id.bnLogin)
    Button bnLogin;
    @BindView(R.id.bnReset)
    Button bnReset;
    @BindView(R.id.bnSignup)
    Button bnSignup;

    private static final String ETAG = "E.LoginActivity";
    private static final int SIGN_IN_REQUEST = 1979;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient client;

    private void openMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //check if user is signed in, if yes then open MainActivity
    private void checkUserSignIn(){
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            openMainActivity();
        }
    }

    private void initGoogleSignInClient(){
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        checkUserSignIn();
        initGoogleSignInClient();
    }

    //SignIn with google
    //////////////////////////////////////
    private void signInWithGoogle(){
        Intent intent = client.getSignInIntent();
        startActivityForResult(intent, SIGN_IN_REQUEST);
    }

    private void getFirebaseAuthWithGoogleAccount(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                openMainActivity();
            }else {
                Log.e(ETAG, "login failed at firebase login with credential");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST && resultCode == RESULT_OK && data != null){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) getFirebaseAuthWithGoogleAccount(account);
            }catch (ApiException e){
                Log.e(ETAG, e.toString());
            }
        }
    }
    ////////////////////////////////////////

    //Signin with email and password
    ////////////////////////////////////////////
    private void loginWithFirebase(String mail, String password){
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                if (password.length() < 6){
                    etPassword.setError(getString(R.string.minimum_password));
                }else {
                    Log.e(ETAG, "login failed at firebase login with email and password");
                }
            }else {
                openMainActivity();
            }
        });
    }

    private void login() {
        String emailString = etEmail.getText().toString().trim();
        String passwordString = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(emailString)) {
            etEmail.setError("Empty");
            return;
        }
        if (TextUtils.isEmpty(passwordString)) {
            etPassword.setError("Empty");
            return;
        }
        loginWithFirebase(emailString, passwordString);
    }
    ////////////////////////////////////////////////////////////////

    private void openResetActivity(){
        startActivity(new Intent(this, ResetActivity.class));
    }

    private void openSignUpActivity(){
        startActivity(new Intent(this, SignUpActivity.class));
    }


    @OnClick({R.id.bnLoginGoogle, R.id.bnLogin, R.id.bnReset, R.id.bnSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnLoginGoogle:
                signInWithGoogle();
                break;
            case R.id.bnLogin:
                login();
                break;
            case R.id.bnReset:
                openResetActivity();
                break;
            case R.id.bnSignup:
                openSignUpActivity();
                break;
        }
    }
}
