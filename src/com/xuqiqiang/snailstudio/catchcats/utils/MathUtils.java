package com.xuqiqiang.snailstudio.catchcats.utils;

public class MathUtils {

    public static double getRad(double px1, double py1, double px2, double py2) {
        double x = px2 - px1;
        double y = py1 - py2;
        double Hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double cosAngle = x / Hypotenuse;
        double rad = (double) Math.acos(cosAngle);
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    public static boolean arrive(double pos_x, double pos_y, double des_x,
            double dex_y, double speed) {
        double px = des_x - pos_x;
        double py = dex_y - pos_y;
        double radius = speed / 2;
        return px * px + py * py <= radius * radius;
    }

}