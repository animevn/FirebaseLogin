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

public class FragmentChangePassword extends Fragment {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.bnChange)
    Button bnChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.etPassword, R.id.etNewPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etPassword:
                break;
            case R.id.etNewPassword:
                break;
        }
    }
}
