package com.zongyi.flavoradapter;

import android.app.Application;
import com.zongyi.channeladapter.ChannelAdapterHonor;

/**
 * Created by zhongpingye on 2018/11/26.
 */

public class FlavorAdapterHonorApplication extends Application {
    private ChannelAdapterHonor _channelAdapterHonor;
    @Override
    public void onCreate() {
        super.onCreate();
        _channelAdapterHonor = new ChannelAdapterHonor();
        _channelAdapterHonor.setInApplication(this);
    }
}
