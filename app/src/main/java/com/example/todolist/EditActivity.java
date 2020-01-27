package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    int taskId;
    EditText editText;
    String task, editTask;

    @Override
    public void onBackPressed() {
        //Do nothing
    }

    public void saveEdit(View view){

        editText = findViewById(R.id.editText);
        editTask = editText.getText().toString();

        HomeActivity.tasks.set(taskId, editTask);
        HomeActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
        ArrayList<String> set = new ArrayList<>(HomeActivity.tasks);
        try{
            sharedPreferences.edit().putString("tasks",ObjectSerializer.serialize(set)).apply();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    public void cancelEdit(View view){

        HomeActivity.tasks.set(taskId, task);
        HomeActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
        ArrayList<String> set = new ArrayList<>(HomeActivity.tasks);
        try{
            sharedPreferences.edit().putString("tasks",ObjectSerializer.serialize(set)).apply();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId",-1);
        task = HomeActivity.tasks.get(taskId);
        editText.setText(task);



    }
}
