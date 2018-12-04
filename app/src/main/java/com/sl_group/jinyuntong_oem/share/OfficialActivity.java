package com.sl_group.jinyuntong_oem.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.OfficialAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/23.
 * description：文案推广
 */
public class OfficialActivity extends BaseActivity {
    private ImageView imgActionbarBack;
    private TextView tvActionbarTitle;
    private GridView gridviewOfficial;


    @Override
    public int bindLayout() {
        return R.layout.activity_official;
    }

    @Override
    public void initView(View view) {
        imgActionbarBack = findViewById(R.id.img_actionbar_back);
        tvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        gridviewOfficial = findViewById(R.id.gridview_official);
    }

    @Override
    public void initData() {
        tvActionbarTitle.setText("文案");
        List<Bitmap> bitmapList = new ArrayList<>();

        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.wenan1));
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.wenan2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.wenan3));
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.wenan4));


        OfficialAdapter officialAdapter = new OfficialAdapter(this, bitmapList);
        gridviewOfficial.setAdapter(officialAdapter);

        gridviewOfficial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                startActivity(OfficialDetailsActivity.class,bundle);
            }
        });
    }

    @Override
    public void setListener() {
        imgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
