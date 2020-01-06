package com.haanhgs.app.firebaselogin.main;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentResetPassword extends Fragment {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.bnReset)
    Button bnReset;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        initFirebase();
        if (user != null && user.getEmail() != null) etEmail.setText(user.getEmail());
        return view;
    }

    private void sendResetPasswordRequest(String mail){
        firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(context, getString(R.string.reset_ok), Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }else {
                Toast.makeText(context, "reset password failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(){
        String mail = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(mail)){
            etEmail.setError(getString(R.string.mail_empty));
        }
        sendResetPasswordRequest(mail);
    }

    @OnClick(R.id.bnReset)
    public void onViewClicked() {
        resetPassword();
    }
}
