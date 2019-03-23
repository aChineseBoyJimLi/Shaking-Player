package com.example.myplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private boolean mSwitch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Music.InitMusic("离思五首其四",null,1235,"/storage/emulated/0/Download/lisiwushouqisi.mp3");
        Music.sSwitch = false;
        CheckPlayStatus();
    }

    @Override
    protected void onResume(){
        CheckPlayStatus();
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        Music.Destroy();
        super.onDestroy();
    }

    public void PlayMusic(View view){
        if(Music.sSwitch){
            Music.sSwitch = false;
            CheckPlayStatus();
        }
        else{
            Music.sSwitch = true;
            CheckPlayStatus();
        }
    }

    public void CheckPlayStatus(){
        Music.PlayMusic();
        if(Music.sSwitch){
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.playbtn);
            Music.PlayMusic();
        }
        else{
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.pausebtn);
            Music.PauseMusic();
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
