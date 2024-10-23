package com.example.assignment2;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ToDoListAdaptor extends ArrayAdapter<Task> {
    Context context;
    int resource;

    interface SelectedTask {
        void onTaskClick(int position);
    }

    SelectedTask selectedTask;
    public ToDoListAdaptor(@NonNull Context context, int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        //selectedTask = (SelectedTask) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Task task = getItem(position);
        TextView tv = view.findViewById(R.id.tvTaskName);
        tv.setText(task.getName());
        /*
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTask.onTaskClick(position);
            }
        });
        */
        return view;
    }
}
