package com.example.dell.driverapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dell on 6/30/201
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx ;
    AlertDialog alertDialog ;


    BackgroundTask(Context ctx)
    {
        this.ctx = ctx ;
    }
    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("LogIn Information");
    }

    @Override
    protected String doInBackground(String... params) {
        String method =params[0];
        if (method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            String login_url  = "http://192.168.0.114/fadi/driver/login.php";
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                try {
                    httpURLConnection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream o8 =httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(o8,"UTF-8"));

                String data = URLEncoder.encode("login_name","UTF-8")+"_"+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"_"+URLEncoder.encode(login_pass,"UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                o8.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String response = "";
                String line = "";

                while ((line=bufferedReader.readLine())!=null)
                {
                    response +=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
