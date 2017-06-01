package com.xuqiqiang.snailstudio.catchcats.game;

import android.view.MotionEvent;

import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.Tool.Point;
import com.xuqiqiang.snailstudio.catchcats.Tool.Queue;
import com.xuqiqiang.snailstudio.catchcats.Tool.Sound;
import com.xuqiqiang.snailstudio.catchcats.View.CatchCats;

public class Map {

    public static int row, col;
    public int map[][];
    public static final int EMPTY = 0, OBSTACLE = 1, PATH = 2;

    public Point[][] before;

    public static int amend[][];

    private Queue queue;

    private Station station[][];

    public static double space;

    private Cat cat;

    public Map() {
        row = 11;
        col = 11;

        space = GameManager.H * 10;

        cat = new Cat(4 + (int) (Math.random() * 3),
                4 + (int) (Math.random() * 3));

        initMap();

        amend = new int[][] { { -1, -1 }, { -1, 0 }, { 0, -1 }, { 0, 1 },
                { 1, -1 }, { 1, 0 }, };

        int i, j;

        station = new Station[row][col];

        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++) {
                station[i][j] = new Station(getX(i, j), getY(i), map[i][j]);
            }

    }

    public static double getX(int i, int j) {
        return (GameManager.W * 100 - space * 10.5) / 2 + space * j + space / 2
                * (i % 2);
    }

    public static double getY(int i) {
        return GameManager.H * 5 + space * Math.sqrt(3) / 2 * i;
    }

    public void initMap() {
        map = new int[row][col];
        int i, j;
        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++)
                map[i][j] = EMPTY;
        int sum = (int) (Math.random() * 3) + 5;
        int ob_i, ob_j;
        for (i = 0; i < sum; i++) {
            do {
                ob_i = (int) (Math.random() * row);
                ob_j = (int) (Math.random() * col);
            } while (ob_i == cat.pos_i && ob_j == cat.pos_j);
            map[ob_i][ob_j] = OBSTACLE;
        }
    }

    public void getPath(int location_i, int location_j) {

        if (location_i == 0 || location_i == row - 1 || location_j == 0
                || location_j == col - 1) {
            if (location_i == 0)
                cat.pos_i = -1;
            else if (location_i == row - 1)
                cat.pos_i = row;
            else if (location_j == 0)
                cat.pos_j = -1;
            else if (location_j == col - 1)
                cat.pos_j = col;
            CatchCats.status = CatchCats.LOST;
            GameManager.setScene(GameManager.GAME, 1000,
                    GameManager.SWITCHER_SLOW);
            return;
        }

        Point location = new Point(location_i, location_j);

        int i, j;

        before = new Point[row][col];
        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++)
                before[i][j] = null;
        before[location.i][location.j] = location;

        queue = new Queue();
        int new_i, new_j;
        for (i = 0; i < 6; i++) {
            new_i = location.i + amend[i][0];
            new_j = location.j + amend[i][1] + location.i % 2
                    * ((i / 2 + 1) % 2);
            if (map[new_i][new_j] == EMPTY) {
                before[new_i][new_j] = location;
                queue.enter(new Point(new_i, new_j));
            }
        }

        while (!queue.isEmpty()) {
            Point p = queue.out();

            if (p.i == 0 || p.i == row - 1 || p.j == 0 || p.j == col - 1) {
                Point path = p;
                int new_location_i = -1, new_location_j = -1;
                while (!path.equal(location)) {
                    map[path.i][path.j] = PATH;
                    new_location_i = path.i;
                    new_location_j = path.j;
                    path = before[path.i][path.j];
                }
                map[location.i][location.j] = PATH;

                if (new_location_i != -1) {
                    cat.pos_i = new_location_i;
                    cat.pos_j = new_location_j;
                }
                return;
            }

            for (i = 0; i < 6; i++) {
                new_i = p.i + amend[i][0];
                new_j = p.j + amend[i][1] + p.i % 2 * ((i / 2 + 1) % 2);
                if (checkInMap(new_i, new_j) && map[new_i][new_j] == EMPTY
                        && before[new_i][new_j] == null) {

                    queue.enter(new Point(new_i, new_j));
                    before[new_i][new_j] = p;
                }
            }
        }

        int find = 0;
        for (i = 0; i < 6; i++) {
            new_i = location.i + amend[i][0];
            new_j = location.j + amend[i][1] + location.i % 2
                    * ((i / 2 + 1) % 2);
            if (map[new_i][new_j] == EMPTY) {
                cat.pos_i = new_i;
                cat.pos_j = new_j;
                find = 1;
            }
        }

        if (find == 0) {
            CatchCats.status = CatchCats.WIN;
            GameManager.sound.play(Sound.sound_catched);
            GameManager.setScene(GameManager.GAME, 1000,
                    GameManager.SWITCHER_SLOW);
        }
    }

    public static boolean checkInMap(int i, int j) {
        return (i >= 0 && i < row && j >= 0 && j < col);
    }

    public void show() {
        int i, j;
        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++) {
                station[i][j].show(map[i][j]);
            }

        cat.show();
    }

    public void logic() {
        cat.move();
    }

    public void onTouch(MotionEvent event) {

        if (cat.isMove)
            return;

        int i, j;
        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++) {
                if (station[i][j].onTouch(event)) {
                    if (i == cat.pos_i && j == cat.pos_j) {
                        GameManager.sound.play(Sound.sound_touched);
                    } else {
                        station[i][j].selected = true;
                        clear();
                        map[i][j] = OBSTACLE;

                        getPath(cat.pos_i, cat.pos_j);
                        cat.setMove();
                        return;
                    }
                }
            }
    }

    public void clear() {
        int i, j;
        for (i = 0; i < row; i++)
            for (j = 0; j < col; j++)
                if (map[i][j] == PATH) {
                    map[i][j] = EMPTY;
                }
    }
}