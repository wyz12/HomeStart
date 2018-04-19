package com.zxwl.homestart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxwl.homestart.Bean.AppDetail;
import com.zxwl.homestart.Bean.VpitemBean;
import com.zxwl.homestart.R;

import java.util.ArrayList;

/**
 * Created by sks on 2018/4/19.
 */

public class VpItmeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<VpitemBean> list;
    private GridView gridView;
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量


    public VpItmeAdapter(Context context, ArrayList<VpitemBean> list, int mIndex, int mPargerSize,GridView gridView) {
        this.context = context;
        this.list = list;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return list.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (list.size() - mIndex*mPargerSize);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPargerSize;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HH hh;
        if(convertView==null){
            hh = new HH();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_viewpager_gridview_item, parent,false);
            hh.name = convertView.findViewById(R.id.textview1);
            hh.iamge = convertView.findViewById(R.id.image1);
            hh.backgd = convertView.findViewById(R.id.backgd);
            convertView.setTag(hh);
        }else{
            hh= (HH) convertView.getTag();
        }

        int height = gridView.getHeight();
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height=height/2;
        convertView.setLayoutParams(layoutParams);



        final int pos = position + mIndex * mPargerSize;
        hh.name.setText(list.get(pos).getName()+"");
        Drawable appIcon = list.get(pos).getImage();
        hh.iamge.setBackground(appIcon);
        Drawable backimage = list.get(pos).getBackimage();
        hh.backgd.setBackground(backimage);
        return convertView;
    }

    class  HH{
        ImageView iamge;
        TextView name;
        LinearLayout backgd;
    }
}
