package com.iamkosgei.androidroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.iamkosgei.androidroom.model.Thought;

import java.util.ArrayList;
import java.util.List;

public class ThoughtAdapter extends ListAdapter<Thought,ThoughtAdapter.ThoughtHolder> {
    private OnItemClickListener listener;

    public ThoughtAdapter() {
        super(DIFF_CALLBACK);
    }
    private static  final  DiffUtil.ItemCallback<Thought> DIFF_CALLBACK = new DiffUtil.ItemCallback<Thought>() {
        @Override
        public boolean areItemsTheSame(@NonNull Thought oldItem, @NonNull Thought newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Thought oldItem, @NonNull Thought newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public ThoughtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thought_item, parent, false);
        return new ThoughtHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThoughtHolder holder, int position) {
        holder.bindThought(getItem(position));
    }

    public Thought getThough(int position) {
        return getItem(position);
    }

    class ThoughtHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public ThoughtHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));

                    }
                }
            });
        }

        void bindThought(Thought thought) {
            title.setText(thought.getTitle().toUpperCase());
            description.setText(thought.getDescription());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Thought thought);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
