package com.example.multiplechoiceapp.activities.Hien;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChartStatistic extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_statistic);

        barChart = findViewById(R.id.chart);

        // Lấy dữ liệu từ CustomAdapterStatistic
        List<Object> dataList = CustomAdapterStatistic.getDataList();

        // Tạo danh sách các thanh cột
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            List<String> data = (List<String>) dataList.get(i);
            float value = Float.parseFloat(data.get(2));
            entries.add(new BarEntry(i, value));
        }

        // Tạo danh sách màu sắc cho các thanh cột
        int[] colors = new int[]{
                Color.rgb(255, 102, 0), // Cam
                Color.rgb(51, 153, 255), // Xanh dương
                Color.rgb(255, 204, 51), //Vàng
               // Color.rgb(255, 0, 0),
                // Thêm các màu khác nếu cần
        };
        Description description = new Description();
        description.setEnabled(false); // Vô hiệu hóa tiêu đề mặc định của biểu đồ
        barChart.setDescription(description);

        // Tạo dataset và đặt giá trị cho biểu đồ cột, gán màu sắc cho từng thanh cột
        BarDataSet dataSet = new BarDataSet(entries, "Số người làm bài");
        dataSet.setValueTextSize(15f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColors(colors);

        // Thêm hiệu ứng cho biểu đồ
        barChart.animateY(1000, Easing.EaseInOutQuad);

        // Tùy chỉnh trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < dataList.size()) {
                    List<String> data = (List<String>) dataList.get(index);
 //                   return data.get(0); // Mã đề
                }
                return ""; // Trả về chuỗi trống nếu không có dữ liệu phù hợp
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisLineWidth(2f);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(45); // Giảm góc quay để tránh chồng chéo
        xAxis.setTextSize(15f); // Đặt kích thước chữ cho trục X
        xAxis.setTextColor(Color.BLACK); // Đặt màu chữ cho trục X

// Tùy chỉnh trục Y
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisLineColor(Color.BLACK);
        leftAxis.setGridColor(Color.BLACK);
        leftAxis.setGranularity(1f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisLineWidth(2f);
        leftAxis.setTextSize(20f);

// Vẽ biểu đồ
        barChart.setData(new BarData(dataSet));
        barChart.invalidate();

        // Vẽ biểu đồ
        barChart.setData(new BarData(dataSet));
        barChart.invalidate();
    }
}
