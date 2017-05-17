package com.comp241.test.hyperspacetv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class ChannelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_select);
    }

    public void onClickOne(View view){
        try {
            ClientApp.main(new String[] {"241","1"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickTick(View view){
        setContentView(R.layout.activity_show_data);
    }

    public void onClickChannel(View view){
        setContentView(R.layout.activity_channel_select);
    }
}