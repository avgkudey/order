package com.example.kasun.sql;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class AdminOrderActivity extends AppCompatActivity {
    ListView listView;
    DatabaseHelper mydb;
    OrderAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);


        mydb = new DatabaseHelper(this);

        listView = findViewById(R.id.admin_orders_listview);

        listAdapter = new OrderAdapter(this, R.layout.order_item_layout);
        loadData();
    }

    private void loadData() {
        Cursor data = mydb.getOrdersList();
        while (data.moveToNext()) {
            Order order = new Order(
                    data.getInt(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3));
            listAdapter.add(order);

        }
        listView.setAdapter(listAdapter);
    }
}

