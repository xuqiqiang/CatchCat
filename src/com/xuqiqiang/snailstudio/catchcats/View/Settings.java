package com.xuqiqiang.snailstudio.catchcats.View;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.xuqiqiang.snailstudio.catchcats.Tool.Button;
import com.xuqiqiang.snailstudio.catchcats.Tool.Drawer;
import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.Tool.Resourse;
import com.xuqiqiang.snailstudio.catchcats.Tool.Sound;

public class Settings {

    private Button button;

    public Settings() {
        button = new Button(GameManager.W * 60, GameManager.H * 40,
                GameManager.W * 35, 0, new Bitmap[] {
                        GameManager.res.pressBmp[0],
                        GameManager.res.pressBmp[3] }, "Yes");
    }

    public void onTouch(MotionEvent event) {
        if (button.onTouch(event) == 1) {
            if (Sound.isPlay) {
                button.text = "NO";
            } else {
                button.text = "YES";
            }
            Sound.isPlay = !Sound.isPlay;
        }
    }

    public void drawSettings() {
        Drawer.DrawImage(GameManager.res.mainbgBmp, 0, 0, GameManager.W * 100,
                GameManager.H * 100);
        Drawer.DrawImage(GameManager.res.soundBmp, GameManager.W * 10,
                GameManager.H * 40, GameManager.W * 40,
                Resourse.getH(GameManager.res.soundBmp, GameManager.W * 40));
        button.show();
    }
}