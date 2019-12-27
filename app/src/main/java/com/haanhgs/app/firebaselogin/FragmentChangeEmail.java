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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangeEmail extends BaseFragment {

    @BindView(R.id.etEmail)
    EditText oldEmail;
    @BindView(R.id.etPassword)
    EditText password;
    @BindView(R.id.etNewEmail)
    EditText newEmail;
    @BindView(R.id.pbrLogin)
    ProgressBar progressBar;
    @BindView(R.id.bnChangeEmail)
    Button changeEmail;
    @BindView(R.id.cardview_email)
    CardView cardviewEmail;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        ButterKnife.bind(this, view);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user != null) oldEmail.setText(user.getEmail());
        return view;
    }

    private void changeEmail(Task<Void> task){
        if (task.isSuccessful()) {
            firebaseAuth.signOut();
            progressBar.setVisibility(View.GONE);
            Log.d("D.FragmentChangeEmail", "change email ok");
        } else {
            Log.d("D.FragmentChangeEmail", "change email fail");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void reEnableCredential(Task<Void> task, String string){
        if (task.isSuccessful()){
            Log.d("D.FragmentChangeEmail", "authenticate ok");
            user.updateEmail(string).addOnCompleteListener(this::changeEmail);
        }else {
            Log.d("D.FragmentChangeEmail", "authenticate failed");
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.bnChangeEmail)
    public void onViewClicked() {
        progressBar.setVisibility(View.VISIBLE);

        //chaning email
        String newEmail = this.newEmail.getText().toString().trim();
        String password = this.password.getText().toString();
        if (user != null && user.getEmail() != null && !newEmail.equals("") && !password.equals("")) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(task ->
                    reEnableCredential(task, newEmail));
        } else if (newEmail.equals("")) {
            this.newEmail.setError("Enter email");
            progressBar.setVisibility(View.GONE);
        }
    }
}
