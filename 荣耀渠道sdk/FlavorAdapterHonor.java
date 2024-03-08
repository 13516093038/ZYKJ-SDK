package com.zongyi.flavoradapter;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


import com.zongyi.channeladapter.ChannelAdapterHonor;
import com.zongyi.channeladapter.ChannelAdapterMain;

public class FlavorAdapterHonor extends FlavorAdapter{

    private ChannelAdapterHonor _channelAdapterHonor;
    private Activity _activity;

    @Override
    public void onCreate(Activity activity) {
        _activity = activity;
        _channelAdapterHonor = new ChannelAdapterHonor();
        _channelAdapterHonor.initSdk("104423156","109999814470","MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAlrwsdTdQCpBT6vIh317l22oWtlrQrCETv4nhosZDA8ZjU6lgzOC3PbdHWfsYcDYwymNN5E2tIhHmnXWsnUYriwLdSZ1fnnV1h7OpG4IiTLN3wufJiKXKVgvJm5KXlMvImZAhQQCYou3QmgssLj7DVh6fg3a8lvu6BMS294XLtmIBPcAHcBpe48B8IibXSjWMv2DIADsP76FOFtWGsjHq7W0Sz4LKg5aAuXgmyfiHzuR0Bhs8E4w6U5N4hzjqgDNi3IxCwRhMaORI0YuwxXE45UrGkpMeLs+7P5c/RsNxHZjPF7hceQ2tiAiuKQtyZhkFSc0SyHQk1VFqIQnWtmjs1P2xE9sYUFTo7IqDxsV0gCUKcGsJnANkcCL19fqUYG+MZTfVOqwm4C579xyeMoeSNR5nBPlfUqeRivcHvULPLtFgQ3qLboCLTxzTT9o7N6sdraelfnQEBdeK7xmJA+beY6N4ZmUQ2wc26ODa+/mO4nz4cd8+Y6djoWLqvdxcpjF3AgMBAAE=",true);
    }

    @Override
    public void initSdk(Activity activity) {
        _channelAdapterHonor.obtainOwnedPurchases(new ChannelAdapterHonor.queryPurchasesCallback() {
            @Override
            public void onSuccess(String productId) {
                Toast.makeText(activity, "查询成功" + productId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(activity, "查询失败" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void login(final Activity activity) {
        _channelAdapterHonor.login(activity, new ChannelAdapterHonor.LoginCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(activity, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void pay(String productId) {
        ChannelAdapterMain.ProductInfo productInfo = new ChannelAdapterMain.ProductInfo();
        productInfo.productId = "zuanshi1";
        _channelAdapterHonor.pay(_activity, productInfo, new ChannelAdapterMain.PayCallback() {
            @Override
            public void onSuccess(ChannelAdapterMain.ProductInfo productInfo) {
                Toast.makeText(_activity, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(ChannelAdapterMain.ProductInfo productInfo, String error) {
                Toast.makeText(_activity, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void exitGame(final Activity activity) {
        _channelAdapterHonor.exitGame(activity);
    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public void onStart(Activity activity) {

    }

    @Override
    public void onRestart(Activity activity) {

    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
