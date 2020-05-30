package com.iamkosgei.androidroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iamkosgei.androidroom.model.Thought;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements AddThoughDialog.AddThoughDialogListener, View.OnClickListener {
    private ThoughViewModel thoughViewModel;

    private AddThoughDialog addThoughDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addThoughDialog = new AddThoughDialog();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        FloatingActionButton floatingActionButtonDeleteAll = findViewById(R.id.floatingActionButtonDeleteAll);
        floatingActionButton.setOnClickListener(this);
        floatingActionButtonDeleteAll.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ThoughtAdapter thoughtAdapter = new ThoughtAdapter();
        recyclerView.setAdapter(thoughtAdapter);

        thoughViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ThoughViewModel.class);

        thoughViewModel.getAllThoughts().observe(this, new Observer<List<Thought>>() {
            @Override
            public void onChanged(List<Thought> thoughts) {
                thoughtAdapter.submitList(thoughts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    thoughViewModel.delete(thoughtAdapter.getThough(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        thoughtAdapter.setOnItemClickListener(thought -> {
            AddThoughDialog editThoughDialog = new AddThoughDialog();

            Bundle bundle = new Bundle();
            bundle.putInt("id", thought.getId());
            bundle.putString("title", thought.getTitle());
            bundle.putString("desc", thought.getDescription());
            editThoughDialog.setArguments(bundle);
            editThoughDialog.show(getSupportFragmentManager(), "edit_thought");

        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, boolean isEdit, int id, String title, String description) {
        Thought thought = new Thought(title, description);
        if (isEdit){
            thought.setId(id);
            thoughViewModel.edit(thought);
        }
        else {
            thoughViewModel.insert(thought);
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButton){
            addThoughDialog.show(getSupportFragmentManager(), "add_thought");
        }
        else if (v.getId() == R.id.floatingActionButtonDeleteAll){
            thoughViewModel.deleteAllThoughts();
        }

    }
}