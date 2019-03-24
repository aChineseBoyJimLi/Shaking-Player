package com.example.myplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar seekBar;
    private SeekBar volumneBar;
    AudioManager audioManager;
    private Timer timer;
    public static List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getMusic();

        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        volumneBar = findViewById(R.id.volumne);
        volumneBar.setOnSeekBarChangeListener(this);





        String local = MainActivity.data.get(0).get("path").toString();
        String title = MainActivity.data.get(0).get("music").toString();
        String artist = MainActivity.data.get(0).get("artist").toString();
        int length = Integer.parseInt(MainActivity.data.get(0).get("time").toString());

        Music.InitMusic(title,artist,length,local, 0);

        Music.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                PlayNext();
            }
        });
        Music.sSwitch = false;
        CheckPlayStatus();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(timer!=null)
        {
            timer.cancel();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar s) {
        float p = (float)seekBar.getProgress() / 100.0f;
        p = p * Music.length;
        Music.mediaPlayer.seekTo((int)p);

        float v = (float)volumneBar.getProgress() / 100.0f;
        v = v * audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)v, AudioManager.FLAG_PLAY_SOUND);
        getProgress();
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

    public void PlayNextBtn(View view){
        PlayNext();
    }

    public void PlayPreviousBtn(View view){
        PlayPrevious();
    }

    public void getMusic(){
        data = new ArrayList<>();
        ContentResolver resolver = getApplicationContext().getContentResolver();
        //内容提供者访问MediaStore.Audio.Media.EXTERNAL_CONTENT_URI 这个地址获取系统音乐文件详细信息。
        Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        c.moveToFirst();
        while (c.moveToNext())
        {
            String title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            int length = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            int size = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            Map<String, Object> map = new HashMap<>();
            map.put("thumb", R.drawable.playbtn);
            map.put("music", title);
            map.put("artist", artist);
            map.put("path", path);
            map.put("time", length);
            map.put("size", size);
            data.add(map);
        }
        c.close();
    }

    private void PlayNext(){
        if(Music.currIndex == data.size()-1){
            String local = data.get(0).get("path").toString();
            String title = data.get(0).get("music").toString();
            String artist = data.get(0).get("artist").toString();
            int length = Integer.parseInt(data.get(0).get("time").toString());
            Music.InitMusic(title,artist,length,local,0);
            Music.sSwitch = true;
            Music.PlayMusic();
        }
        else{
            int nextIndex = Music.currIndex + 1;
            String local = data.get(nextIndex).get("path").toString();
            String title = data.get(nextIndex).get("music").toString();
            String artist = data.get(nextIndex).get("artist").toString();
            int length = Integer.parseInt(data.get(nextIndex).get("time").toString());
            Music.InitMusic(title,artist,length,local,nextIndex);
            Music.sSwitch = true;
            Music.PlayMusic();
        }
        CheckPlayStatus();
    }

    private void PlayPrevious(){
        if(Music.currIndex == 0){
            int nextIndex = data.size()-1;
            String local = data.get(nextIndex).get("path").toString();
            String title = data.get(nextIndex).get("music").toString();
            String artist = data.get(nextIndex).get("artist").toString();
            int length = Integer.parseInt(data.get(nextIndex).get("time").toString());
            Music.InitMusic(title,artist,length,local,nextIndex);
            Music.sSwitch = true;
            Music.PlayMusic();
        }
        else{
            int nextIndex = Music.currIndex - 1;
            String local = data.get(nextIndex).get("path").toString();
            String title = data.get(nextIndex).get("music").toString();
            String artist = data.get(nextIndex).get("artist").toString();
            int length = Integer.parseInt(data.get(nextIndex).get("time").toString());
            Music.InitMusic(title,artist,length,local,nextIndex);
            Music.sSwitch = true;
            Music.PlayMusic();

        }
        CheckPlayStatus();
    }

    public void CheckPlayStatus(){
        Music.PlayMusic();
        if(Music.mediaPlayer != null && Music.sSwitch){
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.playbtn);
            Music.PlayMusic();
        }
        else{
            ((ImageButton)findViewById(R.id.play_btn)).setImageResource(R.drawable.pausebtn);
            Music.PauseMusic();
        }
        ((TextView)findViewById(R.id.music)).setText(Music.title);
        ((TextView)findViewById(R.id.author)).setText(Music.artist);
        int len = Music.length / 1000;
        int min = len / 60;
        int sec = len % 60;
        String l = min + ":" + sec;
        ((TextView)findViewById(R.id.length)).setText(l);

        getProgress();
    }

    private void getProgress(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                float p = (float)Music.mediaPlayer.getCurrentPosition() / (float)Music.length * 100.0f;
                seekBar.setProgress((int)p);

                int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = (float)currentVolume / (float)maxVolume * 100.0f;
                volumneBar.setProgress((int)volume);
            }
        },0,200);
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
