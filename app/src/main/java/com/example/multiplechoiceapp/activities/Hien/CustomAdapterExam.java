package com.example.multiplechoiceapp.activities.Hien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;

import java.util.List;
import java.util.Random;

public class CustomAdapterExam extends ArrayAdapter {
    Context context;
    int resource;
    static List<Object> dataList;
    Random random = new Random();

    public CustomAdapterExam(@NonNull Context context, int resource, List<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ImageView imHinh = convertView.findViewById(R.id.imHinh);
        TextView tvMaCD = convertView.findViewById(R.id.tvMaCD);
        TextView tvTenMon = convertView.findViewById(R.id.tvTenMon);
        TextView tvNguoiTao = convertView.findViewById(R.id.tvNguoiTao);
        ProgressBar tp1 = convertView.findViewById(R.id.tp1);

        List<String> data = (List)dataList.get(position);
 //       tvMaCD.setText(data.get(0).toString());

        String defaultTenMon = "Môn: ";
        tvTenMon.setText(defaultTenMon + data.get(1).toString());

//        tvTenMon.setText(data.get(1).toString());
        String defaultNguoiTao = "Người tạo: ";
        tvNguoiTao.setText(defaultNguoiTao + data.get(2).toString());
//        tvNguoiTao.setText(data.get(2).toString());
        tp1.setProgress((int) (Double.valueOf(data.get(3)) * 10));

        int randomNumber = random.nextInt(2);
        if (randomNumber == 0) {
            imHinh.setImageResource(R.drawable.sach5);
        } else {
            imHinh.setImageResource(R.drawable.sach7);
        }

        return convertView;
    }
    public static List<Object> getDataList() {
        return dataList;
    }
}
