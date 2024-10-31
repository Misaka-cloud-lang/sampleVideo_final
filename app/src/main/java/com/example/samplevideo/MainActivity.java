package com.example.samplevideo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mainLv;
    String url = "http://baobab.kaiyanapp.com/api/v4/tabs/selected?udid=11111&vc=168&vn=3.3.1&deviceModel=Huawei6&first_channel=eyepetizer_baidu_market&system_version_code=20";
    List<VideoBean.ItemListBean> mDatas;
    private VideoAdapter adapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                String json = (String) msg.obj;
                VideoBean videoBean = new Gson().fromJson(json,VideoBean.class);
                List<VideoBean.ItemListBean> itemList = videoBean.getItemList();
                for(int i=0; i<itemList.size();i++){
                    VideoBean.ItemListBean listBean = itemList.get(i);
                    if(listBean.getType().equals("video")){
                        mDatas.add(listBean);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("videos");
        mainLv = findViewById(R.id.main_lv);

        mDatas =new ArrayList<>();

        adapter = new VideoAdapter(this,mDatas);
        mainLv.setAdapter(adapter);

        loadData();
    }

    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonContent = HttpUtils.getJsonContent(url);
                Message message = new Message();
                message.what = 1;
                message.obj = jsonContent;
                handler.sendMessage(message);
            }
        }).start();
    }
}