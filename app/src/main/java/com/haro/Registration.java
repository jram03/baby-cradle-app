package com.haro;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    EditText reget2,reget3,reget4;
    Button regbtn1,regbtn2;
    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog pd;
    FirebaseAuth auth1;
    FirebaseUser user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regbtn1 = findViewById(R.id.regbtn1);
        regbtn2 = findViewById(R.id.regbtn2);
        reget2 = findViewById(R.id.reget2);
        reget3 = findViewById(R.id.reget3);
        reget4 = findViewById(R.id.reget4);
        pd=new ProgressDialog(this);
        auth1=FirebaseAuth.getInstance();
        user1= auth1.getCurrentUser();

        regbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });

        regbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void back() {
        Intent intent=new Intent(Registration.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void authenticate() {
        String em=reget2.getText().toString();
        String p1=reget3.getText().toString();
        String p2=reget4.getText().toString();

        if(!em.matches(regex))
        {
            reget2.setError("Invalid email Id");
            reget2.requestFocus();
        }
        else if(p1.isEmpty()||p1.length()<6)
        {
            reget3.setError("Enter valid password of length greater than 5");
            reget3.requestFocus();
        }
        else if(!p1.equals(p2))
        {
            reget4.setError("Password & Confirm Password Mistmatches");
            reget4.requestFocus();
        }
        else
        {
            pd.setMessage("Registration On Processing...");
            pd.setTitle("New Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            auth1.createUserWithEmailAndPassword(em,p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        pd.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Registration.this,"Registration Sucessful...",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(Registration.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(Registration.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
