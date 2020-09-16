package com.example.finalproject;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReadWrite {
    //method used to write to file using outputStream
     void Writer(String data, String FileName, Context context) {
         FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(FileName,Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //method used to read to file using inputStream
    String Reader(String FileName, Context context){
        FileInputStream inputStream = null;
        String Text = "";
        StringBuilder sb = null;
        try{
            //inputStream = context.openFileInput(FileName);
            inputStream = new FileInputStream(new File(FileName));
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader buffRead = new BufferedReader(reader);
            sb = new StringBuilder();

            while ((Text = buffRead.readLine())!= null){
                sb.append(Text);
                sb.append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


}
