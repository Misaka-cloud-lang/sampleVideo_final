package com.example.samplevideo.extensions;

public interface OnPlaybackListener {
    void onPlaybackStarted();
    void onPlaybackPaused(long playedDuration);
    void onPlaybackEnded(long playedDuration);
}
