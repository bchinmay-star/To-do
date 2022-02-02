package com.example.to_do.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Model.ToDoModel;
import com.example.to_do.R;
import com.example.to_do.Utils.DatabaseHandler;

import java.util.List;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> {

    private List<ToDoModel> completeList;
    private final DatabaseHandler db;

    public CompleteAdapter(DatabaseHandler db) {
        this.db = db;
    }

    @NonNull
    @Override
    public CompleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        return new CompleteAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompleteAdapter.ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = completeList.get(position);
        holder.task.setText(item.getTaskName());
    }

    @Override
    public int getItemCount() {
        return completeList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<ToDoModel> completeList) {
        this.completeList = completeList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView task;
        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.sampleTask);
        }
    }
}
