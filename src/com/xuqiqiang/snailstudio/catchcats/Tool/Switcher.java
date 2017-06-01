package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.os.Handler;
import android.os.Message;

public abstract class Switcher {

    public boolean starting;
    private int delay;
    private int speed;
    private Thread thread = null;

    public static final int HANDLER_INIT = 1;
    private Handler handler;

    public Switcher(int delay, int speed) {
        this.delay = delay;
        this.speed = speed;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch (what) {
                case HANDLER_INIT:
                    init();

                    thread = new Thread(new Runnable() {
                        public void run() {
                            while (GameManager.switcherAlpha > 0) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                GameManager.switcherAlpha -= 12 * Switcher.this.speed;
                                if (GameManager.switcherAlpha < 0)
                                    GameManager.switcherAlpha = 0;
                            }

                            starting = false;
                        }
                    });
                    thread.start();
                    break;
                }
            }
        };
        starting = false;
    }

    public void start() {
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (GameManager.switcherAlpha < 255) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameManager.switcherAlpha += 12 * speed;
                    if (GameManager.switcherAlpha > 255)
                        GameManager.switcherAlpha = 255;

                }

                Message msg = new Message();
                msg.what = HANDLER_INIT;
                handler.sendMessage(msg);
            }
        });
        thread.start();
        starting = true;
    }

    public void stop() {
        if (thread != null)
            thread.interrupt();
        starting = false;
    }

    public abstract void init();
};