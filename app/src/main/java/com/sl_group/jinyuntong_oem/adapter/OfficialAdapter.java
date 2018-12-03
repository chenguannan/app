package com.sl_group.jinyuntong_oem.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sl_group.jinyuntong_oem.R;

import java.util.List;


/**
 * Created by 马天 on 2017/12/8.
 * description：名片文案库
 */

public class OfficialAdapter extends BaseAdapter {

    // 映射List
    private List<Bitmap> mBitmapList;
    private Activity mActivity;

    public OfficialAdapter(Activity mActivity, List<Bitmap> bitmapList) {
        this.mActivity = mActivity;
        this.mBitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return mBitmapList == null ? 0 : mBitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBitmapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_official,null);
            viewHolder = new ViewHolder();
            viewHolder.imgOfficialPoster = convertView.findViewById(R.id.img_item_official);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imgOfficialPoster.setImageBitmap(mBitmapList.get(position));

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgOfficialPoster;

    }
}
