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

        Music.InitMusic("/storage/emulated/0/Download/lisiwushouqisi.mp3");
        Music.PlayMusic();
        Music.PauseMusic();
    }

    @Override
    protected void onDestroy(){
        Music.Destroy();
        super.onDestroy();
    }

    public void PlayMusic(View view){
        Music.PlayMusic();
        if(mSwitch){
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.playbtn);
            Music.PlayMusic();
            mSwitch = false;
        }
        else{
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.pausebtn);
            Music.PauseMusic();
            mSwitch = true;
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
