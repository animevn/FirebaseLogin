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

public class FragmentChangePassword extends Fragment {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.bnChange)
    Button bnChange;

    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    private void initFirebaseAuth() {
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
        initFirebaseAuth();
        return view;
    }

    private void reAuthenticate(Task<Void> task, String string) {
        if (task.isSuccessful()) {
            user.updatePassword(string).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    firebaseAuth.signOut();
                }
            });
        }
    }

    private void changePassword() {
        String oldPass = etPassword.getText().toString().trim();
        String newPass = etNewPassword.getText().toString().trim();
        if (user != null && user.getEmail() != null && !TextUtils.isEmpty(newPass)) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
            user.reauthenticate(credential).addOnCompleteListener(task ->
                    reAuthenticate(task, newPass));
        } else if (TextUtils.isEmpty(newPass)) {
            etNewPassword.setError(getString(R.string.minimum_password));
        }
    }

    @OnClick(R.id.bnChange)
    public void onViewClicked() {
        changePassword();
    }
}
