package com.example.finalproject;

import java.util.ArrayList;

public class PageMaker {

    String book = "";

    public PageMaker(String data){

         book = data;

    }
    public ArrayList getPages(){
        ArrayList<String> Pages = new ArrayList<>();

        if (book.length()<3000){
            Pages.add(book);
        }
        while (book.length()>=3000){
            Pages.add(book.substring(0,3000));
            book = book.substring(3000);
        }
        Pages.add(book);
        return Pages;
    }
}
