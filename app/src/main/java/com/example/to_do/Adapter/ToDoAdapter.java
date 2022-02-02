package com.example.to_do.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
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

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private final DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db) {
        this.db = db;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTaskName());
        if(item.getStatus()==1){
            holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.task.setTextColor(Color.parseColor("#FF0000"));
        }
        final int[] temp_status = {item.getStatus()};
        holder.task.setOnClickListener(view -> {
            if(temp_status[0] ==1){
                temp_status[0] =0;
                db.updateStatus(item.getId(),0);
                holder.task.setPaintFlags(holder.task.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.task.setTextColor(Color.parseColor("#000000"));
            }else{
                temp_status[0] =1;
                db.updateStatus(item.getId(),1);
                holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.task.setTextColor(Color.parseColor("#FF0000"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
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
