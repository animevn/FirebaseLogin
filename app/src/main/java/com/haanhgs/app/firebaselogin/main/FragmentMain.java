package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.haanhgs.app.firebaselogin.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.bnChangeEmail, R.id.bnChangePassword,
            R.id.bnResetPassword, R.id.bnDeleteUser, R.id.bnSignOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnChangeEmail:
                break;
            case R.id.bnChangePassword:
                break;
            case R.id.bnResetPassword:
                break;
            case R.id.bnDeleteUser:
                break;
            case R.id.bnSignOut:
                break;
        }
    }
}
