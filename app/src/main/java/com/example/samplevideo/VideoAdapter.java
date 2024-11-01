package com.example.samplevideo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplevideo.extensions.GFVD;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;


public class VideoAdapter extends BaseAdapter {
	final String TAG =
//		VideoAdapter.class.getSimpleName()
		"GOLDFISH";
	/**
	 * 点击一个视频的播放键时，前后多少个视频要开始加载<br/>
	 * 即：点击位置为{@code position}的视频，[{@code position-NEIGHBOR_FRONT},{@code
	 * position+NEIGHBOR_REAR}]区间内的视频都要预加载
	 */
	final int NEIGHBOR_FRONT = 2;
	final int NEIGHBOR_REAR = 2;
	private final List<VideoBean.ItemListBean> mDatas;
	//储存每个视频的缓冲进度
	private final Map<VideoBean.ItemListBean, Integer> cachedProgress;
	private final Map<Integer, VideoBean.ItemListBean> itemPositions;
	private final Map<Integer, GFVD> vdPositions;
	Context context;

	/**
	 * 构造方法
	 *
	 * @param context 上下文（一般就是窗口）
	 * @param mDatas  储存电影数据源的集合（可直接new一个空列表）
	 */
	public VideoAdapter(Context context, List<VideoBean.ItemListBean> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
		this.cachedProgress = new HashMap<>();
		this.itemPositions = new HashMap<>();
		this.vdPositions = new HashMap<>();
	}

	public void addItemListBean(VideoBean.ItemListBean itemListBean) {
		mDatas.add(itemListBean);
		cachedProgress.put(itemListBean, 0);
	}

	@Override
	public int getCount() {
		int count;
//		count=mDatas.size();
		count = cachedProgress.keySet().size();
		return count;
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewController controller;
		if (convertView == null) {
			convertView =
				LayoutInflater.from(context).inflate(R.layout.item_mainlistview, parent, false);
			controller = new ViewController(convertView, position);
			convertView.setTag(controller);
		} else {
			controller = (ViewController) convertView.getTag();
		}
//		Log.i(TAG, "-----------------------------" + convertView.getClass());
//		Log.i(TAG, "sending view in class" + convertView.getClass());
//		Log.i(TAG, "getView: parent class" + parent.getClass());
//		Log.i(TAG, "position: " + position);

		//加载视频下方注解文字
		VideoBean.ItemListBean.DataBean dataBean =
			mDatas.get(position).getData();
		VideoBean.ItemListBean.DataBean.AuthorBean author =
			dataBean.getAuthor();
		controller.nameTv.setText(author.getName());
		controller.descTv.setText(author.getDescription());
		String message = String.format("Loading author: %s __ %s",
			author.getName(), author.getDescription());
//		Log.i(TAG, message);

		//加载视频作者头像
		String iconURL = author.getIcon();
		if (!TextUtils.isEmpty(iconURL)) {
			Picasso.with(context).load(iconURL).into(controller.iconIv);
		}

		//播放网址
		String str_playUrl = dataBean.getPlayUrl();
		Log.i(TAG, "loading playUrl: " + str_playUrl);
		//视频标题
		String str_title = dataBean.getTitle();
//		Log.i(TAG, "loading title: " + str_title);
		controller.jzvdStd.setUp(str_playUrl, str_title,
			Jzvd.SCREEN_FULLSCREEN);

		//加载视频预览图片
		String thumbUrl = dataBean.getCover().getFeed();
//		Log.i(TAG, "loading thumbUrl: " + thumbUrl);
		Picasso.with(context).load(thumbUrl).into(controller.jzvdStd.posterImageView);
		controller.jzvdStd.positionInList = position;

		return convertView;
	}

	public void NotifyNeighbors(GFVD vd) {
		int position = vd.getPosition();
		for (int i = -NEIGHBOR_FRONT; i <= NEIGHBOR_REAR; i++) {
			int neighbor_position = position + i;
//			if (neighbor_position < 0) continue;
//			if (neighbor_position >= mDatas.size()) continue;
			GFVD gfvd = vdPositions.get(neighbor_position);
			if (gfvd == null) {
				Log.w(TAG,
					"NotifyNeighbors: position not found at " + neighbor_position);
				return;
			}
			gfvd.startVideo(true);
		}
	}

	/**
	 * 之前叫ViewHolder，现在叫ViewController
	 */
	class ViewController {
		GFVD jzvdStd;
		ImageView iconIv;
		TextView nameTv, descTv;

		public ViewController(View view, int count) {
			jzvdStd = view.findViewById(R.id.item_main_gfvd);
			iconIv = view.findViewById(R.id.item_main_iv);
			nameTv = view.findViewById(R.id.item_main_tv_name);
			descTv = view.findViewById(R.id.item_main_tv_des);
			jzvdStd.setAdapter(VideoAdapter.this);
			jzvdStd.setPosition(count);
			vdPositions.put(count, jzvdStd);

		}
	}
}
