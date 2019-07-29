package com.example.kasun.sql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    Button cancel, register;
    EditText name, email, password, confirm_password;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        cancel = findViewById(R.id.cancel_btn_signup);
        register = findViewById(R.id.registerbtn_signup);
        user = new User();
        name = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        confirm_password = findViewById(R.id.confirm_password_signup);
        databaseHelper = new DatabaseHelper(this);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {

        if (password.getText().toString().trim().equals(confirm_password.getText().toString().trim())) {
            if (!databaseHelper.validateUser(email.getText().toString().trim())) {

                user.setName(name.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setRole("Customer");

                databaseHelper.addUser(user);

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));


            } else {
                Toast.makeText(this, "Email Already Exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Passwords not match", Toast.LENGTH_SHORT).show();
            password.getText().clear();
            confirm_password.getText().clear();

        }

    }

    private void cancel() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
