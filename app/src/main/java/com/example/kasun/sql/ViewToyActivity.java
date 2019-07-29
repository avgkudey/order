package com.example.kasun.sql;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewToyActivity extends AppCompatActivity {

    TextView name, price, description;
    EditText amount;
    Button orderBtn;
    int ID;
    DatabaseHelper databaseHelper;
    TOY toy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_toy);
        databaseHelper = new DatabaseHelper(this);
        ID = getIntent().getIntExtra("ID", 1);


        name = findViewById(R.id.view_toy_name);
        price = findViewById(R.id.view_toy_price);
        description = findViewById(R.id.view_toy_description);
        amount = findViewById(R.id.view_toy_amount);
        orderBtn = findViewById(R.id.view_toy_orderBtn);


        Cursor data = databaseHelper.getToyInfo(ID);
        while (data.moveToNext()) {
            toy = new TOY(data.getInt(0),
                    data.getString(1), data.getString(2), data.getString(3));
        }
        name.setText(toy.getName());
        price.setText(toy.getPrice());
        description.setText(toy.getDescription());

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.orderToy(toy,amount.getText().toString(),CustomerActivity.user);
            }
        });



    }
}
