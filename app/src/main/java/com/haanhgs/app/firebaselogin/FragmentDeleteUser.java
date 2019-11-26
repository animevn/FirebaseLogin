package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    TextView email;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.remove)
    Button remove;
    @BindView(R.id.cardview_remove_user)
    CardView cardviewRemoveUser;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_user, container, false);
        ButterKnife.bind(this, view);
        if (user.getEmail() != null) email.setText(user.getEmail());
        return view;
    }

    private void deleteUser() {
        progressBar.setVisibility(View.VISIBLE);
        if (user != null) {
            //noinspection Convert2Lambda
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("D.FragmentDeleteUser", "delete user ok");
                        firebaseAuth.signOut();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Log.d("D.FragmentDeleteUser", "delete user fail");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @OnClick(R.id.remove)
    public void onViewClicked() {
        deleteUser();
    }
}
