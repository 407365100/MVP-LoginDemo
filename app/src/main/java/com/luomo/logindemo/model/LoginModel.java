package com.luomo.logindemo.model;

import android.content.Context;

import com.luomo.logindemo.model.interfac.ILoginModel;
import com.luomo.logindemo.model.interfac.OnStringCallBack;

import java.util.Map;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.shopping.mvp.model
 * @date :2016-05-27 16:35
 * @description:
 */
public class LoginModel implements ILoginModel {
    private Context mContext;

    public LoginModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void login(Map<String, String> param, String url, OnStringCallBack callBack) {
        callBack.onResponse("{'flag':'success'}");
    }
}
