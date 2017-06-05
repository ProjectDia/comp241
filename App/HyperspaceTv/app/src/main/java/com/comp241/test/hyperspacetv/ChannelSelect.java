package com.comp241.test.hyperspacetv;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.io.*;

public class ChannelSelect extends AppCompatActivity {

    int channelNumber;
    EditText txtChannel;
    String channelTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        txtChannel = (EditText)findViewById(R.id.txtChannel);
    }

    public void onChannelSelectClick(View v) {
        setContentView(R.layout.activity_channel_select);
    }

    public void onConfirmClick(View v) {
        channelTxt = txtChannel.getText().toString();
        txtChannel.setText("");
        if (channelTxt.length() == 0) {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.makeText(ChannelSelect.this, "Enter Channel Number", toast.LENGTH_SHORT).show();
            return;
        }
        else {
            try {
                Log.w("WHATS UP", "channel " + channelTxt);
                channelNumber = Integer.parseInt(channelTxt);
                new Client().execute("" + channelNumber);
            } catch (Exception e) {
                return;
            }
        }
    }

    public void showInfo(String s){
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.makeText(ChannelSelect.this, s, toast.LENGTH_SHORT).show();
        channelTxt = "";
        channelNumber = 0;
        Log.w("WHATS UP", "channel " + channelTxt);
        Log.w("WHATS UP", "" + channelNumber);
        setContentView(R.layout.activity_channel_data);
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
                Log.w("WHATS UP", "channel " + params[0]);
                out.println(params[0]);
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
            showInfo(result);
        }
    }
}

