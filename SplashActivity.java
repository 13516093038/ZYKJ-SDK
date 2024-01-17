package com.SYzongYi.doudizhu.huawei;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.unity.ddzjdb.UnityPlayerActivity;



/**
 * 开屏
 *
 * @author YYY
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏展示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        requestSpreadAd();
    }

    private void requestSpreadAd() {
        int first = getIntValue(this,"firstLogin","first",1);
        // 未同意用户协议，则显示用户协议弹框
        if (first == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, UnityPlayerActivity.class));
                    SplashActivity.this.finish();
                }
            },500);;
        }else {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, UnityPlayerActivity.class));
            SplashActivity.this.finish();
//            SimaoAggregate.instance().init(this, "a05d6011e9c24c8d81577d5940dff82a");
//            ZYAGInitializer.registerPlatformsNoProtocol(this);
//            ZYAGInitializer.showSplash("开屏",this, UnityPlayerActivity.class);
        }
    }

    public int getIntValue(Activity activity, String spName, String key, int defValue) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        ZYAGInitializer.splashOnPause("开屏");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ZYAGInitializer.splashOnResume("开屏");
    }
}
