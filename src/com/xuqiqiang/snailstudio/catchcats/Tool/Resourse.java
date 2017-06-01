package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xuqiqiang.snailstudio.catchcats.R;

public class Resourse {

    public Bitmap mainbgBmp, titleBmp, pressBmp[], helpBmp, soundBmp, catBmp[];

    public Resourse(Context context) {

        mainbgBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.mainbg);
        titleBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.title);
        int i;
        pressBmp = new Bitmap[4];
        for (i = 0; i < 4; i++) {
            pressBmp[i] = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.button_1 + i);
        }

        helpBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.help);
        soundBmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sound);

        catBmp = new Bitmap[3];

        catBmp[0] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.right);
        catBmp[1] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.right_up);
        catBmp[2] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.right_down);
    }

    public static double getW(Bitmap bmp, double h) {
        return h / (double) bmp.getHeight() * (double) bmp.getWidth();
    }

    public static double getH(Bitmap bmp, double w) {
        return w / (double) bmp.getWidth() * (double) bmp.getHeight();
    }
}