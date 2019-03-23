package com.example.myplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.provider.MediaStore;

public class Music {

    public String title;
    public String artist;
    public int length;
    public String path;
    public int size;

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public static boolean sSwitch = false;

    public static void InitMusic(String local){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(local);
            mediaPlayer.prepare();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void PlayMusic(){
        mediaPlayer.start();
    }

    public static void PauseMusic(){
        mediaPlayer.pause();
    }

    public static void Destroy(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
