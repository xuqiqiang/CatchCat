package com.xuqiqiang.snailstudio.catchcats.game;

import com.xuqiqiang.snailstudio.catchcats.Tool.Drawer;
import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.utils.MathUtils;

public class Cat {

    public int pos_i, pos_j, pre_pos_i, pre_pos_j;
    private double pos_x, pos_y, width, height, speed;
    private int direct;
    private double des_x, des_y, angle;
    public boolean isMove;
    private int frame;

    public Cat(int i, int j) {
        this.pos_i = i;
        this.pos_j = j;
        pre_pos_i = i;
        pre_pos_j = j;

        pos_x = Map.getX(i, j);
        pos_y = Map.getY(i);

        width = Map.space * 2;
        height = width;

        speed = Map.space / 6;
        direct = 2;
        isMove = false;
        frame = 0;
    }

    public void setMove() {
        int k, new_pos_i, new_pos_j;
        for (k = 0; k < 6; k++) {
            new_pos_i = pre_pos_i + Map.amend[k][0];
            new_pos_j = pre_pos_j + Map.amend[k][1] + pre_pos_i % 2 * ((k / 2 + 1) % 2);
            if (new_pos_i == pos_i && new_pos_j == pos_j) {
                if (k == 0)
                    direct = 4;
                else if (k == 1)
                    direct = 1;
                else if (k == 2)
                    direct = 3;
                else if (k == 3)
                    direct = 0;
                else if (k == 4)
                    direct = 5;
                else
                    direct = 2;
            }
        }
        des_x = Map.getX(pos_i, pos_j);
        des_y = Map.getY(pos_i);
        angle = MathUtils.getRad(pos_x, pos_y, des_x, des_y);
        isMove = true;
    }

    public void move() {
        if (isMove) {
            if (!MathUtils.arrive(pos_x, pos_y, des_x, des_y, speed)
                    || !Map.checkInMap(pos_i, pos_j)) {
                pos_x += Math.cos(angle) * speed;
                pos_y += Math.sin(angle) * speed;
                frame = (frame + 1) % 6;
            } else {
                pos_x = des_x;
                pos_y = des_y;
                pre_pos_i = pos_i;
                pre_pos_j = pos_j;
                isMove = false;
                frame = 0;
            }
        }
    }

    public void show() {
        Drawer.DrawImage(GameManager.res.catBmp[direct % 3], 6, frame, pos_x
                - Map.space * 0.93, pos_y - Map.space * 1.28, width, height, direct > 2);
    }
}