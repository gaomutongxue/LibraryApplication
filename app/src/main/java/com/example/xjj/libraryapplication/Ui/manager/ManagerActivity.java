package com.example.xjj.libraryapplication.Ui.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.common.Const;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerActivity extends BaseActivity {

    @BindView(R.id.etname)
    EditText etname;
    @BindView(R.id.etatuor)
    EditText etatuor;
    @BindView(R.id.etprice)
    EditText etprice;
    @BindView(R.id.ettotle)
    EditText ettotle;
    @BindView(R.id.btnadd)
    Button btnadd;
    @BindView(R.id.btndel)
    Button btndel;
    @BindView(R.id.ettype)
    EditText ettype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnadd, R.id.btndel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnadd:
                if (etname.getText() != null && etatuor.getText() != null && etprice.getText() != null && ettotle.getText() != null && ettype.getText() != null) {
                    String name = String.valueOf(etname.getText());
                    String autor = String.valueOf(etatuor.getText());
                    String weizhi = String.valueOf(etprice.getText());
                    String jianjie = String.valueOf(ettotle.getText());
                    String type= String.valueOf(ettype.getText());
                    PostRequest request = OkGo.<String>post(Const.URL + "addbook").tag(this);
//                    request.params("id",1);
                    request.params("id","1");
                    request.params("info", jianjie);
                    request.params("name", name);
                    request.params("autor", autor);
                    request.params("price", weizhi);
                    request.params("type",type);
                    request.execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("xiao====", "得到返回消息" + response.body());
                            if (response.body().equals("fail")) {
                                showToast("添加失败.请重试");
                            } else {
//                                List<Book> books = GsonUtils.parseJSONArray(response.body(), new TypeToken<List<Book>>() {
//                                }.getType());
                                showToast("添加成功");

                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            showToast("网络状况不佳.请重试");

                        }
                    });
                } else {
                    showToast("请输入相应信息");
                }

                break;
            case R.id.btndel:
                if (etname.getText() != null ) {
                    String name = String.valueOf(etname.getText());
                    PostRequest request = OkGo.<String>post(Const.URL + "deleteBook").tag(this);
//                    request.params("id",1);
                    request.params("name", name);
                    request.execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("xiao====", "得到返回消息" + response.body());
                            if (response.body().equals("fail")) {
                                showToast("没有此书");
                            } else {
//                                List<Book> books = GsonUtils.parseJSONArray(response.body(), new TypeToken<List<Book>>() {
//                                }.getType());
                                showToast("删除成功");

                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            showToast("网络状况不佳.请重试");

                        }
                    });
                } else {
                    showToast("请输入相应信息");
                }
                break;
        }
    }
}
