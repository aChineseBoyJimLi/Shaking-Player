package com.example.myplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private SimpleAdapter simpleAdapter;
//    public static List<Map<String, Object>> data;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        listView = findViewById(R.id.my_list);

//        getMusic();

        if(!MainActivity.data.isEmpty()){
            simpleAdapter = new SimpleAdapter(ListActivity.this, MainActivity.data, R.layout.list_item,
                    new String[]{"thumb", "music", "artist", "time"}, new int[] {R.id.thumb, R.id.music
                    ,R.id.artist,R.id.total_time});

            listView.setAdapter(simpleAdapter);
        }

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        try {
            String local = MainActivity.data.get(position).get("path").toString();
            String title = MainActivity.data.get(position).get("music").toString();
            String artist = MainActivity.data.get(position).get("artist").toString();
            int length = Integer.parseInt(MainActivity.data.get(position).get("time").toString());
            Music.InitMusic(title,artist,length,local,position);
            Music.sSwitch = true;
            Music.PlayMusic();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "无法播放音频", Toast.LENGTH_SHORT).show();
        }

    }



//    public void getMusic(){
//        data = new ArrayList<>();
//        ContentResolver resolver = getApplicationContext().getContentResolver();
//        //内容提供者访问MediaStore.Audio.Media.EXTERNAL_CONTENT_URI 这个地址获取系统音乐文件详细信息。
//        Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        c.moveToFirst();
//        while (c.moveToNext())
//        {
//            String title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//            String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//            String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//            int length = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//            int size = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//            Map<String, Object> map = new HashMap<>();
//            map.put("thumb", R.drawable.playbtn);
//            map.put("music", title);
//            map.put("artist", artist);
//            map.put("path", path);
//            map.put("time", length);
//            map.put("size", size);
//            data.add(map);
//        }
//        c.close();
//    }



}
