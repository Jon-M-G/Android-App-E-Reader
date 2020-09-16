package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class DeleteBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        //Set up activity elements
        ListView list = findViewById(R.id.layout_delete);
        Button readButton = findViewById(R.id.btn_read);
        Button downloadButton = findViewById(R.id.btn_downloads);

        //get list from Strings to use for the book lists
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Books)));
        //adapter for list view
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrList );

        list.setAdapter(adapt);

        //Switch to book select activity
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(I);
            }
        });
        //switch to download Activity
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(),DownLoadActivity.class);
                startActivity(I);
            }
        });
        //Delete book List
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int position1 = position;
                final View view2 = view;
                String FileName = ((TextView) view2).getText().toString();
                File file = new File(getApplicationContext().getFilesDir(),FileName+".txt");

                //get the file path for the book that is being deleted
                String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + FileName;
                File filePath = new File(path);
                //check if there is already a book there if there isnt say Book not found
                if(filePath.exists()){
                    Toast.makeText(getApplicationContext(), "Deleting...", Toast.LENGTH_SHORT).show();
                    deleteFile(FileName);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Book not found", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }

}
