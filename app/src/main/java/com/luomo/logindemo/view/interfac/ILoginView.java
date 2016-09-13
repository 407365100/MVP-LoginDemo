package com.luomo.logindemo.view.interfac;

import java.util.Map;

/**
 * @author :renpan
 * @version :v1.0
 * @class :com.luomo.logindemo.view
 * @date :2016-09-12 15:18
 * @description:
 */
public interface ILoginView extends IBaseView {
    /**
     * 添加EditText文本改变的监听
     */
    void addTextChangedListener();

    /**
     * 显示加载进度框
     */
    void showProgress();

    /**
     * 取消加载进度框
     */
    void dismissProgress();

    /**
     * 跳转到主界面
     */
    void jump2MainPager();

    /**
     * 获取请求参数
     *
     * @param flag
     */
    Map<String, String> getParam(int flag);

    /**
     * 设置删除图标的显示隐藏
     * @param resourceId
     * @param visibility
     */
    void setDeleteIconVisibility(int resourceId, int visibility);

    /**
     * 清除输入框中的文字
     * @param resourceId
     */
    void clearEditText(int resourceId);
}
