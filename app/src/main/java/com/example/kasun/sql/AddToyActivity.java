package com.example.kasun.sql;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddToyActivity extends AppCompatActivity {

    EditText name, price, description;
    Button cancel, save;
    DatabaseHelper databaseHelper;
    TOY toy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toy);
        databaseHelper = new DatabaseHelper(this);

        name = findViewById(R.id.add_toy_name);
        price = findViewById(R.id.add_toy_price);
        description = findViewById(R.id.add_toy_description);
        cancel = findViewById(R.id.add_toy_cancel_btn);
        save = findViewById(R.id.add_toy_save_btn);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ToysActivity.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToy();
            }
        });


    }

    private void saveToy() {
        TOY toy = new TOY();
        toy.setName(name.getText().toString());
        toy.setPrice(price.getText().toString());
        toy.setDescription(description.getText().toString());

        boolean check = databaseHelper.addToy(toy);
        if (check) {
            Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ToysActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
