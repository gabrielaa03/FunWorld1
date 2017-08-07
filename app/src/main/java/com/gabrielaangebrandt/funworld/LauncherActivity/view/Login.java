package com.gabrielaangebrandt.funworld.LauncherActivity.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.gabrielaangebrandt.funworld.ForgottenPassword.view.ForgottenPassword;
import com.gabrielaangebrandt.funworld.LauncherActivity.LauncherContract;
import com.gabrielaangebrandt.funworld.LauncherActivity.presenter.LauncherPresenterImpl;
import com.gabrielaangebrandt.funworld.MainActivity.view.MainActivity;
import com.gabrielaangebrandt.funworld.R;
import com.gabrielaangebrandt.funworld.database.DatabaseConfig;
import com.gabrielaangebrandt.funworld.models.data_model.Player;
import com.gabrielaangebrandt.funworld.register_activity.view.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class Login extends AppCompatActivity implements LauncherContract.LauncherView {
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.et_username)
    EditText username;
    LauncherContract.LauncherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        presenter = new LauncherPresenterImpl(this);
/*
        final TextInputLayout text_input_layout1 = (TextInputLayout) findViewById(R.id.text_input_layout);
        final TextInputLayout text_input_layout2 = (TextInputLayout) findViewById(R.id.text_input_layout1);
        text_input_layout1.setHint("Username");
        text_input_layout2.setHint("Password");*/

    }

    @OnClick(R.id.btn_login1)
    public void openMainActivity() {
        Realm realm = DatabaseConfig.getRealmInstance();
        Player user = realm.where(Player.class).equalTo("username", username.getText().toString()).findFirst();
        if (password.getText().toString().equals(user.getPassword())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (username.getText().toString().equals(null) || password.getText().toString().equals(null)){
            Toast.makeText(this, R.string.userDoesNotExists, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, R.string.userDoesNotExists, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btn_register)
    public void openRegisterActivity() {
        Intent intent1 = new Intent(this, RegisterActivity.class);
        startActivity(intent1);
    }

    @OnClick(R.id.tv_forgotten_pass)
    public void openForgottenPassActivity() {
        Intent intent2 = new Intent(this, ForgottenPassword.class);
        startActivity(intent2);
    }
}
