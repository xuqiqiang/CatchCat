package com.xuqiqiang.snailstudio.catchcats.game;

import android.graphics.Color;
import android.view.MotionEvent;

import com.xuqiqiang.snailstudio.catchcats.Tool.Drawer;

public class Station {

    private double x, y, radius;
    public boolean selected;

    public Station(double x, double y, int map) {
        this.x = x;
        this.y = y;
        radius = Map.space * 0.44;
        selected = false;
        if (map == Map.OBSTACLE)
            selected = true;
    }

    public boolean onTouch(MotionEvent event) {
        if (selected)
            return false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double X = event.getX();
            double Y = event.getY();

            if (Math.sqrt((X - x) * (X - x) + (Y - y) * (Y - y)) <= radius) {
                return true;
            }
        }
        return false;
    }

    public void show(int status) {
        int color = 0;
        if (status == Map.OBSTACLE)
            color = Color.BLUE;
        else
            color = Color.GREEN;
        Drawer.DrawCircle(x, y, radius, color);
    }
}