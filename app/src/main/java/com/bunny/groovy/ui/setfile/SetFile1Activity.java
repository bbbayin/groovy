package com.bunny.groovy.ui.setfile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.listener.PermissionListener;
import com.bunny.groovy.presenter.SetFilePresenter;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.login.LoginActivity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISetFileView;
import com.xw.repo.XEditText;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/****************************************
 * 功能说明:  完善资料第一步
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFile1Activity extends BaseActivity<SetFilePresenter> implements ISetFileView {
    @Bind(R.id.perfect_info_et_fullname)
    XEditText etFullName;
    @Bind(R.id.perfect_info_et_artistname)
    XEditText etArtistName;
    @Bind(R.id.perfect_info_et_zipcode)
    XEditText etZipcode;
    @Bind(R.id.perfect_info_et_website)
    XEditText etWebsite;
    @Bind(R.id.perfect_info_iv_headpic)
    CircleImageView headView;

    @OnClick(R.id.perfect_info_iv_select_pic)
    void selectPic() {
        requestRuntimePermission(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //选择图片
                choosePic(SetFile1Activity.this);
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                //拒绝
                UIUtils.showBaseToast("Select picture denied.");
            }
        });
    }

    @OnClick(R.id.perfect_info_tv_login)
    void login() {
        LoginActivity.launch(this);
    }

    @OnClick(R.id.perfect_info_tv_next)
    void next() {
        //保存数据
        AppCacheData.getPerformerUserModel().setUserName(etFullName.getTrimmedString());
        AppCacheData.getPerformerUserModel().setStageName(etArtistName.getTrimmedString());
        AppCacheData.getPerformerUserModel().setZipCode(etZipcode.getTrimmedString());
        AppCacheData.getPerformerUserModel().setWebSiteAddress(etWebsite.getTrimmedString());
        AppCacheData.getPerformerUserModel().setHeadImg(headImagePath);
        Intent intent = new Intent(this, SetFile2Activity.class);
        startActivityForResult(intent, 1);
    }

    private String headImagePath = "";//头像文件路径

    @Override
    public void initView() {
        super.initView();
        etZipcode.setCheckStatus(XEditText.CheckStatus.INFO);
    }

    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    @Override
    protected SetFilePresenter createPresenter() {
        return new SetFilePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.first_perfect_file_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUESTCODE_SELECT_PIC && resultCode == RESULT_OK) {
            List<String> mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;
            headImagePath = mResults.get(0);
            Bitmap bitmap = BitmapFactory.decodeFile(headImagePath);
            headView.setImageBitmap(bitmap);
        } else if (requestCode == 1 && resultCode == AppConstants.ACTIVITY_FINISH) {
            MainActivity.launchWithData(this, null);
            finish();
        }
    }
}
