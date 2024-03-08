package com.zongyi.zyadaggregate.thirdparty;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


import com.zongyi.zyadaggregate.ZYAGAdZoneBanner;
import com.zongyi.zyadaggregate.ZYAGAdZoneInterstitial;
import com.zongyi.zyadaggregate.ZYAGAdZoneNative;
import com.zongyi.zyadaggregate.ZYAGAdZoneSplash;
import com.zongyi.zyadaggregate.ZYAGAdZoneVideo;
import com.zongyi.zyadaggregate.ZYAdAggregate;
import com.zongyi.zyadaggregate.zyagtoutiao.ZYAGAdPlatformTouTiao;


import java.util.Random;

/**
 * Created by zhangguozhi on 2018/2/8.
 */

public class ZYAGInitializer {

    private static int s_intersTimes = 0;


    public static void registerPlatforms(Activity activity) {
        ZYAdAggregate.getLogger().log("注册平台对象");
        ZYAdAggregate.instance().registerPlatform(ZYAGAdPlatformTouTiao.instance(), activity);
    }

    public static void refreshRemoteConfigs() {
        ZYAdAggregate.getLogger().log("刷新远程配置");
        ZYAdAggregate.instance().refreshRemoteConfig(new ZYAdAggregate.ZoneType[]{ZYAdAggregate.ZoneType.Banner, ZYAdAggregate.ZoneType.Interstitial, ZYAdAggregate.ZoneType.Video, ZYAdAggregate.ZoneType.Splash, ZYAdAggregate.ZoneType.Native});
    }

    public static void initBanner(String zoneName, Activity contentActivity) {

        int bannerWidth = 0;
        int bannerHeight = 0;
        if (contentActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Point winSize = new Point();
            contentActivity.getWindowManager().getDefaultDisplay().getSize(winSize);
            bannerWidth = winSize.x;
            bannerHeight = (int) (bannerWidth / 6.4);
        } else if (contentActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bannerWidth = dp2px(contentActivity, 320);
            bannerHeight = dp2px(contentActivity, 50);
        }
        FrameLayout.LayoutParams bannerLayoutParams = new FrameLayout.LayoutParams(bannerWidth, bannerHeight);
        //     bannerLayoutParams.leftMargin = dp2px(contentActivity, 150);
        bannerLayoutParams.gravity = Gravity.BOTTOM | Gravity.START;
        initBanner(zoneName, contentActivity, bannerLayoutParams);
    }

    private static void initBanner(String zoneName, Activity contentActivity, FrameLayout.LayoutParams bannerLayoutParams) {

        ZYAGAdZoneBanner banner = null;
        try {
            banner = (ZYAGAdZoneBanner) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (banner == null) {
            return;
        }
        banner.setContainerActivity(contentActivity);
        ZYAdAggregate.getLogger().log(String.format("初始化广告位%s", zoneName));
        banner.setBannerViewLayoutParams(bannerLayoutParams);
//        banner.setBannerScaleX(0.8f);
//        banner.setBannerScaleY(0.8f);
        banner.loadAd();
    }

    private static int dp2px(Activity activity, float dpValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showBannner(String zoneName) {
        ZYAdAggregate.getLogger().log(String.format("展示广告位%s", zoneName));
        ZYAGAdZoneBanner banner = null;
        try {
            banner = (ZYAGAdZoneBanner) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (banner == null) {
            return;
        }
        banner.showAd();
    }

    public static void hideBanner(String zoneName) {
        ZYAdAggregate.getLogger().log(String.format("隐藏广告位%s", zoneName));
        ZYAGAdZoneBanner banner = null;
        try {
            banner = (ZYAGAdZoneBanner) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (banner == null) {
            return;
        }
        banner.hideAd();
    }


    public static void initInterstitial(String zoneName, Activity contentActivity) {
        ZYAdAggregate.getLogger().log(String.format("初始化广告位%s", zoneName));
        ZYAGAdZoneInterstitial interstitial = null;
        try {
            interstitial = (ZYAGAdZoneInterstitial) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (interstitial == null) {
            return;
        }
        interstitial.setContainerActivity(contentActivity);
        interstitial.loadAd();
    }

    public static void showInterstitial(String zoneName, Activity contentActivity) {
        ZYAdAggregate.getLogger().log(String.format("展示广告位%s", zoneName));
        ZYAGAdZoneInterstitial interstitial = null;
        try {
            interstitial = (ZYAGAdZoneInterstitial) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (interstitial == null) {
            return;
        }
        if (contentActivity != null) {
            interstitial.setContainerActivity(contentActivity);
        }
        interstitial.showAd();
    }

    public static void showInterstitial(String zoneName) {
        showInterstitial(zoneName, null);
    }

    public static void showDelayInterstitial(final String zoneName, float percentage, float delayTime, float intersTimes) {
        s_intersTimes++;
        if (s_intersTimes == intersTimes) {
            s_intersTimes = 0;
            Random random = new Random();
            int i = random.nextInt(99);
            if (i >= 0 && i < percentage) {
                System.out.println("true");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showInterstitial(zoneName, null);
                    }
                }, (long) (delayTime * 1000));
            } else {
                System.out.println("false");
                showInterstitial(zoneName, null);
            }

        }

    }

    public static boolean isInterstitialAvalible(String zoneName) {
        ZYAGAdZoneInterstitial interstitial = null;
        try {
            interstitial = (ZYAGAdZoneInterstitial) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        if (interstitial == null) {
            return false;
        }
        return interstitial.isAdAvailable();
    }

    public static void initVideo(String zoneName, Activity contentActivity) {
        ZYAdAggregate.getLogger().log(String.format("初始化广告位%s", zoneName));
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (video == null) {
            return;
        }
        video.setContainerActivity(contentActivity);
        video.loadAd();
    }

    public static void showVideo(String zoneName) {
        ZYAdAggregate.getLogger().log(String.format("展示广告位%s", zoneName));
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (video == null) {
            return;
        }
        video.showAd();
    }

    public static void videoOnBackPressed(String zoneName) {
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (video == null) {
            return;
        }
        video.onBackPressed();
    }

    public static void videoOnResume(String zoneName) {
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (video == null) {
            return;
        }
        video.onResume();
    }

    public static void videoOnPause(String zoneName) {
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (video == null) {
            return;
        }
        video.onPause();
    }

    public static boolean isVideoAvalible(String zoneName) {
        ZYAGAdZoneVideo video = null;
        try {
            video = (ZYAGAdZoneVideo) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        if (video == null) {
            return false;
        }
        return video.isAdAvailable();
    }

    public static void showSplash(String zoneName, Activity contentActivity, Class nextActivityClass) {
        ZYAdAggregate.getLogger().log(String.format("展示广告位%s", zoneName));
        ZYAGAdZoneSplash splash = null;
        try {
            splash = (ZYAGAdZoneSplash) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (splash == null) {
            contentActivity.startActivity(new Intent(contentActivity, nextActivityClass));
            contentActivity.finish();
            return;
        }
        splash.timeout = 5;
        splash.setNextActivityClass(nextActivityClass);
        splash.setContainerActivity(contentActivity);
        splash.loadAd();
    }

    //TODO

    /**
     * 自定义广告 配置项
     *
     * @param zoneName   广告样式 文本串
     * @param view       自定义广告view
     * @param adShowTime 广告倒计时秒数
     */
    public static void setADConfigure(String zoneName, View view, int adShowTime) {
        ZYAGAdZoneSplash splash = null;
        try {
            splash = (ZYAGAdZoneSplash) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (splash == null) {
            return;
        }
        splash.setADConfigure(view, adShowTime);
    }

    public static void splashOnPause(String zoneName) {
        ZYAGAdZoneSplash splash = null;
        try {
            splash = (ZYAGAdZoneSplash) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (splash == null) {
            return;
        }
        splash.onPause();
    }

    public static void splashOnResume(String zoneName) {
        ZYAGAdZoneSplash splash = null;
        try {
            splash = (ZYAGAdZoneSplash) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (splash == null) {
            return;
        }
        splash.onResume();
    }


    public static void loadNative(String zoneName, Activity contentActivity, ZYAGAdZoneNative.IZYAGAdZoneNativeCallback nativeCallback) {
        ZYAGAdZoneNative nativeZone = null;
        try {
            nativeZone = (ZYAGAdZoneNative) ZYAdAggregate.instance().getAdZoneByName(zoneName);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }
        if (nativeZone == null) {
            return;
        }
        nativeZone.setContainerActivity(contentActivity);
        nativeZone.delegate = nativeCallback;
        nativeZone.loadAd();
    }

}
