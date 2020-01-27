package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {

    String addTask;
    EditText addText;

    @Override
    public void onBackPressed() {
        //do nothing;
    }

    public void saveTask(View view){

        addText = findViewById(R.id.addText);
        addTask = addText.getText().toString();

        if(addTask.length()>0){

            HomeActivity.tasks.add(0,addTask);
            HomeActivity.arrayAdapter.notifyDataSetChanged();

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
            ArrayList<String> set = new ArrayList<>(HomeActivity.tasks);
            try {
                sharedPreferences.edit().putString("tasks", ObjectSerializer.serialize(set)).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
        }
        else{

            Toast toast = Toast.makeText(this,"Please describe your task first!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

        }
    }


    public void cancelTask(View view){

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
        setContentView(R.layout.activity_add_task);

    }
}
