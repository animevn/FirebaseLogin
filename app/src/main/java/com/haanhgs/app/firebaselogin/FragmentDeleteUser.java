package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDeleteUser extends BaseFragment {

    @BindView(R.id.etEmail)
    TextView email;
    @BindView(R.id.pbrLogin)
    ProgressBar progressBar;
    @BindView(R.id.bnDelete)
    Button remove;
    @BindView(R.id.cardview_remove_user)
    CardView cardviewRemoveUser;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_user, container, false);
        ButterKnife.bind(this, view);
        firebaseAuth = FirebaseAuth.getInstance();
        user =firebaseAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) email.setText(user.getEmail());
        return view;
    }

    private void deleteUser() {
        progressBar.setVisibility(View.VISIBLE);
        if (user != null) {
            user.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("D.FragmentDeleteUser", "delete user ok");
                    firebaseAuth.signOut();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.d("D.FragmentDeleteUser", "delete user fail");
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @OnClick(R.id.bnDelete)
    public void onViewClicked() {
        deleteUser();
    }
}
