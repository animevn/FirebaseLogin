package com.haanhgs.app.firebaselogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDeleteUser extends BaseFragment {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.cardview_reset_password)
    CardView cardviewResetPassword;

    private Context context;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private Activity activity;

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
        activity = getActivity();
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

    private void openSignup(){
        Intent intent = new Intent(context, SignupActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void deleteUser(){
        progressBar.setVisibility(View.VISIBLE);
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("D.FragmentDeleteUser", "delete user ok");
                                openSignup();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Log.d("D.FragmentDeleteUser", "delete user fail");
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        deleteUser();
    }
}
