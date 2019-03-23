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

    public static MediaPlayer mediaPlayer;

    public static boolean sSwitch = false;

    public static void PlayMusic(String local){
        try{
            mediaPlayer.setDataSource(local);
            mediaPlayer.start();
            if(sSwitch){
                // 播放
               sSwitch = false;
            }
            else{
                // 暂停
                mediaPlayer.pause();
                sSwitch = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void Destroy(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
