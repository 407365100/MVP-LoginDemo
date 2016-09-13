package com.luomo.logindemo.view.interfac;

import android.content.Context;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.logindemo.view
 * @date :2016-09-12 15:17
 * @description:
 */
public interface IBaseView {
    /**
     * @return 获取上下文
     */
    Context getContext();

    /**
     * 显示提示信息
     * @param resourceId
     */
    void showToast(int resourceId);
}
