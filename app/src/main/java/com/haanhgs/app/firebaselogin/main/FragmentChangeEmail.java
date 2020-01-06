package com.haanhgs.app.firebaselogin.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.haanhgs.app.firebaselogin.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangeEmail extends Fragment {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewEmail)
    EditText etNewEmail;
    @BindView(R.id.bnChangeEmail)
    Button bnChangeEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bnChangeEmail)
    public void onViewClicked() {
    }
}
