package com.example.samplevideo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	ListView mainListView;
	String url =
		"http://baobab.kaiyanapp.com/api/v4/tabs/selected?udid=11111" + "&vc" + "=168&vn=3.3.1&deviceModel=Huawei6&first_channel" + "=eyepetizer_baidu_market&system_version_code=20";
	List<VideoBean.ItemListBean> mDatas;
	List<JSONObject> movie_infos;
	private VideoAdapter adapter;
	Handler handler = new Handler(Looper.getMainLooper(), msg -> {
		MainActivity.this.handleMessage(msg);
		return true;
	});

	private static List<JSONObject> parseJSONMetaData(JSONObject object) throws JSONException {
		List<JSONObject> movie_infos = new ArrayList<>();
		JSONArray movie_infos_array = object.getJSONArray("itemList");
		for (int i = 0; i < movie_infos_array.length(); i++) {
			//一条电影的源数据
			JSONObject movie_info = movie_infos_array.getJSONObject(i);
			movie_infos.add(movie_info);
		}
		return movie_infos;
	}

	public void handleMessage(@NonNull Message msg) {
		if (msg.what == 1) {
			String json = (String) msg.obj;
			VideoBean videoBean = new Gson().fromJson(json, VideoBean.class);
			List<VideoBean.ItemListBean> itemList = videoBean.getItemList();
			for (int i = 0; i < itemList.size(); i++) {
				VideoBean.ItemListBean listBean = itemList.get(i);
				if (listBean.getType().equals("video")) {
					mDatas.add(listBean);
				}
			}

			//这一句一定得放在handler里面
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		movie_infos = new ArrayList<>();
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		setTitle("videos");
		mainListView = findViewById(R.id.main_lv);

		mDatas = new ArrayList<>();

		adapter = new VideoAdapter(this, mDatas);
		mainListView.setAdapter(adapter);

		loadAllMetaData();
	}

	private void loadAllMetaData() {

		new Thread(new VideoInfoParser()).start();
	}

	private class VideoInfoParser implements Runnable {
		/**
		 * json数据包含约24个电影的信息
		 */
		@Override
		public void run() {
			String jsonContent = HttpUtils.getJsonContent(url);
			try {
				MainActivity.this.movie_infos =
					parseJSONMetaData(new JSONObject(jsonContent));
			} catch (JSONException e) {
				Log.w(MainActivity.class.toString(), "in loadAllMetadata: " +
					"invalid str for jsonObject");
				return;
			}
			Message message = new Message();
			message.what = 1;
			message.obj = jsonContent;
			//将信息添加到处理队列
			handler.sendMessage(message);

		}
	}
}

