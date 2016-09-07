package com.huadi.shoppingmall.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by smartershining on 16-7-15.
 */

public class BaseApplication extends Application {
    private static String mLock = "LOCK";
    private static BaseApplication mApplication;

    public static BaseApplication newInstance(){
        if(mApplication != null){
            synchronized(mLock ){
                if(mApplication != null){
                    mApplication = new BaseApplication();
                }
            }
        }
        return mApplication;
    }
    /**
     * 获取Context
     * @return 返回Context的对象
     */
    public static Context getContext(){
        return mApplication.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
        /*
        *做一些初始化的处理：初始化数据库，化图片缓存，初始化地图等
        */
    }
}