package com.bunny.groovy.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bunny.groovy.R;
import com.bunny.groovy.api.VerifyEvent;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.LoginPresenter;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.setfile.SetFile1Activity;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.PatternUtils;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ILoginView;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 第三方登录，绑定账户
 * <p>
 * Created by bayin on 2018/1/14.
 */

public class BindAccountFragment extends BaseFragment<LoginPresenter> implements ILoginView {

    @Bind(R.id.bind_account_et_account)
    XEditText etAccount;
    @Bind(R.id.bind_account_et_code)
    XEditText etCode;
    private static String logintype;
    private static String uid;
    private static String username;

    @OnClick({R.id.bind_account_tv_ok, R.id.bind_account_tv_send})
    public void onViewClick(View view) {
        UIUtils.hideSoftInput(view);
        String account = etAccount.getTrimmedString();
        switch (view.getId()) {
            case R.id.bind_account_tv_ok:
                String code = etCode.getTrimmedString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(code)) {
                    if (PatternUtils.isValidEmail(account))//邮箱账户
                        mPresenter.checkEmailCode(code, uid, username, logintype, account);
                    else if (PatternUtils.isCNPhone(account) || PatternUtils.isUSphonenumber(account)) {
                        //手机账户
                        VerifyEvent.verifyCode(code);
                    } else {
                        //非法账户
                        UIUtils.showBaseToast("invalid account.");
                    }
                }
                break;
            case R.id.bind_account_tv_send:
                //判断是手机还是邮箱
                if (PatternUtils.isValidEmail(account)) {
                    //发送邮件
                    mPresenter.socialSendEmailCode(account);
                } else if (PatternUtils.isCNPhone(account) || PatternUtils.isUSphonenumber(account)) {
                    //发送手机验证码
                    VerifyEvent.initSinch(mActivity, account);
                }
                break;
        }
    }

    public static void launch(Activity from, Bundle bundle) {
        username = bundle.getString("username");
        uid = bundle.getString("uid");
        logintype = bundle.getString("logintype");
        FragmentContainerActivity.launch(from, BindAccountFragment.class, bundle);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        if (mActivity instanceof FragmentContainerActivity)
            ((FragmentContainerActivity) mActivity).getToolBar().setVisibility(View.GONE);

        registerEventBus(this);
    }

    /**
     * 验证码结果回调
     *
     * @param result 结果
     */
    @Subscribe
    public void onVerifyEvent(String result) {
        switch (result) {
            case AppConstants.Code_Verify_Correct:
                mPresenter.socialLogin(logintype, uid, username, etAccount.getTrimmedString());
                break;
            case AppConstants.Code_Verify_Invalid:
                UIUtils.showBaseToast("验证码不正确");
                break;
            case AppConstants.Code_Send_ServerError:
                UIUtils.showBaseToast("服务器出错");
                break;
        }
    }

    @Override
    public void loginSuccess(PerformerUserModel userModel) {

    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void launchMainPage() {
        //登录成功，进入主页，结束登录页面
        MainActivity.launch(mActivity);
    }

    @Override
    public void launchToSetFile() {
        startActivityForResult(new Intent(mActivity, SetFile1Activity.class), AppConstants.REQUESTCODE_SETFILE);
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_bind_account;
    }

    @Override
    protected void loadData() {

    }
}