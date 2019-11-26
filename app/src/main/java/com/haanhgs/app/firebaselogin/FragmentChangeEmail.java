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

    @BindView(R.id.old_email)
    EditText oldEmail;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.new_email)
    EditText newEmail;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.changeEmail)
    Button changeEmail;
    @BindView(R.id.cardview_email)
    CardView cardviewEmail;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

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
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        ButterKnife.bind(this, view);
        oldEmail.setText(user.getEmail());
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
            user.updateEmail(string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    changeEmail(task);
                }
            });
        }else {
            Log.d("D.FragmentChangeEmail", "authenticate failed");
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.changeEmail)
    public void onViewClicked() {
        progressBar.setVisibility(View.VISIBLE);

        //chaning email
        String newEmail = this.newEmail.getText().toString().trim();
        String password = this.password.getText().toString();
        if (user != null && user.getEmail() != null && !newEmail.equals("") && !password.equals("")) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    reEnableCredential(task, newEmail);
                }
            });
        } else if (newEmail.equals("")) {
            this.newEmail.setError("Enter email");
            progressBar.setVisibility(View.GONE);
        }
    }
}
