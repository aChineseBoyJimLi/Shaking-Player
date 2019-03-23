package com.example.myplayer;


import android.media.MediaPlayer;

public class Music {

    public static String title;
    public static String artist;
    public static int length;
    public static String path;
    public static int size;

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public static boolean sSwitch = false;

    public static void InitMusic(String t,String a,int l,String local){
        title = t;
        if(a==null){
            a = "未知艺术家";
        }
        else{
            a = artist;
        }
        length = l;
        path = local;
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
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
