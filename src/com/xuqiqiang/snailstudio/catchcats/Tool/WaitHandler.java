package com.xuqiqiang.snailstudio.catchcats.Tool;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xuqiqiang.snailstudio.catchcats.R;
import com.xuqiqiang.snailstudio.catchcats.Activity.MainActivity;

public class WaitHandler extends Handler {

    public static final int HANDLER_REFRESH = 1, HANDLER_INIT = 2;
    private RelativeLayout mainLayout;
    private LinearLayout bgLayout;
    private int alpha;
    private MainActivity mainActivity;

    public WaitHandler(MainActivity context) {
        mainActivity = context;
        alpha = 0;
        mainLayout = (RelativeLayout) mainActivity
                .findViewById(R.id.mainLayout);
        bgLayout = (LinearLayout) mainActivity.findViewById(R.id.bgcolor);
        bgLayout.getBackground().setAlpha(alpha);
    }

    public void init() {
        postDelayed(new Runnable() {
            public void run() {
                threadStart();
            }
        }, 2000);
    }

    private void threadStart() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (alpha < 255) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    alpha += 5;
                    Message msg = new Message();
                    msg.what = HANDLER_REFRESH;
                    sendMessage(msg);
                }
                Message msg = new Message();
                msg.what = HANDLER_INIT;
                sendMessage(msg);
            }
        });
        thread.start();
    }

    @Override
    public void handleMessage(Message msg) {
        final int what = msg.what;
        switch (what) {
        case HANDLER_REFRESH:
            bgLayout.getBackground().setAlpha(alpha);
            mainLayout.invalidate();
            break;
        case HANDLER_INIT:
            mainActivity.init();
            break;
        }
    }
};