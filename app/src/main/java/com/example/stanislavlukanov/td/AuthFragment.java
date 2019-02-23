package com.example.stanislavlukanov.td;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment{

    private EditText mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mForgotPassword;

    public static AuthFragment newInstance(){
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void showMessage(@StringRes int string){
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }
    private boolean isLoginValid(){
      //  String a = "admin";
        String a = "";
        return(a.equals(mLogin.getText().toString()));
    }
    private boolean isPasswordValid(){
     //   String b = "password"
        String b = "";
        return (b.equals(mPassword.getText().toString()));
    }
    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isLoginValid() && (isPasswordValid())){
                //todo
                Intent intent = new Intent(getActivity(), AppActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            else {
                showMessage(R.string.fr_auth_reenter_toast);
            }
        }
    };

    public View.OnClickListener mOnForgotPasswordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // send email
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"lukyanov.stas@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Необходимо восстановить пароль к учётной записи.");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Выберите email клиент :"));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_auth, container,false);

        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.fr_auth_buttonEnter);
        mForgotPassword = v.findViewById(R.id.fr_auth_buttonFP);


        mEnter.setOnClickListener(mOnEnterClickListener);
        mForgotPassword.setOnClickListener(mOnForgotPasswordClickListener);
        return v;
    }
}
