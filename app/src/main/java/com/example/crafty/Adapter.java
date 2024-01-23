package com.example.crafty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ArrayList<DataClass> dataList;
    private Context context;
    LayoutInflater layoutInflater;

    public Adapter(ArrayList<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = layoutInflater.inflate(R.layout.grid_view, null);
        }
        ImageView gridImage = view.findViewById(R.id.produk);
        TextView gridCaption1 = view.findViewById(R.id.name_product);
        TextView gridCaption2 = view.findViewById(R.id.harga_product);
        TextView gridCaption3 = view.findViewById(R.id.harga_product);

        Glide.with(context).load(dataList.get(i).getImageUrl()).into(gridImage);
        gridCaption1.setText(dataList.get(i).getCaption1());
        gridCaption2.setText(dataList.get(i).getCaption2());
        gridCaption3.setText(dataList.get(i).getCaption3());
        return view;
    }
}
