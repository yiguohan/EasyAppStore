package com.yiguohan.easyappstore;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_STORAGE = 1;

    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private SharedPreferences sp;

    @BindView(R.id.enter_button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        verifyStoragePermission(this);
        sp = getSharedPreferences("easyAppStore",MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst",true);
        if (!isFirst){

            goHome();
        }
    }
    @OnClick(R.id.enter_button)
    public void goHome(){
        sp.edit().putBoolean("isFirst",false).commit();
        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        finish();
    }


    /**
     * 申请权限 SD卡的读写权限
     *
     * @param activity
     */
    private void verifyStoragePermission(Activity activity) {
        // 1 检测权限
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PermissionChecker.PERMISSION_GRANTED) {
            // 2 没有权限，需要申请权限 弹出对话框
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            //申请权限成功
            Toast.makeText(this, "已获得SD卡权限", Toast.LENGTH_SHORT).show();
        } else {
            //申请权限失败
            Toast.makeText(this, "尚未获得SD卡权限，可能会影响程序运行", Toast.LENGTH_SHORT).show();
        }
    }
}
