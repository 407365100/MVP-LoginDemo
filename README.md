# MVP-LoginDemo
##MVP概述
![这里写图片描述](http://img.blog.csdn.net/20160912144421865)
###Model层
主要处理与数据相关的操作，如操作数据库、请求服务器等。不与View层直接交互，通过Presenter层与View层互动。
###View层
显示界面，对外提供可刷新UI的接口，不做控制操作。如点击登陆按钮后，弹出等待进度框，View层只提供了一个弹出等待进度框的方法，调用弹出是Presenter层通过View的方法调用的（后面会详细介绍）
###Presenter层
Presenter层负责与Model层交互，又负责界面UI更新。View层也持有Presenter的引用，来执行用户操作请求
##登陆案例
因为案例案例源码相对于快速理解MVP不是很合适，所以先实现一个简单案例，大家理解了大概原理之后，在深入讲解案例源码的实现
###项目目录
![这里写图片描述](http://img.blog.csdn.net/20160912172342048)
####model层
- ILoginModel.java 接口类
```java
public interface ILoginModel {

    /**
     * 登陆
     */
    void login(Map<String, String> param, String url, OnStringCallBack callBack);
}
```

 <br/>

 - LoginModel.java 具体实现
```java
public class LoginModel implements ILoginModel {
    private Context mContext;

    public LoginModel(Context context) {
        this.mContext = context;
    }

    @Override
    public void login(Map<String, String> param, String url, OnStringCallBack callBack) {
	    //OnStringCallBack是一个回调接口
        callBack.onResponse("{'flag':'success'}");
    }
}
```
####View层
- IBaseView.java
```java
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
```

- ILoginView.java
```java
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
```

- LoginActivity.java
实现ILoginView接口中的方法，详见代码
####presenter层
- IBasePresenter.java
一些基类方法，当前为空。因为功能简单，没有可抽取的
-ILoginPresenter.java
```java
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
```

- LoginPresenter.java
具体的实现
```java
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
```
###执行流程
```flow
st=>start: 开始
e=>end: 结束
op=>operation: 点击登陆
opOnClick=>operation: 调用LoginActivity->onClick//点击事件
opInvokeLogin=>operation: 调用LoginPresenter->login()//登陆方法
opGetParam=>operation: 调用LoginView->getParam(1)、showProgress()、dismissProgress()//登陆方法中的相关操作
opSuccess=>operation: 调用LoginView->jump2MainPager()//跳转到主页
opFail=>operation: 调用LoginView->showToast("账号或密码错误")//提示登录失败信息
cond=>condition: 登陆成功？

st->op->opOnClick->opInvokeLogin->opGetParam->cond
cond(yes)->opSuccess->e
cond(no)->opFail->e
```
###View层抽取原则
当然针对于当前的简单案例，接下来的文章会对复杂案例进行抽取，包括各种细节会做详细说明

1.需要初始化的信息： setOnclickListener、addTextChangedListener诸如此类的，都要抽取View接口方法，方便Presenter初始化时调用(详见LoginPresenter类)。如
 注：因为我用的注解所以点击事件并未抽取到方法中
 
```java
	/**
     * 添加文本改变的监听
     */
    @Override
    public void addTextChangedListener() 
```

2.显示/取消加载进度框、显示提示信息、获取上下文信息

```java
    /**
     * @return 获取上下文
     */
    Context getContext();

    /**
     * 显示提示信息
     * @param resourceId
     */
    void showToast(int resourceId);
    
    /**
     * 显示加载进度框
     */
    void showProgress();

    /**
     * 取消加载进度框
     */
    void dismissProgress();
```
3.账号输入框中点击删除按钮的响应事件和文本改变的响应事件。以int值id为参数
```java
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
```
4.网络请求成功后，做的跳转事件
```java
	/**
     * 跳转到主界面
     */
    void jump2MainPager();
```
###效果图
![这里写图片描述](http://img.blog.csdn.net/20160912172200952)
###项目地址
[https://github.com/407365100/MVP-LoginDemo](https://github.com/407365100/MVP-LoginDemo)
