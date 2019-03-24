package com.example.myplayer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        listView = findViewById(R.id.my_list);


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



}
