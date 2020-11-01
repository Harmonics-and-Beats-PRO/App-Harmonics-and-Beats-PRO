package com.app.hnbpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.hnbpro.Videos.video1;

public class Video_Player extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__player);
    }
    public void abrirvideo1 (View view){

        Intent intent = new Intent (this, video1.class);
        startActivity(intent);
    }
}