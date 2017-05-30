package com.comp241.test.hyperspacetv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class ChannelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
    }

    public void onClickTick(View view){
        EditText channelNum = (EditText)findViewById(R.id.enterChannel);
        String strChannelNumber = channelNum.getText().toString();
        if (strChannelNumber.equals(null)) return;
        if (strChannelNumber.length() < 3) return;
        int channel = Integer.parseInt(strChannelNumber);
        setContentView(R.layout.activity_show_data);
        TextView txt = (TextView)findViewById(R.id.channelNum);
        txt.setText("" + channel);
    }

    public void onClickChannel(View view){
        setContentView(R.layout.activity_channel_select);
    }
}