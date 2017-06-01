package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.xuqiqiang.snailstudio.catchcats.Activity.MainActivity;
import com.xuqiqiang.snailstudio.catchcats.View.CatchCats;
import com.xuqiqiang.snailstudio.catchcats.View.MainMenu;
import com.xuqiqiang.snailstudio.catchcats.View.Settings;

public class GameManager {

    public static int scene;
    public static final int MAINMENU = 0, GAME = 1, HELP = 2, SET = 3;

    private SurfaceHolder sfh;
    public static Paint paint;
    private SurfaceView gameView;
    public static Canvas canvas;
    private Thread gameThread;
    private boolean gameThreadStart;
    private int gameThreadDelay;

    public static double W, H;

    public static Resourse res;
    public static Sound sound;

    private static MainMenu mainMenu;
    private static Settings settings;
    private static CatchCats game;

    public static int switcherAlpha;
    public static int switcherSpeed;
    public static final int SWITCHER_SLOW = 1, SWITCHER_QUICK = 5;
    public static Switcher switcher;

    public GameManager(Activity context) {

        WindowManager wm = context.getWindowManager();
        W = wm.getDefaultDisplay().getWidth() / 100f;
        H = wm.getDefaultDisplay().getHeight() / 100f;

        gameView = new SurfaceView(context) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                GameManager.this.onTouch(event);
                return true;
            }

        };
        gameView.setFocusable(true);

        sfh = gameView.getHolder();
        sfh.addCallback(new Callback() {

            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                    int arg3) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder arg0) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder arg0) {
            }

        });
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        context.setContentView(gameView);

        res = new Resourse(context);
        sound = new Sound(context);

        mainMenu = new MainMenu();
        scene = MAINMENU;

        onResume();

        switcherAlpha = 0;

    }

    public void onResume() {
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameRun();
            }
        });
        gameThreadStart = true;
        gameThreadDelay = 50;
        gameThread.start();
    }

    public static void setScene(final int s, int delay, int speed) {

        if (switcher != null && switcher.starting)
            switcher.stop();
        switcher = new Switcher(delay, speed) {
            @Override
            public void init() {
                switch (s) {
                case MAINMENU:
                    mainMenu = new MainMenu();
                    break;
                case GAME:
                    game = new CatchCats();
                    MainActivity.showInsertAd();
                    break;
                case HELP:
                    break;
                case SET:
                    settings = new Settings();
                    break;
                }
                scene = s;
            }
        };
        switcher.start();
    }

    public void showHelp() {
        Drawer.DrawImage(GameManager.res.mainbgBmp, 0, 0, GameManager.W * 100,
                GameManager.H * 100);

        double x = GameManager.W * 10, w = GameManager.W * 80, h = Resourse
                .getH(GameManager.res.helpBmp, GameManager.W * 80), y = (GameManager.H * 100 - h) / 2;
        Drawer.DrawRect(x - GameManager.W * 5, y - GameManager.H * 10, w
                + GameManager.W * 10, h + GameManager.H * 20, Color.LTGRAY);
        Drawer.DrawImage(GameManager.res.helpBmp, x, y, w, h);

        // Drawer.DrawRect(GameManager.W*5,GameManager.W*10, GameManager.W*90,
        // GameManager.H*70, Color.LTGRAY);
        // Drawer.DrawImage(GameManager.res.helpbmp,GameManager.W*10,GameManager.H*20,
        // GameManager.W*80,Resourse.geth(GameManager.res.helpbmp,GameManager.W*80));

    }

    private void onTouch(MotionEvent event) {

        if (switcherAlpha != 0)
            return;

        switch (scene) {
        case MAINMENU:
            mainMenu.onTouch(event);
            break;
        case GAME:
            game.onGameTouch(event);
            break;
        case HELP:
            break;
        case SET:
            settings.onTouch(event);
            break;
        }

    }

    private void draw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                int alpha = switcherAlpha;
                switch (scene) {
                case MAINMENU:
                    mainMenu.drawMenu();
                    break;
                case GAME:
                    game.drawGame();
                    break;
                case HELP:
                    showHelp();
                    break;
                case SET:
                    settings.drawSettings();
                    break;
                }

                if (alpha != 0) {
                    Drawer.DrawRect(0, 0, GameManager.W * 100,
                            GameManager.H * 100, Color.BLACK, alpha);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    private void logic() {
        switch (scene) {
        case MAINMENU:
            break;
        case GAME:
            game.logic();
            break;
        case HELP:
            break;
        case SET:
            break;
        }
    }

    private void gameRun() {
        while (gameThreadStart) {
            long start = System.currentTimeMillis();
            draw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < gameThreadDelay) {
                    Thread.sleep(gameThreadDelay - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        gameThreadStart = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (switcher != null && switcher.starting)
            return true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (scene != MAINMENU) {
                setScene(MAINMENU, 0, SWITCHER_QUICK);
                return true;
            }
        }
        return false;
    }
}