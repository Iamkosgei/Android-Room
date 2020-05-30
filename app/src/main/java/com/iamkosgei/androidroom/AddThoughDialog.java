package com.iamkosgei.androidroom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddThoughDialog extends DialogFragment {
    private EditText title, description;
    private static final String TAG = "AddThoughDialog";
    private boolean isEdit=false;
    private int thoughtId;

    public interface AddThoughDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, boolean isEdit, int id, String title, String description);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    AddThoughDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_thought, null);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        builder.setView(view)
                .setPositiveButton(getTag().equals("edit_thought") ? "UPDATE" : "SAVE", (dialog, id) -> {
                    if (title.getText().toString().trim().isEmpty() || description.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        listener.onDialogPositiveClick(AddThoughDialog.this, isEdit, thoughtId, title.getText().toString(), description.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    listener.onDialogNegativeClick(AddThoughDialog.this);
                });

        if (getTag() != null) {
            if (getTag().equals("edit_thought")) {
                isEdit = true;
                Bundle bundle = getArguments();
                thoughtId = bundle.getInt("id");
                title.setText(bundle.getString("title"));
                description.setText(bundle.getString("desc"));
            }
            else {
                isEdit=false;
            }
        }
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddThoughDialogListener) context;
        } catch (ClassCastException e) {
        }
    }
}
