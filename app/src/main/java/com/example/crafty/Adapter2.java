package com.example.crafty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter2 extends BaseAdapter {

    private ArrayList<DataClass2> dataList2;
    private Context context;
    LayoutInflater layoutInflater;

    public Adapter2(ArrayList<DataClass2> dataList2, Context context) {
        this.dataList2 = dataList2;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList2.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = layoutInflater.inflate(R.layout.grid_product, null);
        }

        // Tetapkan pendengar klik untuk setiap item
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan item yang dipilih
                DataClass2 selectedItem = dataList2.get(i);

                // Buat Intent untuk memulai DetailGridActivity
                Intent intent = new Intent(context, DetailGridActivity.class);

                // Sisipkan data ke DetailGridActivity
                intent.putExtra("selectedItem", selectedItem);

                // Mulai DetailGridActivity
                context.startActivity(intent);
            }
        });

        ImageView gridImage = view.findViewById(R.id.produkview);
        TextView gridCaption1 = view.findViewById(R.id.name_product_view);
        TextView gridCaption2 = view.findViewById(R.id.harga_product_view);
        TextView gridCaption3 = view.findViewById(R.id.deskripsi_product);

        Glide.with(context).load(dataList2.get(i).getImageUrl()).into(gridImage);
        gridCaption1.setText(dataList2.get(i).getCaption1());
        gridCaption2.setText(dataList2.get(i).getCaption2());
        gridCaption3.setText(dataList2.get(i).getCaption3());
        return view;
    }
}

