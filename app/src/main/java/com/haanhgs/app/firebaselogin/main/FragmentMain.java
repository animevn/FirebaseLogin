package com.haanhgs.app.firebaselogin.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haanhgs.app.firebaselogin.LoginActivity;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMain extends Fragment {

    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.bnChangeEmail)
    Button bnChangeEmail;
    @BindView(R.id.bnChangePassword)
    Button bnChangePassword;
    @BindView(R.id.bnResetPassword)
    Button bnResetPassword;
    @BindView(R.id.bnDeleteUser)
    Button bnDeleteUser;
    @BindView(R.id.bnSignOut)
    Button bnSignOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        listener = firebaseAuth -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser == null){
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra("logout", "logout");
                startActivity(intent);
                activity.finish();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initFirebase();
        if (user != null && user.getEmail() != null) {
            tvEmail.setText(user.getEmail());
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listener != null){
            firebaseAuth.addAuthStateListener(listener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null){
            firebaseAuth.removeAuthStateListener(listener);
        }
    }

    private void openChangeEmail() {
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("change_email");
            if (fragment == null) {
                FragmentChangeEmail emailFragment = new FragmentChangeEmail();
                ft.add(R.id.flMain, emailFragment, "change_email");
                ft.addToBackStack("change_email");
                ft.hide(this);
                ft.commit();
            } else {
                ft.attach(fragment);
            }
        }
    }

    private void openChangePassword() {
        Log.d("Debug.FragmentMain", "on pass change");
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("change_pass");
            if (fragment == null) {
                FragmentChangePassword passFragment = new FragmentChangePassword();
                ft.add(R.id.flMain, passFragment, "change_pass");
                ft.addToBackStack("change_pass");
                ft.hide(this);
                ft.commit();
            } else {
                ft.attach(fragment);
            }
        }
    }

    private void openResetPassword() {
        Log.d("Debug.FragmentMain", "on pass reset");
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("reset_pass");
            if (fragment == null) {
                FragmentResetPassword passFragment = new FragmentResetPassword();
                ft.add(R.id.flMain, passFragment, "reset_pass");
                ft.addToBackStack("reset_pass");
                ft.hide(this);
                ft.commit();
            } else {
                ft.attach(fragment);
            }
        }
    }

    private void openDeleteUser() {
        Log.d("Debug.FragmentMain", "on pass reset");
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("delete_user");
            if (fragment == null) {
                FragmentDeleteUser passFragment = new FragmentDeleteUser();
                ft.add(R.id.flMain, passFragment, "delete_user");
                ft.addToBackStack("delete_user");
                ft.hide(this);
                ft.commit();
            } else {
                ft.attach(fragment);
            }
        }
    }


    @OnClick({R.id.bnChangeEmail, R.id.bnChangePassword,
            R.id.bnResetPassword, R.id.bnDeleteUser, R.id.bnSignOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnChangeEmail:
                openChangeEmail();
                break;
            case R.id.bnChangePassword:
                openChangePassword();
                break;
            case R.id.bnResetPassword:
                openResetPassword();
                break;
            case R.id.bnDeleteUser:
                openResetPassword();
                break;
            case R.id.bnSignOut:
                firebaseAuth.signOut();
                break;
        }
    }
}
