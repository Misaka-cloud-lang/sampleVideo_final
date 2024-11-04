package com.example.samplevideo.extensions;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.samplevideo.VideoAdapter;

import cn.jzvd.JzvdStd;

public class GFVD extends JzvdStd {
	final String TAG = "GOLDFISH";
	private VideoAdapter adapter;
	private int position = -1;

	public GFVD(Context context) {
		super(context);
	}

	public GFVD(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VideoAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(VideoAdapter adapter) {
		this.adapter = adapter;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
//		int i = v.getId();
//		if (i == R.id.poster) {
//			Log.i(TAG, "onClick: poster");
//		} else if (i == R.id.surface_container) {
//			Log.i(TAG, "onClick: surface_container");
//			if (clarityPopWindow != null) {
//				Log.i(TAG, "onClick: clarityPopWindow");
//			}
//		} else if (i == R.id.back) {
//			Log.i(TAG, "onClick: back");
//		} else if (i == R.id.back_tiny) {
//			Log.i(TAG, "onClick: back_tiny");
//		} else if (i == R.id.clarity) {
//			Log.i(TAG, "onClick: clarity");
//		} else if (i == R.id.retry_btn) {
//			Log.i(TAG, "onClick: retry_btn");
//		}
	}

	@Override
	public void onStatePreparingPlaying() {
		super.onStatePreparingPlaying();
		Log.i(TAG, position + "onStatePreparingPlaying: ");
	}

	@Override
	public void onStatePreparing() {
		super.onStatePreparing();
		Log.i(TAG, position + "onStatePreparing: ");
	}

	@Override
	public void onStatePlaying() {
		super.onStatePlaying();
		Log.i(TAG, position + "onStatePlaying: ");
	}

	@Override
	public void startVideo() {
		startVideo(false);
	}

	public void startVideo(boolean notified) {
		super.startVideo();
		Log.i(TAG, "startVideo: "+position);
		if (notified) return;
		Log.i(TAG,
			"startVideo: notifying neighbors from " + position);
		adapter.NotifyNeighbors(this);
	}
}
