package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.ShareActivity;
import com.example.multiplechoiceapp.models.Topic_Set;

import java.util.List;

public class CustomAdapterTopicSet extends ArrayAdapter<Topic_Set> {

    private Context context;
    private int resource;
    private List<Topic_Set> data;
    private int selectedItem = -1;
    private OnButtonClickListener onButtonClickListener;

    // Khai báo interface
    public interface OnButtonClickListener {
        void onButtonClick(Topic_Set topicSet, int i);
    }

    // Phương thức để thiết lập người nghe cho nút btnThi, btndetailTopicSet
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }
    public CustomAdapterTopicSet(@NonNull Context context, int resource, List<Topic_Set> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data= data;
    }
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }
    public void clearSelection() {
        selectedItem = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTopicSetCode = convertView.findViewById(R.id.txtidTopicSet);
            viewHolder.txtTopicSetName = convertView.findViewById(R.id.txtnameTopicSet);
            viewHolder.btnThi = convertView.findViewById(R.id.btnexamTopicSet);
            viewHolder.btndetailTopicSet = convertView.findViewById(R.id.btndetailTopicSet);
            viewHolder.btnshareTopicSet = convertView.findViewById(R.id.btnshareTopicSet);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Topic_Set topic_set = data.get(position);
        String t =  String.valueOf(topic_set.getTopicSetID());
        if(topic_set.getTopicSetID()!=null){
            t = topic_set.getTopicSetID().toString();
        }

        viewHolder.txtTopicSetCode.setText(t);
        viewHolder.txtTopicSetName.setText(topic_set.getTopicSetName());
        viewHolder.btnThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(data.get(position),1);
                }
            }
        });

        viewHolder.btndetailTopicSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onButtonClickListener !=null){
                    onButtonClickListener.onButtonClick(data.get(position),2);
                }
            }
        });

        viewHolder.btnshareTopicSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClickListener !=null) {
                    onButtonClickListener.onButtonClick(data.get(position),3);
                }

            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView txtTopicSetCode;
        TextView txtTopicSetName;
        Button btnThi, btndetailTopicSet;
        ImageButton btnshareTopicSet;
    }

}
