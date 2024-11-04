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
	private OnPlaybackListener playbackListener;

	public void setOnPlaybackListener(OnPlaybackListener listener) {
		this.playbackListener = listener;
	}

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
		if (playbackListener != null) {
			playbackListener.onPlaybackStarted();
		}
	}
	@Override
	public void onStatePause() {
		super.onStatePause();
		Log.i(TAG, position + "onStatePause: ");
		if (playbackListener != null) {
			long playedDuration = getCurrentPositionWhenPlaying();
			Log.i(TAG, position + "onStatePause: playedDuration = " + playedDuration + " ms");
			playbackListener.onPlaybackPaused(playedDuration);
		}
	}

	@Override
	public void onStateAutoComplete() {
		super.onStateAutoComplete();
		Log.i(TAG, position + "onStateAutoComplete: ");
		if (playbackListener != null) {
			long playedDuration = getCurrentPositionWhenPlaying();
			Log.i(TAG, position + "onStateAutoComplete: playedDuration = " + playedDuration + " ms");
			playbackListener.onPlaybackEnded(playedDuration);
		}
	}

	@Override
	public void startVideo() {
		super.startVideo();
	}

	public void startVideo(boolean notified) {
//		super.startVideo();
		Log.i(TAG, "startVideo: "+position);
		if (notified) {
			registerWifiListener(super.getContext());
			return;
		}
		Log.i(TAG,
				"startVideo: notifying neighbors from " + position);
		adapter.NotifyNeighbors(this);
	}


}