package com.yiguohan.easyappstore.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yiguohan.easyappstore.R;
import com.yiguohan.easyappstore.utils.AppActivityManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected ViewGroup title_bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //保持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppActivityManager.getInstance().addActivity(this);
        //初始化布局
        initLayout();
        ButterKnife.bind(this);
        //设置沉浸式状态栏
        setStatus();
        //初始化View
        initView();

    }

    /**
     * 初始化布局
     */
    protected abstract void initLayout();
    //setContentView(R.layout.xxx)

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 系统版本4.4以上才可以设置沉浸式状态栏
     * 设置沉浸式的状态栏
     */
    protected void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            title_bar = (ViewGroup) findViewById(R.id.bar_layout);
            if (title_bar != null) {
                title_bar.setBackgroundResource(R.color.black_alpha_5);
                final int statusBarHeight = getStatusBarHeight();
                title_bar.post(new Runnable() {
                    @Override
                    public void run() {
                        int height = title_bar.getHeight();
                        ViewGroup.LayoutParams params = title_bar.getLayoutParams();
                        params.height = statusBarHeight + height;
                        title_bar.setLayoutParams(params);
                    }
                });
            }

        }
    }

    /**
     * 利用反射获取状态栏的高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        try {
            //通过反射获取类
            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            //创建对象
            Object o = aClass.newInstance();
            //拿到属性
            Field status_bar_height = aClass.getField("status_bar_height");
            //获取值
            Object o1 = status_bar_height.get(o);
            int height = Integer.parseInt(o1.toString());
            return getResources().getDimensionPixelOffset(height);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onDestroy() {
        AppActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
