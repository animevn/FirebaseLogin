package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentResetPass extends BaseFragment {

    @BindView(R.id.etEmail)
    EditText email;
    @BindView(R.id.pbrLogin)
    ProgressBar progressBar;
    @BindView(R.id.bnReset)
    Button send;
    @BindView(R.id.cardview_reset_password)
    CardView cardviewResetPassword;

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) email.setText(user.getEmail());
        return view;
    }

    private void resetPassword(){
        progressBar.setVisibility(View.VISIBLE);
        String currentEmail = email.getText().toString().trim();
        if (!TextUtils.isEmpty(currentEmail)) {
            firebaseAuth.sendPasswordResetEmail(currentEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("D.FragmentResetPass", "reset pass ok");
                            firebaseAuth.signOut();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("D.FragmentResetPass", "reset pass ok");
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            email.setError("Enter email");
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.bnReset)
    public void onViewClicked() {
        resetPassword();
    }
}
