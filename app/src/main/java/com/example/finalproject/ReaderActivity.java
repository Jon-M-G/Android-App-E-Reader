package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class ReaderActivity extends AppCompatActivity {
    int page = 0;
    int n = 0;
    float fontSize = 0;

    ArrayList<String> pages = new ArrayList<>();

    SharedPreferences prefer;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);


        //set up activity elements
        final Button next = findViewById(R.id.button_nextpage);
        final Button previous = findViewById(R.id.button_previous);
        final TextView reader = findViewById(R.id.tv_reader);
        final EditText et_jump = findViewById(R.id.et_jump);
        final Button ButtonJump = findViewById(R.id.button_jump);
        final EditText et_font = findViewById(R.id.et_font);
        final Button ButtonFont = findViewById(R.id.button_font);


        reader.setMovementMethod(new ScrollingMovementMethod());

        //get the Intent that was passed over
        Intent I = getIntent();
        //get filePath and the index  of the book from the ListView
        String filePath = I.getExtras().getString("book");
        final int position = I.getExtras().getInt("bookPosition");
        //set up bookLIst to help with managing preferences
        ArrayList<String> bookList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.BookNumbers)));

        final String bookNumber = bookList.get(position); // bookNumber from position
        //set up preferences and editor
        prefer = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefer.edit();
        //get page number from preferences, set 0 if it doesnt exist
        page = prefer.getInt(bookNumber,0);
        et_jump.setText(page+""); // set the page number in et_jump

        fontSize = prefer.getFloat(bookNumber+"Font",12); // remember the font size for each book as well
        et_font.setText(fontSize+"");

        //set up reader to read book
        ReadWrite Read = new ReadWrite();
        String data = Read.Reader(filePath,getApplicationContext());
        //break the book into pages with a max of 3000 characters using PageMaker class
        PageMaker pm = new PageMaker(data);
        pages = new ArrayList<>();
        //get the pages from PageMaker
        pages = pm.getPages();
        n = pages.size();

        //display the current page
        reader.setText(pages.get(page));

        // button for turning the page forward
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page != n-1){
                    page +=1;

                    reader.setText(pages.get(page));
                    et_jump.setText(page+"");
                    // do preferences bit here
                    editor.putInt(bookNumber,page);
                    editor.commit();


                }
            }
        });


        //button for turning the page backward
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page!= 0){
                    page -=1;

                    reader.setText(pages.get(page));
                    et_jump.setText(page+"");

                    //do preferences bit here
                    editor.putInt(bookNumber,page);
                    editor.commit();

                }
            }
        });


        //jump to a specific point in the book, checks if the page number is
        // beyond or below the page count
        ButtonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = Integer.parseInt(et_jump.getText().toString());
                if (page> pages.size()-1){
                    page = pages.size()-1;
                    reader.setText(pages.get(page));
                }
                else if (page<0){
                    page = 0;
                    reader.setText(pages.get(0));
                }
                else {
                    reader.setText(pages.get(page));
                }
                et_jump.setText(page+"");
                //do preferences bit here
                editor.putInt(bookNumber,page);
                editor.commit();
            }
        });
        //font size
        ButtonFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontSize = Float.parseFloat(et_font.getText().toString());

                reader.setTextSize(fontSize);

                //do preferences bit here
                editor.putFloat(bookNumber+"Font",fontSize);
                editor.commit();
            }
        });





    }

}
