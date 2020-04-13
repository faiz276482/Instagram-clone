package com.nerdytech.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button loginBtn;
    private TextView registerUser;
    ProgressDialog pd;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpWidgets();
        setUpFireBase();
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Please Wait");
                pd.show();
                final String emails=email.getText().toString();
                final String passwords=password.getText().toString();
                if(TextUtils.isEmpty(emails)){
                    Toast.makeText(LoginActivity.this, "Plaese Enter proper email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(passwords)){
                    Toast.makeText(LoginActivity.this, "Plaese Enter proper Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(emails,passwords).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            user=mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                pd.dismiss();
                                user=mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                mAuth.signOut();
                                Toast.makeText(LoginActivity.this, "Email not verified\nPlease Verify to Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void setUpWidgets() {
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        registerUser=findViewById(R.id.register_user);
        loginBtn=findViewById(R.id.loginBtn);
        pd=new ProgressDialog(this);
    }
    private void setUpFireBase() {
        mAuth=FirebaseAuth.getInstance();
    }

}
