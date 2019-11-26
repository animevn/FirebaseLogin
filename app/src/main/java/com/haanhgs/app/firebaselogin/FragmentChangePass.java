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

public class FragmentChangePass extends BaseFragment {
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.changePass)
    Button changePass;
    @BindView(R.id.cardview_password)
    CardView cardviewPassword;

    private Context context;
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
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void changePass(Task<Void> task){
        if (task.isSuccessful()) {
            Log.d("D.FragmentChangePass", "change pass ok");
            firebaseAuth.signOut();
            progressBar.setVisibility(View.GONE);
        } else {
            Log.d("D.FragmentChangePass", "change pass failed");
            progressBar.setVisibility(View.GONE);
        }

    }

    private void reEnableCredential(Task<Void> task, String string){
        if (task.isSuccessful()){
            Log.d("D.FragmentChangePass", "authenticate ok");
            user.updateEmail(string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.updatePassword(string)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            changePass(task);
                        }
                    });
                }
            });
        }else {
            Log.d("D.FragmentChangePass", "authenticate failed");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void changePassword(){
        progressBar.setVisibility(View.VISIBLE);
        String oldPass = password.getText().toString().trim();
        String newPass = newPassword.getText().toString().trim();
        if (user != null && !newPass.equals("") && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    reEnableCredential(task, newPass);
                }
            });
        } else if (newPass.equals("")) {
            newPassword.setError("Enter password");
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.changePass)
    public void onViewClicked() {
        changePassword();
    }
}
