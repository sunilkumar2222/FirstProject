package com.example.mind.equalify;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btn_signup,btn_signin;
    private EditText ed_email;
    private EditText ed_password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_signup = findViewById(R.id.button);
        btn_signin=findViewById(R.id.button2);
        ed_email = findViewById(R.id.editText);
        ed_password = findViewById(R.id.editText2);
        progressBar=findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = ed_email.getText().toString().trim();
                String passwordString = ed_password.getText().toString().trim();
                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(getApplicationContext(), "ENTER THE EMAIL ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordString)) {
                    Toast.makeText(getApplicationContext(), "ENTER THE PASSWORD", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordString.length() < 6) {
                    Toast.makeText(getApplicationContext(), "password is too short,Enter minimum 6 character", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "task is completed" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "login failed" + task.getException(), Toast.LENGTH_SHORT).show();

                                } else {
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });

    }
}
