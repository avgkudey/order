package com.example.kasun.sql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class AdminViewOrderActivity extends AppCompatActivity {

    TextView name, amount, customer;
    DatabaseHelper databaseHelper;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);
        int ID = getIntent().getIntExtra("ID", 1);
        String Name = getIntent().getStringExtra("NAME");
        String CUSTOMER = getIntent().getStringExtra("CUSTOMER");
        String AMOUNT = getIntent().getStringExtra("AMOUNT");
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.admin_view_order_name);
        amount = findViewById(R.id.admin_view_order_amount);
        customer = findViewById(R.id.admin_view_order_customer);

        name.setText(Name);
        amount.setText(AMOUNT);
        customer.setText(CUSTOMER);

        Toast.makeText(this, String.valueOf(ID), Toast.LENGTH_SHORT).show();
    }
}
