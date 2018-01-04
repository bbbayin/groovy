package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IApplyVenueView;

import java.util.List;
import java.util.Map;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2018/1/3.
 ****************************************/

public class ApplyVenuePresenter extends BasePresenter<IApplyVenueView> {

    public ApplyVenuePresenter(IApplyVenueView view) {
        super(view);
    }

    /**
     * 获取表演style
     */
    public void requestStyle() {
        addSubscription(apiService.getPerformStyle(), new SubscriberCallBack<List<StyleModel>>(mView.get()) {
            @Override
            protected void onSuccess(List<StyleModel> response) {
                if (response != null && response.size() > 0) {
                    mView.showStylePop(response);
                } else {
                    UIUtils.showBaseToast("获取style失败，稍后再试");
                }
            }

            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onFailure(ResultResponse response) {
            }
        });
    }


    /**
     * 申请演出厅演出
     *
     * @param fieldMap
     */
    public void applyVenue(Map<String, String> fieldMap) {
        addSubscription(apiService.releaseShow(fieldMap), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("申请成功");
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

            @Override
            protected boolean isShowProgress() {
                return true;
            }
        });
    }

    /**
     *  确认邀请
     * @param inviteID
     * @param performDesc
     * @param performStyle
     */
    public void confirmInvite(String inviteID,String performDesc,String performStyle){
        addSubscription(apiService.agreePerformerInvite(inviteID, performStyle, performDesc), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("确认成功");
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

            @Override
            protected boolean isShowProgress() {
                return true;
            }
        });
    }
}
