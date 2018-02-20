package com.example.mind.equalify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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



/**
 * Created by mind on 20/2/18.
 */

public class SigninActivity  extends Activity{
     private Button btn_signin;
    private EditText ed_emillogin;
    private EditText ed_passlogin;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinactivity);
        btn_signin=findViewById(R.id.btn_signin);
        ed_emillogin=findViewById(R.id.editText3);
        ed_passlogin=findViewById(R.id.editText4);
        progressBar=findViewById(R.id.progressBar2);

        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SigninActivity.this, MainActivity.class));
            finish();
        }
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStringlogin=ed_emillogin.getText().toString().trim();
                final  String passwordStringLogin=ed_emillogin.getText().toString().trim();
                if(TextUtils.isEmpty(emailStringlogin))
                {
                    Toast.makeText(getApplicationContext(),"enter email id",Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(passwordStringLogin))
                {
                    Toast.makeText(getApplicationContext(),"enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                auth=FirebaseAuth.getInstance();

                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(emailStringlogin,passwordStringLogin)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(!task.isSuccessful())
                                {

                                    if (passwordStringLogin.length()<6)
                                    {
                                        ed_passlogin.setError("error occur");
                                    }
                                    else
                                    {
                                        Toast.makeText(SigninActivity.this,"authenication failed",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                }
                                else
                                {
                                    Intent intent =new Intent(SigninActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }
}
