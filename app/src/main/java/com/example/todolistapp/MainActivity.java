package com.example.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskInput;
    private Button addTaskButton;
    private Button deleteTaskButton;
    private ListView taskListView;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private int selectedPosition = -1; // Tracks the position of the task being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        taskInput = findViewById(R.id.taskInput);
        addTaskButton = findViewById(R.id.addTaskButton);
        deleteTaskButton = findViewById(R.id.deleteTaskButton);
        taskListView = findViewById(R.id.taskListView);

        // Initialize task list and adapter
        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskListView.setAdapter(adapter);

        // Add or update task button listener
        addTaskButton.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                addTask();  // Adding a new task
            } else {
                updateTask();  // Updating an existing task
            }
        });

        // Delete task button listener
        deleteTaskButton.setOnClickListener(v -> deleteTask());

        // Select a task for editing
        taskListView.setOnItemClickListener((parent, view, position, id) -> selectTask(position));
    }

    // Method to add a new task
    private void addTask() {
        String task = taskInput.getText().toString().trim();
        if (task.isEmpty()) {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        } else {
            tasks.add(task);
            adapter.notifyDataSetChanged();
            taskInput.setText("");
            Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to select a task for editing
    private void selectTask(int position) {
        taskInput.setText(tasks.get(position));  // Set the input field to the selected task's text
        addTaskButton.setText("Update Task");    // Change the button text to "Update Task"
        deleteTaskButton.setVisibility(View.VISIBLE); // Show delete button
        selectedPosition = position;  // Store selected position for updating
    }

    // Method to update the selected task
    private void updateTask() {
        String updatedTask = taskInput.getText().toString().trim();
        if (updatedTask.isEmpty()) {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        } else {
            // Replace the text of the task at the selected position
            tasks.set(selectedPosition, updatedTask);
            adapter.notifyDataSetChanged();  // Notify the adapter to update the ListView
            taskInput.setText("");  // Clear the input field
            addTaskButton.setText("Add Task");  // Reset button text to "Add Task"
            deleteTaskButton.setVisibility(View.GONE);  // Hide delete button after update
            selectedPosition = -1;  // Reset selected position after updating
            Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete the selected task
    private void deleteTask() {
        if (selectedPosition != -1) {
            tasks.remove(selectedPosition);  // Remove the task at the selected position
            adapter.notifyDataSetChanged();  // Notify the adapter to update the ListView
            taskInput.setText("");  // Clear the input field
            addTaskButton.setText("Add Task");  // Reset button text to "Add Task"
            deleteTaskButton.setVisibility(View.GONE);  // Hide delete button after deletion
            selectedPosition = -1;  // Reset selected position after deletion
            Toast.makeText(this, "Task deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
