package com.haanhgs.app.loginfirebase.main;

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
import com.haanhgs.app.loginfirebase.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangePassword extends Fragment {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.bnChange)
    Button bnChange;

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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        initFirebase();
        return view;

    }

    private void changePassword(Task<Void> reAuthenticTask, String password){
        if (reAuthenticTask.isSuccessful()){
            user.updatePassword(password).addOnCompleteListener(task->{
                if (task.isSuccessful()) firebaseAuth.signOut();
            });
        }
    }

    private void reAuthenticateAndChangePassword(){
        String newPassword = etNewPassword.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (user != null && user.getEmail() != null && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(newPassword)){
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(task->
                    changePassword(task, newPassword));
        }else if (!TextUtils.isEmpty(password)){
            etPassword.setError(getString(R.string.password_empty));
        }else if (!TextUtils.isEmpty(newPassword)){
            etNewPassword.setError(getString(R.string.email_empty));
        }else if (newPassword.length() < 6){
            etNewPassword.setError(getString(R.string.minimum_password));
        }
    }

    @OnClick(R.id.bnChange)
    public void onViewClicked() {
        reAuthenticateAndChangePassword();
    }
}
