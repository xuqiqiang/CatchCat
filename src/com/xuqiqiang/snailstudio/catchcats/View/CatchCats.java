package com.xuqiqiang.snailstudio.catchcats.View;

import android.graphics.Color;
import android.view.MotionEvent;

import com.xuqiqiang.snailstudio.catchcats.Tool.Drawer;
import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.game.Map;

public class CatchCats {

    public static int status;
    public static final int STARTING = 0, WIN = 1, LOST = 2;

    private Map map;

    public CatchCats() {
        map = new Map();
        status = STARTING;
    }

    public void onGameTouch(MotionEvent event) {

        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;

        if (status == STARTING) {
            map.onTouch(event);
        }
    }

    public void drawGame() {
        Drawer.DrawRect(0, 0, GameManager.W * 100, GameManager.H * 100,
                Color.WHITE);
        map.show();
    }

    public void logic() {
        map.logic();
    }

}