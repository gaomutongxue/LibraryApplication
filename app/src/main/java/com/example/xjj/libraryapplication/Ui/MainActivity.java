package com.example.xjj.libraryapplication.Ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BookManager.BookManagerFragment;
import com.example.xjj.libraryapplication.Ui.Message.MessageFragment;
import com.example.xjj.libraryapplication.Ui.index.IndexFragment;
import com.example.xjj.libraryapplication.Ui.user.UserActivity;
import com.example.xjj.libraryapplication.Ui.user.UserManagerment;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.common.Setting;
import com.example.xjj.libraryapplication.common.Waiting;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.mob.jimu.biz.ReadOnlyProperty;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;
import com.mob.ums.gui.UMSGUI;

import butterknife.BindView;
import butterknife.ButterKnife;
//import cn.smssdk.SMSSDK;

public class MainActivity extends BaseActivity {
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.addview)
    LinearLayout addview;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private BaseFragment currentFragment;
    private FragmentManager fragmentManager;
    private MessageFragment messageFragment;//消息页fragment
    private IndexFragment indexFragment;    // 首页fragment
    private BookManagerFragment bookManagerFragment;//书籍管理界面
    private UserManagerment userManagerment;//用户跳转失败的界面
    private int currentItemPosition ;//当前按钮位置
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE};
    public final int All_Permission = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

        registerListener();
//        UMSSDK.getLoginUser(new OperationCallback<User>(){
//
//            @Override
//            public void onSuccess(User user) {
//                super.onSuccess(user);
//                user.nickname.set("bbb");
//                Log.d("-------------",user.nickname.get());
//            }
//        });

//        toStartActivity(BookDetailActivity.class);  //测试用直接启动想要测试的activity

    }

    private void registerListener() {
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.d("选中的位置=======",String.valueOf(position));
                switch (position){
                    case 0:
                        setContentFragment(indexFragment,false);
                        currentItemPosition=position;
                        break;
                    case 1:
                        if(bookManagerFragment==null){
                            bookManagerFragment=new BookManagerFragment();
                        }
                        setContentFragment(bookManagerFragment,false);
                        currentItemPosition=position;
                        break;
                    case 2:
                    if(messageFragment==null){
                          messageFragment=new MessageFragment();
                      }
                      setContentFragment(messageFragment,false);
                        currentItemPosition=position;
                        break;
                    case 3:
                        if (UMSSDK.amILogin()){
                            toStartActivity(UserActivity.class);;
                            bottomNavigationBar.selectTab(currentItemPosition);
                        }else {
                            if(userManagerment==null){
                                UMSGUI.showLogin(new OperationCallback<com.mob.ums.User>(){
                                    public void onSuccess(com.mob.ums.User user) {
                                        currentUser=user;

                                        Setting.setNormalBooleanValue(Setting.ISLOGIN,true);
                                        toStartActivity(UserActivity.class);
                                        setContentFragment(currentFragment,false);
                                        bottomNavigationBar.selectTab(currentItemPosition);
                                        PostRequest request = OkGo.<String>post(Const.URL+"adduser").tag(this);
                                        String id= currentUser.id.get();
                                        request.params("id",id);
                                        request.execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
                                                waiting.hideWaiting();
                                                Log.e("xiao====", "得到返回消息" + response.body());
                                                if (response.body().equals("fail")) {
                                                    showToast("登入成功");
                                                }else {
                                                    showToast("欢迎");
                                                }
                                            }
                                            @Override
                                            public void onError(Response<String> response) {
                                                super.onError(response);
                                                showToast("网络状况不佳.请重试");
                                            }
                                        });
                                    }

                                    public void onFailed(Throwable var1) {
                                        Log.d("------------","登入失败");

                                    }

                                    public void onCancel() {
                                        Log.d("-----------","情况失败");
                                        setContentFragment(currentFragment,false);
                                        bottomNavigationBar.selectTab(currentItemPosition);
                                    }
                                });
                             //   userManagerment=new UserManagerment();
                            }
//                            setContentFragment(userManagerment,false);
                        }

                        break;



                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        waiting.hideWaiting();
    }

    private void init() {
        checkPermission();
        setCurrentUser();
        waiting = new Waiting(this);
        waiting.showWaiting(false);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home, "首页"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home, "书籍"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home, "消息"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home, "我的"));
        bottomNavigationBar.initialise();

        indexFragment=new IndexFragment();
        setContentFragment(indexFragment,false);
        currentFragment=indexFragment;

    }

    private void setCurrentUser() {
        if (UMSSDK.amILogin()) {
            UMSSDK.getLoginUser(new OperationCallback<User>() {
                public void onSuccess(User user) {
                    currentUser=user;
                    ReadOnlyProperty readOnlyProperty=currentUser.id;
                    String id= currentUser.id.get();
                    Log.d("xiao====",id);
                    PostRequest request = OkGo.<String>post(Const.URL+"adduser").tag(this);
                    request.params("id",id);
                    request.execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            waiting.hideWaiting();
                            Log.e("xiao====", "得到返回消息" + response.body());
                            if (response.body().equals("fail")) {
                                showToast("登入成功");
                            }else {
                                showToast("欢迎");
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            showToast("网络状况不佳.请重试");
                        }
                    });
                    //todo 发起身份请求
                }

                public void onFailed(Throwable var1) {
                    showToast(getString(R.string.net_link_erro));
                }

                public void onCancel() {
                }
            });
        }
    }

    public void setContentFragment(BaseFragment fragment, boolean bringToFront) {
        if (currentFragment == fragment) return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.addview, fragment);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = fragment;

    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, All_Permission);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (exitAppHelper.isNeedExitOnBackKey(this)) {
                //监听back键
                Setting.setNormalBooleanValue(Setting.ISBACKGROUND, true);

                finish();
                return false;
            }
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }
}
