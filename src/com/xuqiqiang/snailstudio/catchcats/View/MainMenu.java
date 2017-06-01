package com.xuqiqiang.snailstudio.catchcats.View;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.xuqiqiang.snailstudio.catchcats.Tool.Button;
import com.xuqiqiang.snailstudio.catchcats.Tool.Drawer;
import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.Tool.Resourse;

public class MainMenu {

    private int buttonSum;
    private Button button[];

    public MainMenu() {
        String text[] = new String[] { "START", "SETTINGS", "HELP" };
        buttonSum = 3;
        button = new Button[buttonSum];
        for (int i = 0; i < buttonSum; i++)
            button[i] = new Button(GameManager.W * 40 + GameManager.W * 10 * i,
                    GameManager.H * 40 + GameManager.H * 20 * i,
                    GameManager.W * 35, 0, new Bitmap[] {
                            GameManager.res.pressBmp[i],
                            GameManager.res.pressBmp[3] }, text[i]);
    }

    public void onTouch(MotionEvent event) {
        for (int i = 0; i < buttonSum; i++)
            if (button[i].onTouch(event) == 1) {
                if (i == 0) {
                    GameManager.setScene(GameManager.GAME, 0,
                            GameManager.SWITCHER_QUICK);
                } else if (i == 1) {
                    GameManager.setScene(GameManager.SET, 0,
                            GameManager.SWITCHER_QUICK);
                } else if (i == 2) {
                    GameManager.setScene(GameManager.HELP, 0,
                            GameManager.SWITCHER_QUICK);
                }
            }
    }

    public void drawMenu() {

        Drawer.DrawImage(GameManager.res.mainbgBmp, 0, 0, GameManager.W * 100,
                GameManager.H * 100);
        Drawer.DrawImage(GameManager.res.titleBmp, GameManager.W * 20,
                GameManager.H * 20, GameManager.W * 60,
                Resourse.getH(GameManager.res.titleBmp, GameManager.W * 60));
        int i;
        for (i = 0; i < buttonSum; i++)
            button[i].show();
    }
}