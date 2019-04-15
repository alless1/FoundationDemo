package com.alless.example.umeng;

import com.alless.commonlib.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by chengjie on 2019/4/15
 * Description:
 */
public abstract  class UMBaseFragment extends BaseFragment {
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }
}
