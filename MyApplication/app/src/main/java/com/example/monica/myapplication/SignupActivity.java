package com.example.monica.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText user,pass;
    private FirebaseAuth firebaseAuth;
    private String numeUser,parolaUser;
    private LoginRemember loginRemember = LoginRemember.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        user.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numeUser = user.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parolaUser = pass.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void clicked6(View view) {
        if(view.getId()==R.id.signIN)
        {
            if(user.getText().length()>0 && pass.getText().length()>0) {
                firebaseAuth.signInWithEmailAndPassword(numeUser,parolaUser)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.putExtra("userr", numeUser);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(SignupActivity.this, "Login not successful!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
