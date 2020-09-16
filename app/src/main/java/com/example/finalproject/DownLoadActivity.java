package com.example.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
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

public class DownLoadActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        //set up activity elements
        ListView list = findViewById(R.id.layout_download);
        Button readButton = findViewById(R.id.btn_read);
        Button deleteButton = findViewById(R.id.btn_delete_download);
        //setup the list of books names for the list view and the list of urls that correspond to that list view
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Books)));
        final ArrayList<String> booksList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.BookList)));
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrList );
        //set the adapter for the list view
        list.setAdapter(adapt);


        //change to delete activity
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(), DeleteBookActivity.class);
                startActivity(I);
            }
        });
        //change to read Activity
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(I);
            }
        });
        //List activity
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int position1 = position;
                final View view2 = view;
                //use fetch code to get the book that is suppose to be downloaded
                FetchValues f= new FetchValues();

                f.books = booksList;
                f.n = position; // position of the item in the book list
                f.file = ((TextView) view2).getText().toString(); // set up the file name for the book

                //set up a file path for the file
                String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + f.file;
                File filePath = new File(path);

                //check if the file path exists if not download it
                if(!filePath.exists()){
                    Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                    new DownloadFilesTask().execute(f);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Book already Exists", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    //use FetchValues to get it through the AsyncTask
     class FetchValues {
        ArrayList<String> books;
        int n;
        String file;
        public void Test(ArrayList<String> List, Integer position, String FileName) {
            books = List;
            n = position;
            file = FileName;
        }



    }

    //Async task to download the book in a background thread
    class  DownloadFilesTask extends AsyncTask<FetchValues, ArrayList, String>{

        // View view,ArrayList<String> books, int position, Integer ... params
        protected String doInBackground(FetchValues ... params) {
            //download the book
            Integer position = params[0].n;
            ArrayList<String> books= params[0].books;
            String URL = books.get(position);
            fetch f = new fetch();
            String data = f.fetchItem(URL);
            //if the book doesnt download properly or if the book is just an empty string  dont write to file
            if (data.equals("")){
                return "False";
            }
            else {
                ReadWrite rw = new ReadWrite();
                rw.Writer(data, params[0].file, getApplicationContext());
                return "";
            }

        }
        protected void onPostExecute(String result) {
            //tell user if download fails and if it succeeds
            if (result.equals("")) {
                Toast.makeText(getApplicationContext(), "Download Complete", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();
            }

        }





    }


}

