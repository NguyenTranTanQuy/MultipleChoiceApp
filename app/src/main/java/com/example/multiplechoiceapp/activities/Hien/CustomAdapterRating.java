package com.example.multiplechoiceapp.activities.Hien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;

import java.util.List;
import java.util.Random;

public class CustomAdapterRating extends ArrayAdapter {
    Context context;
    int resource;
    List<Object> dataList;
    Random random = new Random();

    public CustomAdapterRating(@NonNull Context context, int resource, List<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvSTT = convertView.findViewById(R.id.tvSTT);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvKetQua = convertView.findViewById(R.id.tvKetQua);
        TextView tvDiem = convertView.findViewById(R.id.tvDiem);

        List<String> data = (List)dataList.get(position);
        tvSTT.setText(String.valueOf(position + 1));
        tvTen.setText(data.get(0).toString());
        tvKetQua.setText(data.get(1).toString() + "/" + data.get(2).toString());
        tvDiem.setText(String.valueOf(data.get(3)));

        return convertView;
    }
}
