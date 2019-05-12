package com.example.xjj.libraryapplication.Ui.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.Ui.MainActivity;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.interfaces.OnPermissonListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserUpDataActivity extends BaseActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_brithday)
    EditText etBrithday;
    @BindView(R.id.iv_edit_user_face)
    ImageView ivEditUserFace;
    @BindView(R.id.tb_user_updata)
    Toolbar tbUserUpdata;
    @BindView(R.id.bt_user_certain)
    Button btUserCertain;
    @BindView(R.id.bt_user_cancel)
    Button btUserCancel;
    private List<Uri> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_updata);
        ButterKnife.bind(this);
        init();
        registerListener();

    }

    private void registerListener() {
        toolbarListener();
    }

    private void toolbarListener() {
        tbUserUpdata.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        setSupportActionBar(tbUserUpdata);
        ivEditUserFace.setClickable(true);
        Glide.with(this).load(Uri.parse(MainActivity.currentUser.avatar.get()[0])).error(R.mipmap.ic_launcher).into(ivEditUserFace);
        etBrithday.setText(MainActivity.currentUser.nickname.get());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (MainActivity.currentUser.birthday.get() != null) {
            etBrithday.setText(simpleDateFormat.format(MainActivity.currentUser.birthday.get()));
        }

    }

    @OnClick(R.id.iv_edit_user_face)
    public void onViewClicked() {
        request();
    }

    private void request() {
        permissionRequests(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new OnPermissonListener() {
            @Override
            public void onClick(boolean bln) {
            }
        });
        permissionRequests(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new OnPermissonListener() {
            @Override
            public void onClick(boolean bln) {

            }
        });
        permissionCheck(this, new OnPermissonListener() {
            @Override
            public void onClick(boolean bln) {
                initMatisse();
            }
        }, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        });
    }

    private void initMatisse() {
        Matisse.from(UserUpDataActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(Const.MATISSE_EXPECTESIZE)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(Const.REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            imageUris = Matisse.obtainResult(data);
            Glide.with(this).load(imageUris.get(0)).into(ivEditUserFace);
        }
    }

    @OnClick({R.id.bt_user_certain, R.id.bt_user_cancel})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.bt_user_certain:
                //TODO 修改用户资料
                break;
            case R.id.bt_user_cancel:
                finish();
                break;
        }
    }


}
