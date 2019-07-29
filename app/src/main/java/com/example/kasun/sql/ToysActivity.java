package com.example.kasun.sql;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class ToysActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;
    FloatingActionButton orders;
    DatabaseHelper mydb;
    ToyAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toys);

        mydb = new DatabaseHelper(this);

        listView = findViewById(R.id.toys_activity_list_view);
        fab = findViewById(R.id.toys_activity_fab);
        orders = findViewById(R.id.toys_activity_orders);
        listAdapter = new ToyAdapter(this, R.layout.toy_item_layout);
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddToyActivity.class));
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminOrderActivity.class));
            }
        });
    }

    private void loadData() {
        Cursor data = mydb.getToyList();
        while (data.moveToNext()) {
            TOY toy = new TOY(data.getInt(0),
                    data.getString(1), data.getString(2), data.getString(3));
            listAdapter.add(toy);

        }
        listView.setAdapter(listAdapter);
    }
}
