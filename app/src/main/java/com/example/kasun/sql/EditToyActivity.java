package com.example.kasun.sql;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditToyActivity extends AppCompatActivity {

    EditText name, price, description;
    Button cancel, save;
    int ID;
    DatabaseHelper databaseHelper;
    TOY toy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toy);
        databaseHelper = new DatabaseHelper(this);
        ID = getIntent().getIntExtra("ID", 1);

        name = findViewById(R.id.edit_toy_name);
        price = findViewById(R.id.edit_toy_price);
        description = findViewById(R.id.edit_toy_description);
        cancel = findViewById(R.id.edit_toy_cancel_btn);
        save = findViewById(R.id.edit_toy_save_btn);


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

        Cursor data = databaseHelper.getToyInfo(ID);
        while (data.moveToNext()) {
            toy = new TOY(data.getInt(0),
                    data.getString(1), data.getString(2), data.getString(3));
        }
        name.setText(toy.getName());
        price.setText(toy.getPrice());
        description.setText(toy.getDescription());

    }

    private void saveToy() {

        boolean check = databaseHelper.UpdateToy(String.valueOf(ID), name.getText().toString(), price.getText().toString(), description.getText().toString());
        if (check) {
            Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ToysActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
