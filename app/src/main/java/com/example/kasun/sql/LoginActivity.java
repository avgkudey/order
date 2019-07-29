package com.example.kasun.sql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    Button login, signup;
    EditText email, password;
    private DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.loginbtn_login);
        signup = findViewById(R.id.signupbtn_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        databaseHelper = new DatabaseHelper(this);
        user = new User();

        makeAdminUser();
        makeDummyToy();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    private void signup() {
        startActivity(new Intent(this, SignupActivity.class));
    }


    private void login() {

        User userInfo = databaseHelper.validateUser(new User(email.getText().toString().trim(), password.getText().toString().trim()));
        if (userInfo != null) {

            if (userInfo.getRole().equals("Admin")) {
                Intent accountsIntent = new Intent(this, ToysActivity.class);
                startActivity(accountsIntent);
            }
            if (userInfo.getRole().equals("Customer")) {
                Intent accountsIntent = new Intent(this, CustomerActivity.class);
                accountsIntent.putExtra("USER", userInfo);
                startActivity(accountsIntent);
            }


        } else {
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeAdminUser() {

        if (!databaseHelper.validateUser("admin@gmail.com")) {

            user.setName("Admin");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setRole("Admin");
            databaseHelper.addUser(user);
        } else {

        }
    }

    private void makeDummyToy() {
        TOY toy = new TOY();
        toy.setName("Toy 1");
        toy.setDescription("description goes here");
        toy.setPrice("5000");
        boolean check = databaseHelper.addToy(toy);
        if (check) {
            Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

        }
    }
}
