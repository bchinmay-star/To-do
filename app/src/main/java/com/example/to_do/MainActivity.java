package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.example.to_do.Adapter.CompleteAdapter;
import com.example.to_do.Adapter.ToDoAdapter;
import com.example.to_do.Model.ToDoModel;
import com.example.to_do.Utils.DatabaseHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView todoListRecyclerView;
    private ToDoAdapter todoTasksAdapter;
    private List<ToDoModel> todoTaskList;

    private CompleteAdapter completeTasksAdapter;
    private List<ToDoModel> completeTaskList;

    private EditText inputText;
    private DatabaseHandler db;
    static List<ToDoModel> myMap = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        db.openDatabase();

        todoTaskList = new ArrayList<>();
        todoListRecyclerView = findViewById(R.id.todoList);
        todoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoTasksAdapter = new ToDoAdapter(db);
        todoListRecyclerView.setAdapter(todoTasksAdapter);

        todoTaskList = db.getAllTasks();
        Collections.reverse(todoTaskList);
        todoTasksAdapter.setTasks(todoTaskList);

        completeTaskList = new ArrayList<>();
        RecyclerView completeListRecyclerView = findViewById(R.id.completedList);
        completeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        completeTasksAdapter = new CompleteAdapter(db);
        completeListRecyclerView.setAdapter(completeTasksAdapter);

        completeTaskList = db.getAllCompleteTasks();
        Collections.reverse(completeTaskList);
        completeTasksAdapter.setTasks(completeTaskList);



        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputString = inputText.getText().toString();
                if(inputString.length()<5){
                    inputText.setError("The task should have at least 5 letters.");
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };


        db = new DatabaseHandler(this);
        db.openDatabase();

        inputText = findViewById(R.id.inputText);
        inputText.setOnKeyListener((view, i, keyEvent) -> {
            if(keyEvent.getAction()==KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER){
                String inputString = inputText.getText().toString();
                if(inputString.length()<5){
                    inputText.setError("The task should have at least 5 letters.");
                    inputText.addTextChangedListener(textWatcher);
                }else{

                    ToDoModel task = new ToDoModel();
                    task.setTaskName(inputString);
                    task.setStatus(0);
                    db.insertTask(task);

                    todoTaskList = db.getAllTasks();
                    Collections.reverse(todoTaskList);
                    todoTasksAdapter.setTasks(todoTaskList);

                    inputText.getText().clear();
                    inputText.setError(null);
                    inputText.removeTextChangedListener(textWatcher);
//
                }

                return true;
            }
            return false;
        });


        Button btnRemoveCompletedTasks = findViewById(R.id.btnRemoveCompletedTasks);
        btnRemoveCompletedTasks.setOnTouchListener((view, motionEvent) -> {
            for (int i = 0; i < todoTaskList.size(); i++) {
                if(todoTaskList.get(i).getStatus()==1){
                    myMap.add(todoTaskList.get(i));
                }
            }

            for(int i=0;i<myMap.size();i++){
                db.deleteTask(myMap.get(i).getId());
                todoTaskList.remove(myMap.get(i));
                db.insertCompleteTask(myMap.get(i));
            }

            todoTaskList = db.getAllTasks();
            Collections.reverse(todoTaskList);
            todoListRecyclerView.setAdapter(todoTasksAdapter);
            todoTasksAdapter.setTasks(todoTaskList);

            completeTaskList = db.getAllCompleteTasks();
            Collections.reverse(completeTaskList);
            completeTasksAdapter.setTasks(completeTaskList);

            myMap.clear();
            return false;
        });
    }
}