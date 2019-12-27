package com.haanhgs.app.firebaselogin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.cardview_reset_password)
    CardView cardviewResetPassword;

    private FirebaseAuth firebaseAuth;

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void resetPassword(){
        progressBar.setVisibility(View.VISIBLE);
        String currentEmail = email.getText().toString().trim();

        if (!currentEmail.equals("")) {
            //noinspection Convert2Lambda
            firebaseAuth.sendPasswordResetEmail(currentEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("D.FragmentResetPass", "reset pass ok");
                                firebaseAuth.signOut();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Log.d("D.FragmentResetPass", "reset pass ok");
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        } else {
            email.setError("Enter email");
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        resetPassword();
    }
}
