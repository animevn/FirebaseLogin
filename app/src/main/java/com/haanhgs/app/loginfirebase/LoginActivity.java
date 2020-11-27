package com.haanhgs.app.loginfirebase;

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
import com.haanhgs.app.loginfirebase.main.MainActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.cvEmail)
    CardView cvEmail;
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

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient client;


    //init firebaseAuth and check if user logged out in last session, if not then simply close
    // login activity and open MainActivity.
    private void checkUserSignIn(){
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void initGoogleSignIn(){
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
    }

    //I have not found a way to sign out Google account from where user click signout buttons,
    //indeed that action taken place in MainActivity, but right here the gg client is still there
    //so the ugly way is to sign out in MainActitivity and then close MainActivity, turn back here
    //and sign out again (ugly huh) once more and for all :D . Currently that works, will need
    //to find a better way.
    ////////////////////////////////////////////
    private void signOutGoogleAccount(){
        Intent intent = getIntent();
        String string = intent.getStringExtra(Base.LOGOUT);
        if (Base.LOGOUT.equals(string)){
            client.signOut();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //require portrait mode for easy setup now, but can easily transfer to landscape
        //layout, just create a landscape.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        checkUserSignIn();
        initGoogleSignIn();
        signOutGoogleAccount();
    }

    //this part is for google accoutn sign in
    /////////////////////////////////////////
    private void getFirebaseAuthWithGoogleSignInAccount(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task ->{

            if (task.isSuccessful()){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else {
                Log.e(Base.LOGIN_TAG, "login error or cancelled");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Base.RC_SIGN_IN && resultCode == RESULT_OK && data != null){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null)getFirebaseAuthWithGoogleSignInAccount(account);
            }catch (ApiException e){
                Log.e(Base.LOGIN_TAG, e.toString()) ;
            }
        }
    }

    //this part for sign in with email and password
    ////////////////////////////////////////////////
    private void loginWithUserEmailAndPassword(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Log.e(Base.LOGIN_TAG, "login failed");
            }else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void login(){
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

        loginWithUserEmailAndPassword(email, password);
    }

    //this part controll all button clicks
    //////////////////////////////////////
    @OnClick({R.id.bnLoginGoogle, R.id.bnLogin, R.id.bnReset, R.id.bnSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnLoginGoogle:
                startActivityForResult(client.getSignInIntent(), Base.RC_SIGN_IN);
                break;
            case R.id.bnLogin:
                login();
                break;
            case R.id.bnReset:
                startActivity(new Intent(this, ResetActivity.class));
                break;
            case R.id.bnSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}