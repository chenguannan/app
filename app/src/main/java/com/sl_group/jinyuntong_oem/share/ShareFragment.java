package com.sl_group.jinyuntong_oem.share;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseFragment;

/**
 * Created by 马天 on 2018/11/13.
 * description：分享
 */
public class ShareFragment extends BaseFragment {
    private TextView mTvShareFaceToFace;
    private TextView mTvShareRegist;
    private TextView mTvShareOfficial;

    @Override
    public int bindLayout() {
        return R.layout.fragment_share;
    }

    @Override
    public void initView(View view) {
        mTvShareFaceToFace = view.findViewById(R.id.tv_share_face_to_face);
        mTvShareRegist = view.findViewById(R.id.tv_share_regist);
        mTvShareOfficial = view.findViewById(R.id.tv_share_official);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mTvShareFaceToFace.setOnClickListener(this);
        mTvShareRegist.setOnClickListener(this);
        mTvShareOfficial.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_face_to_face:
                //面对面分享
                startActivity(ShareFaceToFaceActivity.class);
                break;
            case R.id.tv_share_regist:
                //分享注册
                startActivity(ShareRegistActivity.class);
                break;
            case R.id.tv_share_official:
                //文案推广
                startActivity(OfficialActivity.class);
                break;

        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
