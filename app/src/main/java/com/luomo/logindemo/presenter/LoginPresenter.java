package com.luomo.logindemo.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.luomo.logindemo.R;
import com.luomo.logindemo.model.LoginModel;
import com.luomo.logindemo.model.interfac.OnStringCallBack;
import com.luomo.logindemo.presenter.interfac.ILoginPresenter;
import com.luomo.logindemo.view.interfac.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.logindemo.presenter
 * @date :2016-09-12 15:46
 * @description:
 */
public class LoginPresenter implements ILoginPresenter {
    String TAG = getClass().getSimpleName();
    private final LoginModel mLoginMode;
    /**
     * 登陆的view
     */
    private ILoginView mLoginView;

    public LoginPresenter(ILoginView view) {
        mLoginView = view;//view层的关联
        mLoginMode = new LoginModel(mLoginView.getContext());//model层的关联
        initData();
    }

    private void initData() {
        mLoginView.addTextChangedListener();
    }

    /**
     * 登陆的方法
     */
    @Override
    public void login() {
        Map<String,String> param = mLoginView.getParam(1);
        Log.i(TAG, param.toString());
        if(param.isEmpty())//则不请求
            return;
        mLoginView.showProgress();
        mLoginMode.login(param, "请求地址", new OnStringCallBack() {
            @Override
            public void onError(Exception e) {
                mLoginView.dismissProgress();
            }

            @Override
            public void onResponse(String s){
                mLoginView.dismissProgress();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if ("success".equals(jsonObject.getString("flag"))) {//登陆成功
                        //缓冲相关数据
                        //跳转到主界面
                        mLoginView.jump2MainPager();
                    } else {
                        mLoginView.showToast(R.string.account_or_password_error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void textChanged(int resourceId, CharSequence charSequence) {
        mLoginView.setDeleteIconVisibility(resourceId,TextUtils.isEmpty(charSequence) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void deleteIconClick(int resourceId) {
        mLoginView.clearEditText(resourceId);
    }
}
