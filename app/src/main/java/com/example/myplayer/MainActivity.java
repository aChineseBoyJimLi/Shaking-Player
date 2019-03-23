package com.example.myplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private boolean mSwitch = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    @Override
    protected void onDestroy(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        super.onDestroy();
    }

    public void PlayMusic(View view){
        mediaPlayer = MediaPlayer.create(this, R.raw.happy_tree_friends);

        if(!mSwitch){
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.pausebtn);
            mSwitch = true;
            mediaPlayer.pause();
        }
        else{
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.playbtn);
            mSwitch = false;
            mediaPlayer.start();
        }
    }

    public void OpenList(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void ToSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
