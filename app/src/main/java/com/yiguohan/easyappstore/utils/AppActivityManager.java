package com.yiguohan.easyappstore.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * 统一管理Activity
 * Created by yiguohan on 2017/10/18.
 */

public class AppActivityManager {

    private static Stack<Activity> mActivityStack;
    private static AppActivityManager mAppActivityManager;

    public AppActivityManager() {
    }

    /**
     * 获得单例
     *
     * @return
     */
    public static AppActivityManager getInstance() {
        if (mAppActivityManager == null) {
            mAppActivityManager = new AppActivityManager();
        }
        return mAppActivityManager;
    }

    /**
     * 添加到管理栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶的Activity实例
     *
     * @return
     */
    public Activity getTopActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 移除指定Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 结束栈顶的Activity实例
     */
    public void killTopActivity() {
        Activity activity = getTopActivity();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity实例
     *
     * @param activity
     */
    private void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    private void killAllActivity() {
        for (Activity activity : mActivityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * @param context
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Log.e("AppActivityManager", "" + e);
        }
    }
}
