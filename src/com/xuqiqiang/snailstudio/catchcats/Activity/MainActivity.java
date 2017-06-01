package com.xuqiqiang.snailstudio.catchcats.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ZMAD.PushMessage.GetMessage;
import com.ZMAD.conne.AdManager;
import com.ZMAD.insert.GetInsert;
import com.xuqiqiang.snailstudio.catchcats.R;
import com.xuqiqiang.snailstudio.catchcats.Tool.GameManager;
import com.xuqiqiang.snailstudio.catchcats.Tool.WaitHandler;

public class MainActivity extends Activity {

    private static MainActivity mInstance;
    private GameManager mGameManager;
    private WaitHandler mWaitHandler;

    private static final String AD_ID = "d32a2e85721cee364aeaabb578c9586f";
    private static final double SHOW_INSERT_AD_CHANCE = 0.5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        mWaitHandler = new WaitHandler(this);
        mWaitHandler.init();
        initAd();
    }

    private void initAd() {
        AdManager ad = new AdManager(this, AD_ID, "01");
        ad.init();

        GetMessage gMessage = new GetMessage(this);
        gMessage.create();

    }

    public static void showInsertAd() {
        if (mInstance == null)
            return;
        double rand = Math.random();
        if (rand <= SHOW_INSERT_AD_CHANCE) {
            WindowManager wm = mInstance.getWindowManager();
            if (wm.getDefaultDisplay().getWidth() <= 320
                    && wm.getDefaultDisplay().getHeight() <= 250)
                return;
            GetInsert gInsert = new GetInsert(mInstance);
            gInsert.create();
        }
    }

    public void init() {
        mGameManager = new GameManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (mGameManager != null)
            mGameManager.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mGameManager != null)
            mGameManager.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mInstance = null;
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mGameManager != null && mGameManager.onKeyDown(keyCode, event))
            return true;
        else
            return super.onKeyDown(keyCode, event);
    }

}