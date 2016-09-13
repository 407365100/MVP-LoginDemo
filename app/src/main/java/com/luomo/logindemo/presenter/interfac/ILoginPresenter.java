package com.luomo.logindemo.presenter.interfac;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.logindemo.presenter
 * @date :2016-09-12 15:37
 * @description:
 */
public interface ILoginPresenter extends IBasePresenter{
    /**
     * 登陆
     */
    void login();

    /**
     * 文字改变
     * @param resourceId
     * @param charSequence
     */
    void textChanged(int resourceId,CharSequence charSequence);

    /**
     * 点击输入框右侧的删除按钮
     * @param resourceId
     */
    void deleteIconClick(int resourceId);
}
