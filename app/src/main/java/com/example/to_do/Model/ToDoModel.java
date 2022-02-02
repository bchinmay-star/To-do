package com.example.to_do.Model;

public class ToDoModel {
    private int id, status;
    private String taskName;

    public int getId() {
        return id;
    }

//    public ToDoModel(int id, int status, String task) {
//        this.id = id;
//        this.status = status;
//        this.task = task;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
//    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
