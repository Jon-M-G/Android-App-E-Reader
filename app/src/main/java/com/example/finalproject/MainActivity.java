package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up activity elements
        ListView list = findViewById(R.id.layout_read);
        Button deleteButton = findViewById(R.id.btn_delete_read);
        Button downloadButton = findViewById(R.id.btn_downloads);

        // set up List View book names
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Books)));

        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);

        list.setAdapter(adapt);

        //move to delete activity

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), DeleteBookActivity.class);
                startActivity(I);
            }
        });
        //move to download actiivty
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), DownLoadActivity.class);
                startActivity(I);
            }
        });
        //read books if they are selected to be read
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //setup file for reading and writing
                String bookName = ((TextView) view).getText().toString();
                File file = new File(getApplicationContext().getFilesDir(), bookName + ".txt");

                //get the file path for the book
                String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + bookName;
                File filePath = new File(path);

                //if the file exists,pass the file path to the ReaderActivity else say that the book cant be found
                if (filePath.exists()) {

                    Intent I = new Intent(getApplicationContext(), ReaderActivity.class);
                    //pass file path to new Activity
                    I.putExtra("book",filePath.toString());
                    //pass the bookPosition to help manage preferences in readerActivity
                    I.putExtra("bookPosition", position);
                    startActivity(I);

                } else {
                    Toast.makeText(getApplicationContext(), "Book not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
