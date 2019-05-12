package com.example.xjj.libraryapplication.Ui.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xjj.libraryapplication.BaseApplication;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.Ui.manager.LendActivity;
import com.example.xjj.libraryapplication.Ui.manager.ManagerActivity;
import com.liaoinstan.springview.widget.SpringView;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.bt_user_lent)
    Button btUserLent;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_person_data)
    LinearLayout llPersonData;
    @BindView(R.id.bt_guanliyuan)
    Button btGuanliyuan;
    @BindView(R.id.bt_lend)
    Button btLend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        init();
        registerListener();

    }

    private void registerListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btUserLent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llPersonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toStartActivity(UserUpDataActivity.class);
                Log.d("-----------", "qianjin");
            }
        });
    }

    private void init() {

        setSupportActionBar(toolbar);

        llPersonData.setClickable(true);
        getUserInfo();


    }

    private void getUserInfo() {
        UMSSDK.getLoginUser(new OperationCallback<User>() {

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                String[] faceImageUri = user.avatar.get();
                Glide.with(BaseApplication.baseContext).load(Uri.parse(faceImageUri[0])).into(profileImage);
                tvUserName.setText(user.nickname.get());

            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llPersonData,"scaleY",1f, 5f);
//        objectAnimator.setDuration(2000);
//        objectAnimator.start();
        super.onResume();
    }


    @OnClick({R.id.bt_guanliyuan, R.id.bt_lend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_guanliyuan:
                Intent intent=new Intent(UserActivity.this, ManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_lend:
                Intent intent1=new Intent(UserActivity.this, LendActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
