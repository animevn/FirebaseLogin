package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangeEmail extends Fragment {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewEmail)
    EditText etNewEmail;
    @BindView(R.id.pbrLogin)
    ProgressBar pbrLogin;
    @BindView(R.id.bnChangeEmail)
    Button bnChangeEmail;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        ButterKnife.bind(this, view);
        initFirebase();
        if (user != null && user.getEmail() != null) etEmail.setText(user.getEmail());
        return view;
    }

    private void changeEmail(Task<Void> reAuthenticTask, String email){
        if (reAuthenticTask.isSuccessful()){
            user.updateEmail(email).addOnCompleteListener(task->{
                if (task.isSuccessful()) firebaseAuth.signOut();
            });
        }
    }

    private void reAuthenticateAndChangeEmail(){
        String newEmail = etNewEmail.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (user != null && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(newEmail)){
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            user.reauthenticate(credential).addOnCompleteListener(task->
                    changeEmail(task, newEmail));
        }else if (!TextUtils.isEmpty(email)){
            etEmail.setError(getString(R.string.email_empty));
        }else if (!TextUtils.isEmpty(newEmail)){
            etNewEmail.setError(getString(R.string.email_empty));
        }
    }

    @OnClick(R.id.bnChangeEmail)
    public void onViewClicked() {
        reAuthenticateAndChangeEmail();
    }
}
