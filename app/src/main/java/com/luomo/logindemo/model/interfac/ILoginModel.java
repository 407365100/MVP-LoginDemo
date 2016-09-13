package com.luomo.logindemo.model.interfac;

import java.util.Map;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.shopping.mvp.model
 * @date :2016-05-27 16:34
 * @description:
 */
public interface ILoginModel {

    /**
     * 登陆
     */
    void login(Map<String, String> param, String url, OnStringCallBack callBack);
}
