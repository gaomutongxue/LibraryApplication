package com.example.xjj.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xjj.libraryapplication.Ui.MainActivity;
import com.example.xjj.libraryapplication.common.Setting;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuidActivity extends AppCompatActivity {

    @BindView(R.id.iv_guid)
    ImageView ivGuid;
    @BindView(R.id.bt_guid_skip)
    Button btGuidSkip;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        ButterKnife.bind(this);
        register();


    }

    @Override
    protected void onResume() {
        turnToMainActivity();
        super.onResume();
    }

    private void turnToMainActivity() {
        if (!Setting.getNormalBooleanValue(getString(R.string.isFristLogin),true)){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainactivy();
                finish();
            }
        }, 2000);
        }else{
            Setting.setNormalBooleanValue(getString(R.string.isFristLogin),false);
            finish();
        }
    }

    private void register() {

    }

    private void startMainactivy() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.bt_guid_skip)
    public void skipGuidActivity() {
        startMainactivy();
        finish();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }
}
