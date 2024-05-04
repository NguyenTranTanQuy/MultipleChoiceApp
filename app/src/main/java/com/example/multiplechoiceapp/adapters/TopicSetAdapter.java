package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.ShareActivity;
import com.example.multiplechoiceapp.models.Topic_Set;

import com.example.multiplechoicetestapp.viewholders.TopicSetViewHolder;


import java.util.ArrayList;
import java.util.List;

public class TopicSetAdapter extends RecyclerView.Adapter<TopicSetViewHolder> implements Filterable {
    List<Topic_Set> topicSets;

    List<Topic_Set> filterTopicSets;
    Context context;

    private int lastPosition = -1;

    public TopicSetAdapter(List<Topic_Set> topicSet, Context context) {
        this.filterTopicSets = topicSet;
        topicSets = new ArrayList<>(topicSet);
        this.context = context;
    }
    @NonNull
    @Override
    public TopicSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.topic_card,parent,false);
        TopicSetViewHolder topicViewHolder = new TopicSetViewHolder(view);
        return topicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicSetViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Resources resources = Resources.getSystem();
        holder.imgTopic.setImageResource(R.drawable.ic_launcher_foreground);
        holder.textName.setText(topicSets.get(index).getTopicSetName());
        holder.textTime.setText(topicSets.get(index).getDuration().toString());
        holder.textId.setText(String.valueOf(topicSets.get(index).getTopicSetID()));
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShareActivity.class);
                intent.putExtra("topicSetCode",topicSets.get(index).getTopicSetID());
                view.getContext().startActivity(intent);
            }
        });
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return topicSets.size();
    }

    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Topic_Set> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(topicSets);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Topic_Set item : filterTopicSets) {
                    if (item.getTopicSetName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            topicSets.clear();
            topicSets.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
