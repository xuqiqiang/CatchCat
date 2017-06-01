package com.xuqiqiang.snailstudio.catchcats.Tool;

import java.util.ArrayList;

public class Queue {

    private ArrayList<Point> point;

    public Queue() {
        point = new ArrayList<Point>();
    }

    public boolean isEmpty() {
        return (point.size() == 0);
    }

    public void enter(Point p) {
        point.add(p);
    }

    public Point out() {
        if (isEmpty())
            return null;
        Point p = point.get(0);
        point.remove(0);
        return p;
    }
}