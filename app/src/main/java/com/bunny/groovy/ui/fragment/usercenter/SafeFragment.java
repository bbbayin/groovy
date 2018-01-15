package com.bunny.groovy.ui.fragment.usercenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.base.FragmentContainerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2018/1/12.
 ****************************************/

public class SafeFragment extends BaseFragment {

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();

        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "ACCOUNT SECURITY");
        FragmentContainerActivity.launch(from, SafeFragment.class, bundle);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_safe;
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.safe_phone, R.id.safe_email})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.safe_phone:
                bundle = new Bundle();
                bundle.putString(LinkFragment.KEY, "phone");
                bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "LINK PHONE");
                break;
            case R.id.safe_email:
                bundle = new Bundle();
                bundle.putString(LinkFragment.KEY, "email");
                bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "LINK EMAIL");
                break;
        }
        LinkFragment.launch(mActivity, bundle);
    }
}