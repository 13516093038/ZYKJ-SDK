package com.SYzongYi.doudizhu.huawei;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.bfwhxg.spfan.SimaoAggregate;
import com.bfwhxg.spfan.SimaoZoneBanner;
import com.bfwhxg.spfan.SimaoZoneInterstitial;
import com.bfwhxg.spfan.SimaoZoneNative;
import com.bfwhxg.spfan.SimaoZoneSplash;
import com.bfwhxg.spfan.SimaoZoneVideo;
import com.bfwhxg.spfan.simaohuawei.SimaoPlatformHuawei;
import com.bfwhxg.spfan.simaohuawei.SimaoPlatformHuaweiNative;

import java.util.Random;

/**
 * Created by zhangguozhi on 2018/2/8.
 */

public class ZYAGInitializer {

    private static int s_intersTimes = 0;

    public static void registerPlatforms(Activity activity){
        SimaoAggregate.getLogger().log("注册平台对象");
        SimaoAggregate.instance().registerPlatform(SimaoPlatformHuawei.instance(), activity);
        SimaoAggregate.instance().registerPlatform(SimaoPlatformHuaweiNative.instance(), activity);
    }

    public static void registerPlatformsNoProtocol(Activity activity){
        SimaoAggregate.getLogger().log("注册平台对象");
        SimaoAggregate.instance().registerPlatform2(SimaoPlatformHuawei.instance(), activity);
        SimaoAggregate.instance().registerPlatform2(SimaoPlatformHuaweiNative.instance(), activity);
    }

    public static void refreshRemoteConfigs(){
        SimaoAggregate.getLogger().log("刷新远程配置");
        SimaoAggregate.instance().refreshRemoteConfig(new SimaoAggregate.ZoneType[]{SimaoAggregate.ZoneType.Banner, SimaoAggregate.ZoneType.Interstitial, SimaoAggregate.ZoneType.Video, SimaoAggregate.ZoneType.Splash, SimaoAggregate.ZoneType.Native});
    }

    public static void initBanner(String zoneName, Activity contentActivity){

        int bannerWidth = 0;
        int bannerHeight = 0;
        if (contentActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Point winSize = new Point();
            contentActivity.getWindowManager().getDefaultDisplay().getSize(winSize);
            bannerWidth = winSize.x;
            bannerHeight = (int) (bannerWidth/6.4);
        }else if (contentActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            bannerWidth = dp2px(contentActivity, 320);
            bannerHeight = dp2px(contentActivity, 50);
        }
        FrameLayout.LayoutParams bannerLayoutParams = new FrameLayout.LayoutParams(bannerWidth, bannerHeight);
   //     bannerLayoutParams.leftMargin = dp2px(contentActivity, 150);
        bannerLayoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        initBanner(zoneName, contentActivity, bannerLayoutParams);
    }

    private static void initBanner(String zoneName, Activity contentActivity, FrameLayout.LayoutParams bannerLayoutParams){

        SimaoZoneBanner banner = null;
        try{
            banner = (SimaoZoneBanner) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (banner == null){
            return;
        }
        banner.setContainerActivity(contentActivity);
        SimaoAggregate.getLogger().log(String.format("初始化广告位%s",zoneName));
        banner.setBannerViewLayoutParams(bannerLayoutParams);
//        banner.setBannerScaleX(0.8f);
//        banner.setBannerScaleY(0.8f);
        banner.loadAd();
    }

    private static int dp2px(Activity activity, float dpValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showBannner(String zoneName){
        SimaoAggregate.getLogger().log(String.format("展示广告位%s",zoneName));
        SimaoZoneBanner banner = null;
        try{
            banner = (SimaoZoneBanner) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (banner == null){
            return;
        }
        banner.showAd();
    }

    public static void hideBanner(String zoneName){
        SimaoAggregate.getLogger().log(String.format("隐藏广告位%s",zoneName));
        SimaoZoneBanner banner = null;
        try{
            banner = (SimaoZoneBanner) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (banner == null){
            return;
        }
        banner.hideAd();
    }


    public static void initInterstitial(String zoneName, Activity contentActivity){
        SimaoAggregate.getLogger().log(String.format("初始化广告位%s",zoneName));
        SimaoZoneInterstitial interstitial = null;
        try{
            interstitial = (SimaoZoneInterstitial) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (interstitial == null){
            return;
        }
        interstitial.setContainerActivity(contentActivity);
        interstitial.loadAd();
    }

    public static void showInterstitial(String zoneName, Activity contentActivity){
        SimaoAggregate.getLogger().log(String.format("展示广告位%s",zoneName));
        SimaoZoneInterstitial interstitial = null;
        try{
            interstitial = (SimaoZoneInterstitial) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (interstitial == null){
            return;
        }
        if (contentActivity != null){
            interstitial.setContainerActivity(contentActivity);
        }
        interstitial.showAd();
    }

    public static void showInterstitial(String zoneName){
        showInterstitial(zoneName, null);
    }

    public static void showDelayInterstitial(final String zoneName, float percentage, float delayTime, float intersTimes){
        s_intersTimes ++;
        if(s_intersTimes == intersTimes)
        {
            s_intersTimes = 0;
            Random random = new Random();
            int i = random.nextInt(99);
            if(i >= 0 && i < percentage)
            {
                System.out.println("true");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showInterstitial(zoneName, null);
                    }
                },(long)(delayTime * 1000));
            }
            else
            {
                System.out.println("false");
                showInterstitial(zoneName, null);
            }

        }

    }

    public static boolean isInterstitialAvalible(String zoneName){
        SimaoZoneInterstitial interstitial = null;
        try{
            interstitial = (SimaoZoneInterstitial) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return false;
        }
        if (interstitial == null){
            return false;
        }
        return interstitial.isAdAvailable();
    }

    public static void initVideo(String zoneName, Activity contentActivity){
        SimaoAggregate.getLogger().log(String.format("初始化广告位%s",zoneName));
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (video == null){
            return;
        }
        video.setContainerActivity(contentActivity);
        video.loadAd();
    }

    public static void showVideo(String zoneName){
        SimaoAggregate.getLogger().log(String.format("展示广告位%s",zoneName));
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (video == null){
            return;
        }
        video.showAd();
    }

    public static void videoOnBackPressed(String zoneName){
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (video == null){
            return;
        }
        video.onBackPressed();
    }

    public static void videoOnResume(String zoneName){
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (video == null){
            return;
        }
        video.onResume();
    }

    public static void videoOnPause(String zoneName){
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (video == null){
            return;
        }
        video.onPause();
    }

    public static boolean isVideoAvalible(String zoneName){
        SimaoZoneVideo video = null;
        try{
            video = (SimaoZoneVideo) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return false;
        }
        if (video == null){
            return false;
        }
        return video.isAdAvailable();
    }

    public static void showSplash(String zoneName, Activity contentActivity, Class nextActivityClass){
        SimaoAggregate.getLogger().log(String.format("展示广告位%s",zoneName));
        SimaoZoneSplash splash = null;
        try{
            splash = (SimaoZoneSplash) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (splash == null){
            contentActivity.startActivity(new Intent(contentActivity, nextActivityClass));
            contentActivity.finish();
            return;
        }
        splash.timeout = 5;
        splash.setNextActivityClass(nextActivityClass);
        splash.setContainerActivity(contentActivity);
        splash.loadAd();
    }

    public static void splashOnPause(String zoneName){
        SimaoZoneSplash splash = null;
        try{
            splash = (SimaoZoneSplash) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (splash == null){
            return;
        }
        splash.onPause();
    }

    public static void splashOnResume(String zoneName){
        SimaoZoneSplash splash = null;
        try{
            splash = (SimaoZoneSplash) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (splash == null){
            return;
        }
        splash.onResume();
    }


    public static void loadNative(String zoneName, Activity contentActivity, SimaoZoneNative.IZYAGAdZoneNativeCallback nativeCallback){
        SimaoZoneNative nativeZone = null;
        try{
            nativeZone = (SimaoZoneNative) SimaoAggregate.instance().getAdZoneByName(zoneName);
        }catch (ClassCastException e){
            e.printStackTrace();
            return;
        }
        if (nativeZone == null){
            return;
        }
        nativeZone.setContainerActivity(contentActivity);
        nativeZone.delegate = nativeCallback;
        nativeZone.loadAd();
    }
}
