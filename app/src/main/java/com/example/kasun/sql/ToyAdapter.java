package com.example.kasun.sql;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToyAdapter extends ArrayAdapter {
    List list = new ArrayList();

    DatabaseHelper databaseHelper;

    public ToyAdapter(Context context, int resource) {
        super(context, resource);
        databaseHelper = new DatabaseHelper(context);
    }

    static class LayoutHandler {
        TextView id, name, description, price;
        ImageButton deleteBtn;
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
            row = layoutInflater.inflate(R.layout.toy_item_layout, parent, false);
            layoutHandler = new LayoutHandler();

            layoutHandler.linearLayout = row.findViewById(R.id.toy_item_linear_layout);
            layoutHandler.name = row.findViewById(R.id.toy_item_name);
            layoutHandler.description = row.findViewById(R.id.toy_item_description);
            layoutHandler.price = row.findViewById(R.id.toy_item_price);
            layoutHandler.deleteBtn = row.findViewById(R.id.toy_item_delete_btn);
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
                Intent intent = new Intent(getContext(), EditToyActivity.class);
                intent.putExtra("ID", toy.getId());
                view.getContext().startActivity(intent);
            }
        });
        layoutHandler.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteToy(String.valueOf(toy.getId()));
                view.getContext().startActivity(new Intent(view.getContext(), ToysActivity.class));
            }
        });


        return row;

    }
}
