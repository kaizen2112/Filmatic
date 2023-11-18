package com.example.filmatic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmatic.R;
import com.example.filmatic.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private TextView loginNOW;
    private TextInputEditText usernametxt, emailtxt, dateofbirthtxt, passwordtxt;
    private Button btn_reg;
    FirebaseAuth mAuth;
    ActivityMainBinding binding;  //1
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
        buttonsClick();
    }
    private void initView() {
        loginNOW = findViewById(R.id.loginNOW);
        usernametxt = findViewById(R.id.editTextUsername);
        emailtxt = findViewById(R.id.editTextEmail);
        dateofbirthtxt = findViewById(R.id.editTextDateofBirth);
        passwordtxt = findViewById(R.id.editTextPassword);

        btn_reg = findViewById(R.id.buttonRegistration);

        mAuth = FirebaseAuth.getInstance();

    }
    private void buttonsClick() {
        loginNOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
        });

        // ...

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, email, dob, password;
                username = String.valueOf(usernametxt.getText());
                email = String.valueOf(emailtxt.getText());
                dob = String.valueOf(dateofbirthtxt.getText());
                password = String.valueOf(passwordtxt.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Registration.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dob)) {
                    Toast.makeText(Registration.this, "Enter Date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase auth
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Get the UID of the newly created user
                                    String uid = mAuth.getCurrentUser().getUid();

                                    // Create a UserInfoClass instance
                                    UserInfoClass users = new UserInfoClass(username, email, dob, password);

                                    // RTDB: Use UID as the key in "Users" node
                                    db = FirebaseDatabase.getInstance();
                                    reference = db.getReference("Users");
                                    reference.child(uid).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Registration.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // Redirect to login after successful registration
                                    Toast.makeText(Registration.this, "Account created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registration.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Registration.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    // Log the exception for further analysis
                                    Log.e("Registration", "Authentication failed", task.getException());
                                }
                            }
                        });
            }
        });

// ...

    }
}