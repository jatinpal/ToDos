package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add){
            Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
            startActivity(intent);

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
        ListView listView = findViewById(R.id.listView);

        ArrayList<String> set = new ArrayList<>();
        try{
            set = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("tasks",ObjectSerializer.serialize(new ArrayList<String>())));

        }catch (Exception e){
            e.printStackTrace();
        }

        if (set == null) {

        } else {
            tasks = new ArrayList(set);
        }

        HomeActivity.arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,HomeActivity.tasks);
        listView.setAdapter(HomeActivity.arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                intent.putExtra("taskId",position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemToDelete = position;

                new AlertDialog.Builder(HomeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Remove this task?")
                        .setMessage("Do you want to remove this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tasks.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                ArrayList<String> set = new ArrayList<>(HomeActivity.tasks);
                                try{
                                    sharedPreferences.edit().putString("tasks",ObjectSerializer.serialize(set)).apply();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });


    }

}
