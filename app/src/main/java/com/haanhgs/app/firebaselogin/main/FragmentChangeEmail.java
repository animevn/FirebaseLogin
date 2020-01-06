package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    @BindView(R.id.bnChangeEmail)
    Button bnChangeEmail;

    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

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
        return view;
    }

    //signout if change email succesfull
    private void reAuthenticateUser(Task<Void> task, String mail){
        if (task.isSuccessful()){
            user.updateEmail(mail).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()){
                    firebaseAuth.signOut();
                }
            });
        }
    }

    private void changeMail(){
        String password = etPassword.getText().toString().trim();
        String newEmail = etNewEmail.getText().toString().trim();
        if (user != null && user.getEmail() != null && !TextUtils.isEmpty(etNewEmail.getText())){
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(task ->
                    reAuthenticateUser(task, newEmail));
        }else if (TextUtils.isEmpty(newEmail)){
            etNewEmail.setError(getString(R.string.mail_empty));
        }
    }

    @OnClick(R.id.bnChangeEmail)
    public void onViewClicked() {
        changeMail();
    }
}
