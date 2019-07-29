package com.example.kasun.sql;

import android.content.Context;
import android.content.Intent;
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

public class CustomerToyAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public CustomerToyAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView name, description, price;
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
            row = layoutInflater.inflate(R.layout.customer_toy_item, parent, false);
            layoutHandler = new LayoutHandler();

            layoutHandler.linearLayout = row.findViewById(R.id.customer_toy_item_linear_layout);
            layoutHandler.name = row.findViewById(R.id.customer_toy_item_name);
            layoutHandler.description = row.findViewById(R.id.customer_toy_item_description);
            layoutHandler.price = row.findViewById(R.id.customer_toy_item_price);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();

        }
        final TOY toy = (TOY) this.getItem(position);
        layoutHandler.name.setText(toy.getName());
        layoutHandler.description.setText(toy.getDescription());
        layoutHandler.price.setText(toy.getPrice());
        layoutHandler.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewToyActivity.class);
                intent.putExtra("ID", toy.getId());
                view.getContext().startActivity(intent);
            }
        });

        return row;

    }
}
