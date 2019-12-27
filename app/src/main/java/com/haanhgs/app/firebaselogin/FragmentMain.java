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
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMain extends BaseFragment{

    public static final String LOGOUT = "logout";
    @BindView(R.id.textview_user_email)
    TextView textviewUserEmail;
    @BindView(R.id.cardview_user)
    CardView cardviewUser;
    @BindView(R.id.change_email_button)
    Button changeEmailButton;
    @BindView(R.id.change_password_button)
    Button changePasswordButton;
    @BindView(R.id.sending_pass_reset_button)
    Button sendingPassResetButton;
    @BindView(R.id.remove_user_button)
    Button removeUserButton;
    @BindView(R.id.sign_out)
    Button signOut;
    @BindView(R.id.cardview_main)
    CardView cardviewMain;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    private FirebaseUser user;
    private GoogleSignInAccount account;
    private Activity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fireAuthListener != null){
            firebaseAuth.addAuthStateListener(fireAuthListener);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (fireAuthListener != null) {
            firebaseAuth.removeAuthStateListener(fireAuthListener);
        }
    }

    private void initGoogleAccount(){
        account = GoogleSignIn.getLastSignedInAccount(activity);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //noinspection Convert2Lambda
        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null && account == null) {
                    //user not login
                    Intent intent = new Intent(activity, LoginActivity.class);
                    intent.putExtra(LOGOUT, LOGOUT);
                    activity.startActivity(intent);
                    activity.finish();
                }
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
            textviewUserEmail.setText(user.getEmail());
        }
        return view;
    }

    private void openChangeEmail() {
        Log.d("Debug.FragmentMain", "on mail change");
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("change_email");
            if (fragment == null) {
                FragmentChangeEmail emailFragment = new FragmentChangeEmail();
                emailFragment.setUser(user);
                emailFragment.setFirebaseAuth(firebaseAuth);
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
                FragmentChangePass passFragment = new FragmentChangePass();
                passFragment.setUser(user);
                passFragment.setFirebaseAuth(firebaseAuth);
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
                FragmentResetPass passFragment = new FragmentResetPass();
                passFragment.setFirebaseAuth(firebaseAuth);
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
                passFragment.setUser(user);
                passFragment.setFirebaseAuth(firebaseAuth);
                ft.add(R.id.flMain, passFragment, "delete_user");
                ft.addToBackStack("delete_user");
                ft.hide(this);
                ft.commit();
            } else {
                ft.attach(fragment);
            }
        }
    }

    @OnClick({R.id.change_email_button, R.id.change_password_button,
            R.id.sending_pass_reset_button, R.id.remove_user_button, R.id.sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_email_button:
                openChangeEmail();
                break;
            case R.id.change_password_button:
                openChangePassword();
                break;
            case R.id.sending_pass_reset_button:
                openResetPassword();
                break;
            case R.id.remove_user_button:
                openDeleteUser();
                break;
            case R.id.sign_out:
                firebaseAuth.signOut();
                break;
        }
    }
}
