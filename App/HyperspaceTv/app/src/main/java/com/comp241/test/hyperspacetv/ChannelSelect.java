package com.comp241.test.hyperspacetv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.io.*;

public class ChannelSelect extends AppCompatActivity {

    int channelNumber;
    EditText txtChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
        txtChannel = (EditText)findViewById(R.id.txtChannel);
    }

    public void onConfirmClick(View v) {
        String channelTxt = txtChannel.getText().toString();
        if (channelTxt.length() == 0) {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.makeText(ChannelSelect.this, "Enter Channel Number", toast.LENGTH_SHORT).show();
            return;
        }
        try {
            channelNumber = Integer.parseInt(channelTxt);
            new Client().execute();
        }catch (Exception e) {
            return;
        }
    }

    public void toastMe(String s){
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.makeText(ChannelSelect.this, s, toast.LENGTH_SHORT).show();
    }

    private class Client extends AsyncTask<String, String, String> {

        String fromServer;
        Socket socc;
        PrintWriter out;
        BufferedReader in;

        @Override
        protected String doInBackground(String... params) {
            String received = "";

            try{
                socc = new Socket("f.l0.nz", 1234);
                out = new PrintWriter(socc.getOutputStream(), true);
                out.println("" + channelNumber);
                Log.w("Test", "Sent channel");
                in = new BufferedReader(new InputStreamReader(socc.getInputStream()));
                Log.w("Test", "Start To Read");
                while((fromServer = in.readLine()) != null){
                    Log.w("word", "Still going");
                    received += " " + fromServer;
                }
                Log.w("Test", "Got Here");
                in.close();
            } catch (UnknownHostException e) {
                Log.w("error", "Unknown host");
            } catch (IOException e) {
                Log.w("error", "IO Exception for host");
            } catch (Exception e) {
                Log.w("error", "Other");
            }

            return received;
        }

        @Override
        protected void onPostExecute(String result) {
            toastMe(result);
        }
    }
}

