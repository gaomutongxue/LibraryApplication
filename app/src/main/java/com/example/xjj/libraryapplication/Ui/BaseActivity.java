package com.example.xjj.libraryapplication.Ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.user.UserActivity;
import com.example.xjj.libraryapplication.common.ExitAppHelper;
import com.example.xjj.libraryapplication.common.Setting;
import com.example.xjj.libraryapplication.common.Waiting;
import com.example.xjj.libraryapplication.interfaces.OnPermissonListener;
import com.mob.ums.User;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {
    public Waiting waiting;
    public ExitAppHelper exitAppHelper;
    public OnPermissonListener  onPermissionListener;
    public static User currentUser;
    protected void onResume() {
        super.onResume();

        exitAppHelper=new ExitAppHelper();
    }
 
    public void permissionRequests(Activity activity, String permission, OnPermissonListener onPermissonListener){
        onPermissionListener= onPermissonListener;
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {
                //权限通过
                onPermissionListener.onClick(true);
            } else {
                //没有权限，申请一下
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        1);
            }
        }else {
            //权限已有
            if (onPermissionListener != null) {
                onPermissionListener.onClick(true);
            }
        }
    }

    /**
     * 当此权限是必要的，而用户没有允许，允许弹出自定义的设置窗口

     */
    public void permissionCheck(Activity activity, OnPermissonListener onPermissonListener, String ...permissions){
        onPermissionListener = onPermissonListener;
        for (String permission:permissions){

            if (ContextCompat.checkSelfPermission(activity, permission)!= PackageManager.PERMISSION_GRANTED) {
                //没有权限，申请一下
                onPermissionListener.onClick(false);
                break;
            } else {
                if (permission.equals(permissions[permissions.length-1])){
                    onPermissionListener.onClick(true);
                }
            }
        }
    }



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                if(onPermissionListener!=null){
                    onPermissionListener.onClick(true);
                }
            } else {
                //权限拒绝
                if(onPermissionListener!=null){
                    onPermissionListener.onClick(false);
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void showToast(String toast) {
        try {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }
    public void resisterEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            //加上判断
            EventBus.getDefault().register(this);
        }
    }
    protected void toStartActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }
    protected void toStartActivityExtra(Class<?> targetClass,String key,String value) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtra(key,value);
        startActivity(intent);
    }
    protected String getIntentStringExtra(String key){
        Intent intent=new Intent();
        return intent.getStringExtra(key);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_hold, R.anim.activity_exit);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
