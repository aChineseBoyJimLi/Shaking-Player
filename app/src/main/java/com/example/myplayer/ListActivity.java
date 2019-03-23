package com.example.myplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data;
    private List<Music> musicList;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        listView = findViewById(R.id.my_list);

        getMusic();

        if(!data.isEmpty()){
            simpleAdapter = new SimpleAdapter(ListActivity.this, data, R.layout.list_item,
                    new String[]{"thumb", "music", "artist", "time"}, new int[] {R.id.thumb, R.id.music
                    ,R.id.artist,R.id.total_time});

            listView.setAdapter(simpleAdapter);
        }

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

    }



    private void getMusic(){
        data = new ArrayList<>();
        ContentResolver resolver = getApplicationContext().getContentResolver();
        //内容提供者访问MediaStore.Audio.Media.EXTERNAL_CONTENT_URI 这个地址获取系统音乐文件详细信息。
        Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        c.moveToFirst();
        while (c.moveToNext())
        {
            Music m = new Music();
            m.title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            m.artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            m.path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            m.length = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            m.size = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//            musicList.add(m);
            Map<String, Object> map = new HashMap<>();
            map.put("thumb", R.drawable.playbtn);
            map.put("music", m.title);
            map.put("artist", m.artist);
            map.put("path", m.path);
            map.put("time", m.length);
            data.add(map);
        }
        c.close();
    }



}
