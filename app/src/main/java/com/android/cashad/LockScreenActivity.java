package com.android.cashad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;

public class LockScreenActivity extends Activity {
    private Button msize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        /*
                AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // Show the ad.
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();*/

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(10); // 시크바 최대값 설정
        seekBar.setProgress(0); // 초기 시크바 값 설정

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //TODO [SeekBar 컨트롤 진행 중]
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO [SeekBar 터치 이벤트 발생]
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //TODO [SeekBar 터치 이벤트 종료]

                Log.d("---","---");
                Log.w("//===========//","================================================");
                Log.d("","\n"+"[A_DisplayLight > onProgressChanged() 메소드 : SeekBar 터치 이벤트 종료 데이터 확인 실시]");
                Log.d("","\n"+"[데이터 : " + String.valueOf(progress) + "]");
                Log.w("//===========//","================================================");
                Log.d("---","---");
                if(progress == 10) finish();
            }
        });
    }
}
