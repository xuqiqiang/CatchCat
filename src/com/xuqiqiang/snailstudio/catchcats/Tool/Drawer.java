package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

public class Drawer {

    public static void DrawImage(Bitmap bmp, int n, int m, double x, double y,
            double w, double h, boolean isTurn) {

        double width = (double) bmp.getWidth() / (double) n;
        Matrix matrix = new Matrix();
        Bitmap newBitmap = Bitmap.createBitmap(bmp, (int) (m * width), 0,
                (int) width, bmp.getHeight(), matrix, true);
        if (isTurn) {
            matrix.postScale(-1, 1);
            newBitmap = Bitmap.createBitmap(newBitmap, 0, 0,
                    newBitmap.getWidth(), newBitmap.getHeight(), matrix, true);
        }
        Rect r1 = new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight());
        RectF r2 = new RectF((float) x, (float) y, (float) (x + w),
                (float) (y + h));
        GameManager.canvas.drawBitmap(newBitmap, r1, r2, GameManager.paint);
    }

    public static void DrawImage(Bitmap bmp, double x, double y, double w,
            double h) {
        Rect r1 = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        RectF r2 = new RectF((float) x, (float) y, (float) (x + w),
                (float) (y + h));
        GameManager.canvas.drawBitmap(bmp, r1, r2, GameManager.paint);
    }

    public static void Drawtext(String text, double x, double y, double size,
            int color) {
        Typeface serif_italic = Typeface
                .create(Typeface.SERIF, Typeface.ITALIC);

        GameManager.paint.setTypeface(serif_italic);

        GameManager.paint.setTextSize((float) size);
        GameManager.paint.setColor(color);
        GameManager.canvas.drawText(text, (float) x, (float) y,
                GameManager.paint);
    }

    public static void DrawRect(double x, double y, double w, double h,
            int color) {
        GameManager.paint.setColor(color);
        GameManager.canvas.drawRect(new RectF((float) x, (float) y,
                (float) (x + w), (float) (y + h)), GameManager.paint);
    }

    public static void DrawRect(double x, double y, double w, double h,
            int color, int alpha) {
        GameManager.paint.setColor(color);
        GameManager.paint.setAlpha(alpha);
        GameManager.canvas.drawRect(new RectF((float) x, (float) y,
                (float) (x + w), (float) (y + h)), GameManager.paint);
        GameManager.paint.setAlpha(255);
    }

    public static void DrawCircle(double x, double y, double radius, int color) {
        GameManager.paint.setColor(color);
        GameManager.canvas.drawCircle((float) x, (float) y, (float) radius,
                GameManager.paint);
    }

}