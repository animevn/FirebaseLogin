package com.haanhgs.app.firebaselogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangeEmail extends BaseFragment {

    @BindView(R.id.old_email)
    EditText etOldEmail;
    @BindView(R.id.new_email)
    EditText etNewEmail;
    @BindView(R.id.changeEmail)
    Button bnChangeEmail;
    @BindView(R.id.cardview_email)
    CardView cvEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);
        ButterKnife.bind(view);
        return view;
    }


}
