package com.iamkosgei.androidroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamkosgei.androidroom.model.Thought;

import java.util.ArrayList;
import java.util.List;

public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtAdapter.ThoughtHolder> {
    private List<Thought> thoughtList = new ArrayList<>();

    @NonNull
    @Override
    public ThoughtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thought_item, parent,false);
        return new ThoughtHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ThoughtHolder holder, int position) {
        holder.bindThought(thoughtList.get(position));
    }
    @Override
    public int getItemCount() {
        return thoughtList.size();
    }
    public void setThoughts(List<Thought> thoughts){
        this.thoughtList = thoughts;
        notifyDataSetChanged();
    }

    public  Thought getThough(int position){
        return  thoughtList.get(position);
    }
    class ThoughtHolder extends  RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;

        public ThoughtHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
        }

        void bindThought(Thought thought){
            title.setText(thought.getTitle().toUpperCase());
            description.setText(thought.getDescription());
        }
    }
}
