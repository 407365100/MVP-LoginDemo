package com.luomo.logindemo.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luomo.logindemo.R;
import com.luomo.logindemo.presenter.LoginPresenter;
import com.luomo.logindemo.utils.TextWatcherWrap;
import com.luomo.logindemo.view.interfac.ILoginView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity implements ILoginView {
    /**
     * 用户名输入框
     */
    @BindView(R.id.et_account)
    EditText mEtAccount;
    /**
     * 密码输入框
     */
    @BindView(R.id.et_password)
    EditText mEtPwd;
    /**
     * 用户名右侧删除按钮
     */
    @BindView(R.id.iv_account_delete)
    ImageView mIvAccountDelete;
    /**
     * 密码右侧删除按钮
     */
    @BindView(R.id.iv_password_delete)
    ImageView mIvPwdDelete;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private Map<String, String> mParam;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this);
    }

    @OnClick({R.id.tv_login, R.id.iv_account_delete, R.id.iv_password_delete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login://点击登陆
                mLoginPresenter.login();
                break;
            case R.id.iv_account_delete:
                mLoginPresenter.deleteIconClick(R.id.iv_account_delete);
                break;
            case R.id.iv_password_delete:
                mLoginPresenter.deleteIconClick(R.id.iv_password_delete);
                break;
        }
    }

    /**
     * 添加文本改变的监听
     */
    @Override
    public void addTextChangedListener() {
        //输入框的监听
        mEtAccount.addTextChangedListener(new TextWatcherWrap() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                mLoginPresenter.textChanged(R.id.et_account,charSequence);
            }
        });
        mEtPwd.addTextChangedListener(new TextWatcherWrap() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                mLoginPresenter.textChanged(R.id.et_password,charSequence);
            }
        });
    }

    @Override
    public void setDeleteIconVisibility(int resourceId, int visibility){
        switch (resourceId) {
            case R.id.et_account:
                mIvAccountDelete.setVisibility(visibility);
                break;
            case R.id.et_password:
                mIvPwdDelete.setVisibility(visibility);
                break;
        }
    }

    @Override
    public void clearEditText(int resourceId){
        switch (resourceId) {
            case R.id.iv_account_delete:
                mEtAccount.getText().clear();
                mIvAccountDelete.setVisibility(View.GONE);
                break;
            case R.id.iv_password_delete:
                mEtPwd.getText().clear();
                mIvAccountDelete.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void jump2MainPager() {
        showToast(R.string.jump_to_main_pager);
    }

    @Override
    public Map<String, String> getParam(int flag) {
        if (mParam == null)//为了复用mParam
            mParam = new HashMap<>();
        else
            mParam.clear();
        if (TextUtils.isEmpty(mEtAccount.getText())) {
            showToast(R.string.account_cannot_be_empty);
        } else if (TextUtils.isEmpty(mEtPwd.getText())) {
            showToast(R.string.password_cannot_be_empty);
        } else {
            mParam.put("name", mEtAccount.getText().toString().trim());
            mParam.put("password", mEtPwd.getText().toString().trim());
        }
        return mParam;
    }

    @Override
    public void showProgress() {
        //这里本来应该是显示加载进度框的
        showToast(R.string.display_progress);
        mTvLogin.setClickable(false);
    }

    @Override
    public void dismissProgress() {
        //这里本来应该是取消加载进度框的
        showToast(R.string.cancel_progress);
        mTvLogin.setClickable(true);
    }

    @Override
    public void showToast(int resourceId) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show();
    }
}
