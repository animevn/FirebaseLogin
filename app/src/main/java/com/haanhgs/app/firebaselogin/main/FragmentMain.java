package com.haanhgs.app.firebaselogin.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haanhgs.app.firebaselogin.Base;
import com.haanhgs.app.firebaselogin.LoginActivity;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
    @BindView(R.id.cardview_main)
    CardView cardviewMain;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private Activity activity;
    private FragmentManager manager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
        manager = getFragmentManager();
    }

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        authStateListener = firebaseAuth -> {
            if (user == null){
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra(Base.LOGOUT, Base.LOGOUT);
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
        if (user != null){
            tvEmail.setText(user.getEmail());
        }

        //if signin with google then can only delete user, can not change email, password
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (account != null){
            bnChangeEmail.setEnabled(false);
            bnChangePassword.setEnabled(false);
            bnResetPassword.setEnabled(false);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (authStateListener != null) firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void changeEmail(){
        FragmentTransaction ft = manager.beginTransaction();
        FragmentChangeEmail fragment = (FragmentChangeEmail) manager
                .findFragmentByTag(Base.FRAGMENT_CHANGE_EMAIL);
        if (fragment == null){
            FragmentChangeEmail fragmentChangeEmail = new FragmentChangeEmail();
            ft.add(R.id.flMain, fragmentChangeEmail, Base.FRAGMENT_CHANGE_EMAIL);
            ft.addToBackStack(Base.FRAGMENT_CHANGE_EMAIL);
            ft.hide(this);
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    private void changePassword(){
        FragmentTransaction ft = manager.beginTransaction();
        FragmentChangePassword fragment = (FragmentChangePassword) manager
                .findFragmentByTag(Base.FRAGMENT_CHANGE_PASSWORD);
        if (fragment == null){
            FragmentChangePassword fragmentChangePassword = new FragmentChangePassword();
            ft.add(R.id.flMain, fragmentChangePassword, Base.FRAGMENT_CHANGE_PASSWORD);
            ft.addToBackStack(Base.FRAGMENT_CHANGE_PASSWORD);
            ft.hide(this);
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    private void resetPassword(){
        FragmentTransaction ft = manager.beginTransaction();
        FragmentResetPassword fragment = (FragmentResetPassword) manager
                .findFragmentByTag(Base.FRAGMENT_RESET_PASSWORD);
        if (fragment == null){
            FragmentResetPassword fragmentResetPassword = new FragmentResetPassword();
            ft.add(R.id.flMain, fragmentResetPassword, Base.FRAGMENT_RESET_PASSWORD);
            ft.addToBackStack(Base.FRAGMENT_RESET_PASSWORD);
            ft.hide(this);
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    private void deleteUser(){
        FragmentTransaction ft = manager.beginTransaction();
        FragmentDeleteUser fragment = (FragmentDeleteUser) manager
                .findFragmentByTag(Base.FRAGMENT_DELETE_USER);
        if (fragment == null){
            FragmentDeleteUser fragmentDeleteUser = new FragmentDeleteUser();
            ft.add(R.id.flMain, fragmentDeleteUser, Base.FRAGMENT_DELETE_USER);
            ft.addToBackStack(Base.FRAGMENT_DELETE_USER);
            ft.hide(this);
            ft.commit();
        }else {
            ft.attach(fragment);
        }
    }

    @OnClick({R.id.bnChangeEmail, R.id.bnChangePassword,
            R.id.bnResetPassword, R.id.bnDeleteUser, R.id.bnSignOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnChangeEmail:
                changeEmail();
                break;
            case R.id.bnChangePassword:
                changePassword();
                break;
            case R.id.bnResetPassword:
                resetPassword();
                break;
            case R.id.bnDeleteUser:
                deleteUser();
                break;
            case R.id.bnSignOut:
                firebaseAuth.signOut();
                break;
        }
    }
}
