package com.example.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetch  {

    protected String fetchItem( String str_url )  {
        try {
            // assemble the string and the search request
            StringBuilder response = new StringBuilder();
            URL url = new URL(str_url);

            // make the connection
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            // did it do ok?
            //if ( httpconn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    // have more data
                    response.append(strLine);
                    response.append("\n");
                }
                input.close();

                return response.toString();
           // }
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch ( IOException e ) {
            return e.getMessage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
