package com.ogaga.flash.acitivies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etPhonenumber)EditText etPhonenumber;
    @Bind(R.id.etPassword)EditText etPassword;
    @Bind(R.id.btnLogin)Button btnLogin;
    @Bind(R.id.tvRegistry)TextView tvRegistry;
    private Firebase mFirebaseRef;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        mFirebaseRef= FirebaseClient.getRoot();
    }

    @OnClick(R.id.btnLogin)
    protected void LoginFirebase(){

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppThemeProgress);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        final User user = new User();
                        user.setPhonenumber(etPhonenumber.getText().toString());
                        mFirebaseRef.authWithPassword(user.getEmail(), etPassword.getText().toString(),
                                new Firebase.AuthResultHandler() {
                                    @Override
                                    public void onAuthenticated(AuthData authData) {
                                        // Authentication just completed successfully :)
                                        AuthorHelper.writeString(getApplicationContext(), "uid", authData.getUid().toString());
                                        FirebaseClient.getUserLogin(getApplicationContext(), new FirebaseClient.LoginUser() {
                                            @Override
                                            public void onLoginSuccess(User user) {
                                                Intent intent = new Intent();
                                                intent.putExtra("user", Parcels.wrap(user));
                                                setResult(Constant.LOGIN_SUCCESS_CODE, intent);
                                                Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_success), Toast.LENGTH_LONG);
                                                finish();//finishing activity
                                            }
                                        });
                                    }

                                    @Override
                                    public void onAuthenticationError(FirebaseError error) {
                                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_fails), Toast.LENGTH_LONG);
                                    }
                                });
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    @OnClick(R.id.tvRegistry)
    public void onRegistry(){
        Intent intent=new Intent(this,UserRegistryActivity.class);
        startActivityForResult(intent, Constant.REGISTRY_SUCCESS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REGISTRY_SUCCESS_CODE) {
                if (data != null) {
                    mUser=Parcels.unwrap(data.getParcelableExtra("user"));
                    Intent intent=new Intent();
                    intent.putExtra("user", Parcels.wrap(mUser));
                    setResult(Constant.LOGIN_SUCCESS_CODE, intent);
                    finish();//finishing activity
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
