package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDeleteUser extends Fragment {

    @BindView(R.id.etEmail)
    TextView etEmail;
    @BindView(R.id.bnDelete)
    Button bnDelete;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private void initFirebaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_user, container, false);
        ButterKnife.bind(this, view);
        initFirebaseAuth();
        if (user != null && user.getEmail() != null) etEmail.setText(user.getEmail());
        return view;
    }

    private void deleteUser(){
        if (user != null){
            user.delete().addOnCompleteListener(task->{
                if (task.isSuccessful()) firebaseAuth.signOut();
            });
        }
    }

    @OnClick(R.id.bnDelete)
    public void onViewClicked() {
        deleteUser();
    }
}
