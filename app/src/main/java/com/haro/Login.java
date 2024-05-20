package com.haro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText loginet1,loginet2;
    Button loginbtn1;
    ProgressDialog pd;
    FirebaseAuth auth1;
    FirebaseUser user1;
    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Button buttonOne = findViewById(R.id.loginbtn2);
        loginet1=findViewById(R.id.loginet1);
        loginet2=findViewById(R.id.loginet2);
        loginbtn1=findViewById(R.id.loginbtn1);
        pd=new ProgressDialog(this);
        auth1= FirebaseAuth.getInstance();
        user1= auth1.getCurrentUser();

        buttonOne.setOnClickListener(v -> {
            System.out.println("Button Clicked");
            Intent activity2Intent = new Intent(getApplicationContext(), Registration.class);
            activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activity2Intent);
        });

        loginbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String em=loginet1.getText().toString();
        String p1=loginet2.getText().toString();

        if(!em.matches(regex))
        {
            loginet1.setError("Invalid email Id");
            loginet1.requestFocus();
        }
        else if(p1.isEmpty())
        {
            loginet2.setError("Enter password");
            loginet2.requestFocus();
        }
        else {
            pd.setMessage("Validation On Prograss...");
            pd.setTitle("Login Validation");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            auth1.signInWithEmailAndPassword(em,p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        pd.dismiss();
                        nextActivity();
                        Toast.makeText(Login.this,"Login Successful...",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(Login.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void nextActivity() {
        Intent intent=new Intent(Login.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}