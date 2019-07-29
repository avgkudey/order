package com.example.kasun.sql;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;
    DatabaseHelper mydb;
    CustomerToyAdapter listAdapter;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        user = (User) getIntent().getSerializableExtra("USER");

        mydb = new DatabaseHelper(this);
        Cursor data = mydb.getToyList();
        listView = findViewById(R.id.customer_activity_listview);
        fab = findViewById(R.id.customer_activity_fab);
        listAdapter = new CustomerToyAdapter(this, R.layout.customer_toy_item);

        Toast.makeText(this, String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
        while (data.moveToNext()) {
            TOY toy = new TOY(data.getInt(0),
                    data.getString(1), data.getString(2), data.getString(3));
            listAdapter.add(toy);
            listView.setAdapter(listAdapter);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddToyActivity.class));
            }
        });

    }
}
