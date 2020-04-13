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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //Widgets
    private EditText username,name,email,password,confirmpassword;
    private Button register_btn;
    private TextView alreadyAUser;

    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference mRootRef;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpWidgets();
        setUpFireBase();

        alreadyAUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Please Wait");
                pd.show();
                final String emails=email.getText().toString();
                final String names=name.getText().toString();
                final String passwords=password.getText().toString();
                String confirm=confirmpassword.getText().toString();
                final String usernames=username.getText().toString();


                if(TextUtils.isEmpty(usernames)){
                    Toast.makeText(RegisterActivity.this, "Plaese Enter proper Username", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(names)){
                    Toast.makeText(RegisterActivity.this, "Plaese Enter proper Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(emails)){
                    Toast.makeText(RegisterActivity.this, "Plaese Enter proper email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(passwords)){
                    Toast.makeText(RegisterActivity.this, "Plaese Enter proper Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(confirm) || !confirm.equals(passwords)){
                    Toast.makeText(RegisterActivity.this, "Password Missmatch", Toast.LENGTH_SHORT).show();
                }
                else if(passwords.length()<6)
                {
                    Toast.makeText(RegisterActivity.this, "Password too Short", Toast.LENGTH_SHORT).show();
                }
                else{

                    mAuth.createUserWithEmailAndPassword(emails,passwords).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Map<String, Object> map=new HashMap<>();
                            pd.dismiss();

                            FirebaseUser currentUser=mAuth.getCurrentUser();
                            map.put("email",emails);
                            map.put("name",names);
                            map.put("username",usernames);
                            map.put("bio","");
                            map.put("imageUrl","default");
                            map.put("uid",(currentUser.getUid()));
                            mRootRef.child("Users").child((currentUser.getUid())).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Toast.makeText(RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                        FirebaseUser currentUser=mAuth.getCurrentUser();
                                        currentUser.sendEmailVerification();
                                        mAuth.signOut();

                                        Toast.makeText(RegisterActivity.this, "Update the profile for better experience", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                }
            }
        });


    }

    private void setUpFireBase() {
        mAuth=FirebaseAuth.getInstance();
        mRootRef=FirebaseDatabase.getInstance().getReference();
    }

    private void setUpWidgets() {
        username=findViewById(R.id.username);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirm_password);
        alreadyAUser=findViewById(R.id.already_a_user);
        register_btn=findViewById(R.id.registerBtn);
        pd=new ProgressDialog(this);
    }
}
