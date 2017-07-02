package com.sdkv2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class LockActivity extends Activity {
//    private boolean checkingDrawPermission = false;
//    public WindowManager winManager;
//    public RelativeLayout wrapperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        relativeLayout.setBackgroundColor(Color.TRANSPARENT);
//        setContentView(relativeLayout);
        MobileAds.initialize(this, "ca-app-pub-8634259134319673~6932397142");
        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8634259134319673/8129928746");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = pm.isScreenOn();
                Log.e("test","screen : " + isScreenOn);
                if (isScreenOn){
                    int number = SharedPreferencesUtil.getTagValueInt(LockActivity.this,"number");
                    long duration = number * 10000;
                    Log.e("test","duration = " + duration);
                    new CountDownTimer(duration,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                        @Override
                        public void onFinish() {
                            mInterstitialAd.show();
                        }
                    }.start();
                }
            }
            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                int number = SharedPreferencesUtil.getTagValueInt(LockActivity.this,"number");
                number++;
                SharedPreferencesUtil.setTagValueInt(LockActivity.this,"number",number);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }else
                    finish();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                int number = SharedPreferencesUtil.getTagValueInt(LockActivity.this,"number");
                number++;
                SharedPreferencesUtil.setTagValueInt(LockActivity.this,"number",number);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }else
                    finish();
            }
        });

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkingDrawPermission && Settings.canDrawOverlays(this)) {
//            recreate();
//        } else {
//
//        }
//    }
}
