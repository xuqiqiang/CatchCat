package com.xuqiqiang.snailstudio.catchcats.Tool;

public class Point {

    public int i, j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public boolean equal(Point p) {
        return (i == p.i && j == p.j);
    }
}