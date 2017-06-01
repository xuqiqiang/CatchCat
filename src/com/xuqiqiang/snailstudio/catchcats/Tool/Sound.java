package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.xuqiqiang.snailstudio.catchcats.R;

public class Sound {

    public static boolean isPlay;
    private SoundPool sp;
    public static int sound_touched, sound_catched, sound_button;

    public Sound(Context context) {
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        sound_touched = sp.load(context, R.raw.touched, 1);
        sound_catched = sp.load(context, R.raw.catched, 1);
        sound_button = sp.load(context, R.raw.button, 1);
        isPlay = true;
    }

    public void play(int sound) {
        if (isPlay)
            sp.play(sound, 1f, 1f, 0, 0, 1);
    }
}