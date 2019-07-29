package com.example.kasun.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter {
    List list = new ArrayList();

    DatabaseHelper databaseHelper;

    public OrderAdapter(Context context, int resource) {
        super(context, resource);
        databaseHelper = new DatabaseHelper(context);
    }

    static class LayoutHandler {
        TextView name, amount;
        LinearLayout linearLayout;
    }

    @Override
    public void add(Object object) {
        list.add(object);
        super.add(object);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.order_item_layout, parent, false);
            layoutHandler = new LayoutHandler();

            layoutHandler.linearLayout = row.findViewById(R.id.order_item_layout);
            layoutHandler.name = row.findViewById(R.id.order_item_toy);
            layoutHandler.amount = row.findViewById(R.id.order_item_amount);

            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();

        }
        final Order order = (Order) this.getItem(position);
//        TOY toy = (TOY) databaseHelper.getToyInfo(Integer.valueOf(order.getName()));

        TOY toy = null;
        Cursor data = databaseHelper.getToyInfo(Integer.valueOf(order.getName()));
        while (data.moveToNext()) {
            toy = new TOY(data.getInt(0),
                    data.getString(1), data.getString(2), data.getString(3));
        }


//        User orderUser = (User) databaseHelper.getUserInfo(Integer.valueOf(order.getCustomer()));
        User orderUser = null;

        Cursor datauser = databaseHelper.getUserInfo(Integer.valueOf(order.getCustomer()));
        while (datauser.moveToNext()) {
            orderUser = new User(datauser.getInt(0),
                    datauser.getString(1), datauser.getString(2), datauser.getString(3));
        }

        order.setName(toy.getName());
        order.setCustomer(orderUser.getName());
        layoutHandler.name.setText(order.getName());

        layoutHandler.amount.setText(order.getAmount());
        layoutHandler.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminViewOrderActivity.class);
                intent.putExtra("ID", order.getId());
                intent.putExtra("NAME", order.getName());
                intent.putExtra("AMOUNT", order.getAmount());
                intent.putExtra("CUSTOMER", order.getCustomer());
                view.getContext().startActivity(intent);
            }
        });


        return row;

    }
}
