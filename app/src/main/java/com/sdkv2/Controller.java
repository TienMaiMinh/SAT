package com.sdkv2;

import android.content.Context;
import android.content.Intent;

import java.util.Random;

public class Controller {
    public static void startLock(Context context){
        Random random = new Random();
        int number = 1 + random.nextInt(5);
        SharedPreferencesUtil.setTagValueInt(context,"number",number);
        context.startService(new Intent(context, StartLockscreenService.class));
    }
}
