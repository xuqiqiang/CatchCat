package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;

public class Button {

    public int isPress;
    private Bitmap bmp[];
    public String text;
    public double x, y, w, h;

    public Button(double xx, double yy, double ww, double hh, Bitmap bmp[],
            String text) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;

        this.bmp = bmp;
        this.text = text;

        if (w == 0)
            w = Resourse.getW(bmp[0], h);
        if (h == 0)
            h = Resourse.getH(bmp[0], w);

        isPress = 0;

    }

    public int onTouch(MotionEvent event) {

        double X = event.getX();
        double Y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (X >= x && X <= x + w && Y >= y && Y <= y + h) {
                isPress = 1;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isPress == 1) {
                isPress = 0;
                if (X >= x && X <= x + w && Y >= y && Y <= y + h) {
                    GameManager.sound.play(Sound.sound_button);
                    return 1;
                }
            }
        }
        return 0;
    }

    public void show() {
        Drawer.DrawImage(bmp[isPress], x, y, w, h);
        Drawer.Drawtext(text, x + w / 2 - 15 * GameManager.W * 5 / 4
                + GameManager.W * 5, y + h / 2 + GameManager.H * 1.9,
                GameManager.W * 5, Color.BLACK);
    }

}